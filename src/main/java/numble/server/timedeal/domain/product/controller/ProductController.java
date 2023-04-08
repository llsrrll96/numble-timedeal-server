package numble.server.timedeal.domain.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.product.dto.CategoryDto;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.domain.product.dto.ReqProduct;
import numble.server.timedeal.domain.product.dto.RespProduct;
import numble.server.timedeal.domain.user.util.UserEnum;
import numble.server.timedeal.domain.product.service.CategoryService;
import numble.server.timedeal.domain.product.service.ProductService;
import numble.server.timedeal.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    private boolean isAdminUser(String userId){
        return userService.isUserRoleByUserId(userId, UserEnum.ROLE_ADMIN);
    }

    @PostMapping("/v1")
    private ResponseEntity<APIMessage<RespProduct>> createProduct(@RequestBody ReqProduct reqProduct){
        if(isAdminUser(reqProduct.getUserId())){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),ProductResponseMessage.SAVING_PRODUCT_SUCCESS.getRespDesc(),productService.createProduct(reqProduct)),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), ProductResponseMessage.SAVING_PRODUCT_FAILED.getRespDesc(),new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> updateProduct(@PathVariable Long productid, @RequestBody ReqProduct reqProduct){
        if(isAdminUser(reqProduct.getUserId())) {
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),ProductResponseMessage.UPDATING_PRODUCT_SUCCESS.getRespDesc(), productService.updateProduct(productid, reqProduct)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), ProductResponseMessage.UPDATING_PRODUCT_FAILED.getRespDesc(), new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<Boolean>> deleteProduct(@PathVariable Long productid){
        if(productService.deleteProduct(productid) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),ProductResponseMessage.DELETING_PRODUCT_FAILED.getRespDesc(), false),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),ProductResponseMessage.DELETING_PRODUCT_SUCCESS.getRespDesc(), true),HttpStatus.OK);
    }

    @GetMapping("/v1")
    private ResponseEntity<APIMessage<List<RespProduct>>> findProductList(){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),ProductResponseMessage.FINDING_PRODUCT_SUCCESS.getRespDesc(), productService.findProductList()),HttpStatus.OK);
    }

    @GetMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> findProductInfo(@PathVariable Long productid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),ProductResponseMessage.FINDING_PRODUCT_SUCCESS.getRespDesc(),productService.findProductInfo(productid)),HttpStatus.OK);
    }

    /**
    * 카테고리 등록과 삭제
    * */
    @PostMapping("/v1/category")
    private ResponseEntity<APIMessage<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),ProductResponseMessage.SAVING_CATEGORY_SUCCESS.getRespDesc(), categoryService.createCategory(categoryDto)),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/category/{code}")
    private ResponseEntity<APIMessage<Boolean>> deleteCategory(@PathVariable String code){
        if(categoryService.deleteCategory(code) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),ProductResponseMessage.DELETING_CATEGORY_FAILED.getRespDesc(), false),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),ProductResponseMessage.DELETING_CATEGORY_FAILED.getRespDesc(), true),HttpStatus.OK);
    }
}
