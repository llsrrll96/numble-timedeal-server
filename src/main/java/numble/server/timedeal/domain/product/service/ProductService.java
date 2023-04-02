package numble.server.timedeal.domain.product.service;

import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.product.repository.Category;
import numble.server.timedeal.domain.product.repository.ProductEntity;
import numble.server.timedeal.domain.product.repository.ProductRepository;
import numble.server.timedeal.domain.user.repository.UserEntity;
import numble.server.timedeal.domain.user.repository.UserRepository;
import numble.server.timedeal.domain.product.dto.ReqProduct;
import numble.server.timedeal.domain.product.dto.RespProduct;
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
                .categoryCode(reqProduct.getCategoryCode())
                .build();
        UserEntity userEntity = userRepository.findById(reqProduct.getUserId()).get();

        return ProductEntity.builder()
                .category(category)
                .userEntity(userEntity)
                .productCode(reqProduct.getProductCode())
                .productName(reqProduct.getProductName())
                .productDesc(reqProduct.getProductDesc())
                .productPrice(reqProduct.getProductPrice())
                .salePrice(reqProduct.getSalePrice())
                .productAmount(reqProduct.getProductAmount())
                .build();
    }

    private RespProduct convertToDto(ProductEntity productEntity){
        return modelMapper.map(productEntity, RespProduct.class);
    }

    public RespProduct createProduct(ReqProduct reqProduct) {
        ProductEntity productEntity = productRepository.save(convertToEntity(reqProduct));
        return convertToDto(productEntity);
    }

    @Transactional
    public RespProduct updateProduct(Long productid, ReqProduct reqProduct) {
        ProductEntity productEntity = productRepository.findById(productid).get();
        productEntity.setCategory(Category.builder().categoryCode(reqProduct.getCategoryCode()).build());
        productEntity.setProductName(reqProduct.getProductName());
        productEntity.setProductDesc(reqProduct.getProductDesc());
        productEntity.setProductPrice(reqProduct.getProductPrice());
        productEntity.setSalePrice(reqProduct.getSalePrice());
        productEntity.setProductAmount(reqProduct.getProductAmount());
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

    public ProductEntity findByProductId(Long productid){
        return productRepository.findByProductId(productid).get();
    }

    public List<RespProduct> findProductList() {
        List<ProductEntity> productEntityList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return productEntityList.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }
}
