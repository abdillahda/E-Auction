package com.eauction.application.domain.common.filtering;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRange {
    private int page;
    private int perPage;
}
