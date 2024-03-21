package com.eauction.application.domain.common.filtering;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {
    private boolean orPredicate;
    private String key;
    private String Operation;
    private String value;
    private String valueTo;
}
