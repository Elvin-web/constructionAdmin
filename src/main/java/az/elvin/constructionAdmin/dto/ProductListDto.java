package az.elvin.constructionAdmin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {

    private String name;

    private int productCode;

    private String category;

    private Double oldAmount;

    private Integer havingStatus;

    private String havingStatusName;

    private String actions;
}
