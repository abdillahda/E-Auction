package com.eauction.application.domain.common.filtering;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchQuery {
    private SearchRange searchRange;
    private SortCriteria[] sortCriteria;
    private SearchCriteria[] searchCriteria;

    public Sort getSort(){
        if (sortCriteria.length == 0){
            return Sort.by("id");
        }
        Sort x = sortCriteria[0].build();
        for (int i = 0; i < sortCriteria.length; i++) {
            x.and(sortCriteria[i].build());
        }
        return x;
    }

    public Pageable getPageable() {
        return PageRequest.of(searchRange.getPage() - 1, searchRange.getPerPage(), getSort());
    }

    public String getContentRange(){
        int start = (searchRange.getPage() * searchRange.getPerPage()) - searchRange.getPerPage()-1;
        return String.format("%d-%d",start,(start+searchRange.getPerPage()-1));
    }
}
