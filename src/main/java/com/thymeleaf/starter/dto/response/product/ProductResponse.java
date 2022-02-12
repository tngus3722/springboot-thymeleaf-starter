package com.thymeleaf.starter.dto.response.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thymeleaf.starter.dto.page.CursorCriteria;
import com.thymeleaf.starter.dto.page.CustomPageable;
import com.thymeleaf.starter.dto.response.BaseResponse;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
public class ProductResponse extends BaseResponse implements CustomPageable {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer productRemains;
    @JsonIgnore
    private String cursor;

    @Override
    public String getCursor(CursorCriteria cursorCriteria) {
        switch (cursorCriteria.getSortBy()) {
            case "productPrice":
                this.cursor = this.productPrice + " " + this.productId;
                break;
            default:
                this.cursor = String.valueOf(this.productId);
                break;
        }
        return Base64.encodeBase64String(this.cursor.getBytes(StandardCharsets.UTF_8));
    }
}
