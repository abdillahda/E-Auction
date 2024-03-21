package com.eauction.application.util.search;

import com.eauction.application.domain.common.filtering.SearchCriteria;
import com.eauction.application.util.constant.SearchOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GenericSpecificationsBuilder<U> {
    private final List<SpecificationSearchCriteria> params;

    public GenericSpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public Specification<U> getSpecification(SpecificationSearchCriteria criteria) {
        return new Specification<U>() {
            @Override
            public Predicate toPredicate(Root<U> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                try {
                    return genericCriteria(criteria,root,criteriaBuilder);
                } catch (ParseException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public Predicate genericCriteria(SpecificationSearchCriteria criteria, Root<?> root, CriteriaBuilder builder) throws ParseException, JsonProcessingException {
        Class dataType = root.get(criteria.getKey()).getJavaType();
        //log.info("[GenericSpecificationsBuilder::genericCriteria][key:" + criteria.getKey() + "][value:" + criteria.getValue(dataType) + "]");
        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue(dataType));
            case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue(dataType));
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), (Comparable)criteria.getValue(dataType));
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), (Comparable)criteria.getValue(dataType));
            case GREATER_THAN_EQUAL -> builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Comparable)criteria.getValue(dataType));
            case LESS_THAN_EQUAL -> builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Comparable)criteria.getValue(dataType));
            case LIKE -> builder.like(root.get(criteria.getKey()), criteria.getValue(dataType).toString());
            case STARTS_WITH -> builder.like(root.get(criteria.getKey()), criteria.getValue(dataType) + "%");
            case ENDS_WITH -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue(dataType));
            case CONTAINS -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue(dataType) + "%");
            case BETWEEN -> builder.between(root.get(criteria.getKey()), (Comparable)criteria.getValue(dataType), (Comparable)criteria.getValueTo(dataType) );
            case INLIST -> root.get(criteria.getKey()).in(criteria.getValueAsList());
            case NOTINLIST -> builder.not(root.get(criteria.getKey()).in( criteria.getValueAsList() ));
            default -> null;
        };
    }

    public final GenericSpecificationsBuilder<U> with(final SearchCriteria[] criteria) {
        if(criteria == null) {
            return this;
        }
        for( SearchCriteria c : criteria) {
            if(c.getKey() != null && c.getOperation() != null && c.getValue() != null) {
                with(c.isOrPredicate(), c.getKey(), c.getOperation(), c.getValue(), c.getValueTo());
            }
        }
        return this;
    }

    public final GenericSpecificationsBuilder<U> with(final SearchCriteria criteria) {
        return with(false, criteria.getKey(), criteria.getOperation(), criteria.getValue(), criteria.getValueTo());
    }

    public final GenericSpecificationsBuilder<U> with(final String key, final String operation, final Object value, final Object valueTo ) {
        return with(false, key, operation, value, valueTo);
    }

    public final GenericSpecificationsBuilder<U> with(final boolean orPredicate, final String key, final String operation, final Object value, final Object valueTo ) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        if (op != null) {
            params.add(new SpecificationSearchCriteria(orPredicate, key, op, value, valueTo));
        }
        return this;
    }

    public Specification<U> build() {

        if (params.isEmpty()) {
            return null;
        }

        final List<Specification<U>> specs = params.stream().map(this::getSpecification).collect(Collectors.toCollection(ArrayList::new));

        Specification<U> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx).isOrPredicate()
                    ? Specification.where(result).or(specs.get(idx))
                    : Specification.where(result).and(specs.get(idx));
        }

        return result;
    }
}
