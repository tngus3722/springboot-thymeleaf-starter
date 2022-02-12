package com.thymeleaf.starter.dto.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OffsetCriteria extends BaseCriteria {
    @Max(value = 10000000, message = "page는 너무 클 수 없습니다.")
    private int page = 0;
    @JsonIgnore
    private int offset = 0;

    public int getOffset() {
        if (page <= 0)
            return 0;
        else
            return this.page * this.limit;
    }
}
