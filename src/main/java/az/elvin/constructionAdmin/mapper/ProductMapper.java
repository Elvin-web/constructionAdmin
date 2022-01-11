package az.elvin.constructionAdmin.mapper;


import az.elvin.constructionAdmin.dto.ProductDto;
import az.elvin.constructionAdmin.dto.ProductInfoDto;
import az.elvin.constructionAdmin.dto.ProductListDto;
import az.elvin.constructionAdmin.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static az.elvin.constructionAdmin.enums.ActiveEnum.ACTIVE;

@Mapper
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(target = "brend.id", source = "brendId"),
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "colour.id", source = "colourId"),
            @Mapping(target = "country.id", source = "countryId"),
            @Mapping(target = "size.id", source = "sizeId"),
            @Mapping(target = "active", expression = "java(active())")
    })
    public abstract ProductEntity dtoToEntity(ProductDto productDto);

    public Integer active() {
        return ACTIVE.ordinal();
    }

    @Mappings({@Mapping(target = "brendId", source = "brend.id"),
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "colourId", source = "colour.id"),
            @Mapping(target = "countryId", source = "country.id"),
            @Mapping(target = "havingStatusName", source = "havingStatus", qualifiedByName = "havingStatus"),
            @Mapping(target = "sizeId", source = "size.id")})
    public abstract ProductInfoDto entityToInfoDtoById(ProductEntity productEntity);

    @Mappings({
            @Mapping(target = "havingStatusName", source = "havingStatus", qualifiedByName = "havingStatus"),
            @Mapping(target = "category", source = "category.name")
    })
    public abstract ProductListDto entityToDto(ProductEntity productEntity);


    @Named(value = "havingStatusName")
    public static String havingStatusName(Integer havingStatus) {
        if (havingStatus.equals(0))
            return "Yoxdur";
        else if (havingStatus.equals(1))
            return "Var";
        return null;
    }
}
