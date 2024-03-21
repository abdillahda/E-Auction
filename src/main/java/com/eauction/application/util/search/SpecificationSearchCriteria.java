package com.eauction.application.util.search;

import com.eauction.application.util.constant.SearchOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Setter
@Getter
public class SpecificationSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private Object valueTo;
    private boolean orPredicate;

    public SpecificationSearchCriteria() {

    }

    public SpecificationSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.valueTo = null;
    }

    public SpecificationSearchCriteria(final String key, final SearchOperation operation, final Object value, final Object valueTo) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.valueTo = valueTo;
    }

    public SpecificationSearchCriteria(final boolean orPredicate, final String key, final SearchOperation operation, final Object value, final Object valueTo) {
        super();
        this.orPredicate = orPredicate;
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.valueTo = valueTo;
    }

    public SpecificationSearchCriteria(String key, String operation, Object value, Object valueTo) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        this.key = key;
        this.operation = op;
        this.value = value;
        this.valueTo = valueTo;
    }

    public Object getValue(Class dataType) throws ParseException {
        if(dataType == java.lang.String.class) {
            return value;
        }
        else if(dataType == java.math.BigDecimal.class) {
            return new BigDecimal(value.toString());
        }
        else if(dataType == java.lang.Long.class) {
            return Long.parseLong(value.toString());
        }
        else if(dataType == java.lang.Integer.class) {
            return Integer.parseInt(value.toString());
        }
        else if(dataType == java.lang.Double.class) {
            return Double.parseDouble(value.toString());
        }
        else if(dataType == java.lang.Float.class) {
            return Float.parseFloat(value.toString());
        }
        else if(dataType == java.util.Date.class) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return fmt.parse(value.toString());
        }
        else if(dataType == java.sql.Date.class) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return fmt.parse(value.toString());
        }
        else if(dataType == java.lang.Boolean.class) {
            return value.toString().equalsIgnoreCase("true");
        }

        return value;
    }

    public Object getValueTo(Class dataType) throws ParseException {
        if(dataType == java.lang.String.class) {
            return valueTo;
        }
        else if(dataType == java.math.BigDecimal.class) {
            return new BigDecimal(valueTo.toString());
        }
        else if(dataType == java.lang.Long.class) {
            return Long.parseLong(valueTo.toString());
        }
        else if(dataType == java.lang.Integer.class) {
            return Integer.parseInt(valueTo.toString());
        }
        else if(dataType == java.lang.Double.class) {
            return Double.parseDouble(valueTo.toString());
        }
        else if(dataType == java.lang.Float.class) {
            return Float.parseFloat(valueTo.toString());
        }
        else if((dataType == java.util.Date.class)||(dataType == java.sql.Date.class)) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return fmt.parse(valueTo.toString());
        }
        else if(dataType == java.lang.Boolean.class) {
            return valueTo.toString().equalsIgnoreCase("true");
        }

        return valueTo;
    }

    public java.util.List<String> getValueAsList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value.toString(), new TypeReference<List<String>>(){});
    }
}
