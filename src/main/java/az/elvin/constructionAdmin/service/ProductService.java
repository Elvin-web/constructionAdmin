package az.elvin.constructionAdmin.service;


import az.elvin.constructionAdmin.dto.*;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.entity.*;
import az.elvin.constructionAdmin.enums.ResponseEnum;
import az.elvin.constructionAdmin.exception.CommonException;
import az.elvin.constructionAdmin.mapper.ProductMapper;
import az.elvin.constructionAdmin.repo.*;
import az.elvin.constructionAdmin.util.Method;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static az.elvin.constructionAdmin.dto.respone.CommonResponse.success;
import static az.elvin.constructionAdmin.enums.ActiveEnum.ACTIVE;
import static az.elvin.constructionAdmin.enums.ActiveEnum.DEACTIVE;
import static az.elvin.constructionAdmin.enums.ResponseEnum.PRODUCT_NOT_FOUND;
import static az.elvin.constructionAdmin.util.CommonUtil.createDataTable;
import static az.elvin.constructionAdmin.util.CommonUtil.getPerPage;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final BrendRepository brendRepository;
    private final ColourRepository colourRepository;
    private final CountryRepository countryRepository;
    private final SizeRepository sizeRepository;
    private final ProductImageRepository productImageRepository;

    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
    private static final int REQUEST_SIZE = 1024 * 1024 * 50;

    public ProductService(ProductRepository productRepository, BrendRepository brendRepository, ColourRepository colourRepository, CountryRepository countryRepository, SizeRepository sizeRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.brendRepository = brendRepository;
        this.colourRepository = colourRepository;
        this.countryRepository = countryRepository;
        this.sizeRepository = sizeRepository;
        this.productImageRepository = productImageRepository;
    }


    public Map<String, Object> getProducts(int page, String perPage) {
        log.info("*****Get Product List Service*****");
        List<ProductListDto> productListDtos = new LinkedList<>();

        Page<ProductEntity> getProductEntities = productRepository.findAllByActiveOrderByNameAsc(ACTIVE.ordinal(),
                PageRequest.of(page - 1, getPerPage(perPage)));

        getProductEntities.forEach(productEntity -> {
            String actions = "";
            if (productEntity.getActive().equals(ACTIVE.ordinal())) {
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill productEdit\" data-id=\"" + productEntity.getId() + "\" title=\"Edit\"><i class=\"la la-edit\"></i></a>";
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill productDelete\" data-id=\"" + productEntity.getId() + "\" title=\"Delete\"><i class=\"la la-trash\"></i></a>";
            }

            ProductListDto productListDto = ProductMapper.INSTANCE.entityToDto(productEntity);
            productListDto.setActions(actions);

            productListDtos.add(productListDto);
        });
        long productCount = productRepository.countAllByActive(ACTIVE.ordinal());
        return createDataTable(productListDtos, productCount, page, perPage);

    }

    public CommonResponse addProduct(ProductDto productDto) throws JsonProcessingException {
        log.info("*****Add Product Service*****");
        log.info(String.format("productDto = %s", productDto));

        ProductEntity productEntity = ProductMapper.INSTANCE.dtoToEntity(productDto);
        productEntity.setNewAmount(productDto.getNewAmount() == null ? 0.0 : productDto.getNewAmount());
        productEntity = productRepository.save(productEntity);

        try {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(THRESHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(REQUEST_SIZE);
            ProductImageEntity productImageEntity = new ProductImageEntity();
            if (!productDto.getMainImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setMainImage(Method.fileWrite(productDto.getMainImage()));
            }
            if (!productDto.getFirstImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setFirstImage(Method.fileWrite(productDto.getFirstImage()));
            }
            if (!productDto.getSecondImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setSecondImage(Method.fileWrite(productDto.getSecondImage()));
            }

            productImageEntity.setProduct(productEntity);
            productImageEntity.setActive(ACTIVE.ordinal());
            productImageRepository.save(productImageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success();

    }

    public CommonResponse getProductById(Long id) {
        log.info("*****Get Product By Id Service*****");
        log.info(String.format("id = %s", id));

        ProductEntity productEntity = productRepository.findById(id).get();

        ProductImageEntity productImageEntity = productImageRepository.findProductImageEntityByProductId(productEntity.getId());

        ProductInfoDto productInfoDto = ProductMapper.INSTANCE.entityToInfoDtoById(productEntity);

        productInfoDto.setMainImage(productImageEntity.getMainImage());
        productInfoDto.setFirstImage(productImageEntity.getFirstImage());
        productInfoDto.setSecondImage(productImageEntity.getSecondImage());

        return success(productInfoDto);
    }

    public CommonResponse editProduct(Long id, ProductDto productDto) throws JsonProcessingException {
        log.info("*****Edit Product Service*****");
        log.info(String.format("productDto = %s", productDto));

        Optional<ProductEntity> productEntity =
                Optional.ofNullable(productRepository.findById(id)
                        .orElseThrow(() -> new CommonException(PRODUCT_NOT_FOUND)));

        ProductEntity product = productEntity.get();

        product.setName(productDto.getName());

        product.setProductCode(productDto.getProductCode());

        product.setDescription(productDto.getDescription());

        product.setHavingStatus(productDto.getHavingStatus());

        product.setOldAmount(productDto.getOldAmount());

        product.setNewAmount(productDto.getNewAmount());

        BrendEntity brendEntity = new BrendEntity();
        brendEntity.setId(productDto.getBrendId());
        product.setBrend(brendEntity);


        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(productDto.getCategoryId());
        product.setCategory(categoryEntity);

        ColourEntity colourEntity = new ColourEntity();
        colourEntity.setId(productDto.getColourId());
        product.setColour(colourEntity);

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(productDto.getCountryId());
        product.setCountry(countryEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(productDto.getSizeId());
        product.setSize(sizeEntity);

        productRepository.save(product);

        ProductImageEntity productImageEntity;
        productImageEntity = productImageRepository.findProductImageEntityByProductId(id);

        try {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(THRESHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(REQUEST_SIZE);
            if (!productDto.getMainImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setMainImage(Method.fileWrite(productDto.getMainImage()));
            }
            if (!productDto.getFirstImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setFirstImage(Method.fileWrite(productDto.getFirstImage()));
            }
            if (!productDto.getSecondImage().getOriginalFilename().isEmpty()) {
                productImageEntity.setSecondImage(Method.fileWrite(productDto.getSecondImage()));
            }

            productImageEntity.setProduct(product);

            productImageEntity.setActive(ACTIVE.ordinal());
            productImageRepository.save(productImageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productImageRepository.save(productImageEntity);
        return success();
    }


    public CommonResponse deleteProduct(Long id) {
        log.info("*****Delete Product Service*****");
        log.info(String.format("id = %s", id));

        ProductEntity productEntity = productRepository.findById(id).get();
        productEntity.setActive(DEACTIVE.ordinal());
        productRepository.save(productEntity);
        return success();
    }

    public CommonResponse getBrendList() {
        List<BrendEntity> brendEntities = brendRepository.findAllByActiveOrderById(ACTIVE.ordinal());
        List<BrendDto> brendDtos = new LinkedList<>();
        brendEntities.forEach(brendEntity -> {
            BrendDto brendDto = new BrendDto();
            brendDto.setId(brendEntity.getId());
            brendDto.setName(brendEntity.getName());
            brendDtos.add(brendDto);
        });
        return success(brendDtos);
    }

    public CommonResponse getColourList() {
        List<ColourEntity> colourEntities = colourRepository.findAllByActiveOrderById(ACTIVE.ordinal());
        List<ColourDto> colourDtos = new LinkedList<>();
        colourEntities.forEach(colourEntity -> {
            ColourDto colourDto = new ColourDto();
            colourDto.setId(colourEntity.getId());
            colourDto.setName(colourEntity.getName());
            colourDtos.add(colourDto);
        });
        return success(colourDtos);
    }

    public CommonResponse getCountryList() {
        List<CountryEntity> countryEntities = countryRepository.findAllByActive(ACTIVE.ordinal());
        List<CountryDto> countryDtos = new LinkedList<>();
        countryEntities.forEach(countryEntity -> {
            CountryDto countryDto = new CountryDto();
            countryDto.setId(countryEntity.getId());
            countryDto.setName(countryEntity.getName());
            countryDtos.add(countryDto);
        });
        return success(countryDtos);
    }

    public CommonResponse getSizeList() {
        List<SizeEntity> sizeEntities = sizeRepository.findAllByActive(ACTIVE.ordinal());
        List<SizeDto> sizeDtos = new LinkedList<>();
        sizeEntities.forEach(sizeEntity -> {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setId(sizeEntity.getId());
            sizeDto.setName(sizeEntity.getName());
            sizeDtos.add(sizeDto);
        });
        return success(sizeDtos);
    }

    public CommonResponse getHavingStatusList() {

        String[] nameList = {"Yoxdur", "Var"};
        Integer[] activeList = {0, 1};

        List<HavingStatusEntity> havingStatusEntities = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            HavingStatusEntity havingStatusEntity = new HavingStatusEntity();
            havingStatusEntity.setActive(activeList[i]);
            havingStatusEntity.setName(nameList[i]);
            havingStatusEntities.add(havingStatusEntity);
        }
        return success(havingStatusEntities);
    }
}
