package az.elvin.constructionAdmin.mapper;


import az.elvin.constructionAdmin.dto.SizeDto;
import az.elvin.constructionAdmin.entity.SizeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static az.elvin.constructionAdmin.enums.ActiveEnum.ACTIVE;

@Mapper
public abstract class SizeMapper {

    public static final SizeMapper INSTANCE = Mappers.getMapper(SizeMapper.class);

    @Mappings({
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "active", expression = "java(active())")
    })
    public abstract SizeEntity dtoToEntity(SizeDto sizeDto);

    public Integer active() {
        return ACTIVE.ordinal();
    }

    @Mapping(target = "categoryId", source = "category.id")
    public abstract SizeDto entityToDtoById(SizeEntity sizeEntity);
}
