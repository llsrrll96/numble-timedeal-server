package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.category.Category;
import numble.server.timedeal.domain.product.ProductEntity;
import numble.server.timedeal.domain.product.ProductRepository;
import numble.server.timedeal.domain.user.UserEntity;
import numble.server.timedeal.domain.user.UserRepository;
import numble.server.timedeal.dto.request.ReqProduct;
import numble.server.timedeal.dto.response.RespProduct;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private ProductEntity convertToEntity(ReqProduct reqProduct){
        Category category = Category.builder()
                .categoryCode(reqProduct.getCategory_code())
                .build();
        UserEntity userEntity = userRepository.findById(reqProduct.getUser_id()).get();

        return ProductEntity.builder()
                .category(category)
                .userEntity(userEntity)
                .productCode(reqProduct.getProduct_code())
                .productName(reqProduct.getProduct_name())
                .productDesc(reqProduct.getProduct_desc())
                .productPrice(reqProduct.getProduct_price())
                .salePrice(reqProduct.getSale_price())
                .productAmount(reqProduct.getProduct_amount())
                .build();
    }

    private RespProduct convertToDto(ProductEntity productEntity){
        return modelMapper.map(productEntity, RespProduct.class);
    }

    public RespProduct productCreation(ReqProduct reqProduct) {
        ProductEntity productEntity = productRepository.save(convertToEntity(reqProduct));
        return convertToDto(productEntity);
    }

    @Transactional
    public RespProduct updateProduct(Long productid, ReqProduct reqProduct) {
        ProductEntity productEntity = productRepository.findById(productid).get();
        productEntity.setCategory(Category.builder().categoryCode(reqProduct.getCategory_code()).build());
        productEntity.setProductName(reqProduct.getProduct_name());
        productEntity.setProductDesc(reqProduct.getProduct_desc());
        productEntity.setProductPrice(reqProduct.getProduct_price());
        productEntity.setSalePrice(reqProduct.getSale_price());
        productEntity.setProductAmount(reqProduct.getProduct_amount());
        return convertToDto(productEntity);
    }

    @Transactional
    public int deleteProduct(Long productId) {
        return productRepository.deleteByProductId(productId);
    }

    public RespProduct findProductInfo(Long productid) {
        ProductEntity productEntity = productRepository.findByProductId(productid).get();
        return convertToDto(productEntity);
    }

    public List<RespProduct> findProductList() {
        List<ProductEntity> productEntityList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return productEntityList.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }
}
