package com.thymeleaf.starter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thymeleaf.starter.dto.page.OffsetCriteria;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOffsetResponse<T> {
    private List<T> response;
    private String next;
    //private String previous;

    public BaseOffsetResponse(List<T> response, OffsetCriteria offsetCriteria, String endpoint) {
        if (response.size() == offsetCriteria.getLimit() + 1) {
            this.response = new ArrayList<>(response.subList(0, offsetCriteria.getLimit()));
            this.next = offsetCriteria.getNextUrlBase(endpoint)
                    .append("&page=").append(offsetCriteria.getPage() + 1)
                    .toString();
        } else
            this.response = response;
    }
}
