package az.elvin.constructionAdmin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private int productCode;

    private String description;

    private int showCount;

    private Integer havingStatus;

    private Double oldAmount;

    private Double newAmount;

    private Long brendId;

    private Long categoryId;

    private Long colourId;

    private Long countryId;

    private Long sizeId;

    private String havingStatusName;

    private MultipartFile mainImage;

    private MultipartFile firstImage;

    private MultipartFile secondImage;

    private String mainImageName;

    private String firstImageName;

    private String secondImageName;

}
