package com.eauction.application.domain.common.filtering;

import lombok.*;
import org.springframework.data.domain.Sort;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SortCriteria {
    private String field;
    private String order;

    public Sort build(){
        return Sort.by(order.equalsIgnoreCase("asc")? Sort.Direction.ASC: Sort.Direction.DESC,field);
    }
}