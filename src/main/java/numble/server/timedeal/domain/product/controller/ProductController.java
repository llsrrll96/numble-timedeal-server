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
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"상품등록성공",productService.createProduct(reqProduct)),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "상품등록실패",new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> updateProduct(@PathVariable Long productid, @RequestBody ReqProduct reqProduct){
        if(isAdminUser(reqProduct.getUserId())) {
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품수정성공",productService.updateProduct(productid, reqProduct)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "상품수정실패",new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<Boolean>> deleteProduct(@PathVariable Long productid){
        if(productService.deleteProduct(productid) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),"상품삭제 실패",false),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품삭제 성공",true),HttpStatus.OK);
    }

    @GetMapping("/v1")
    private ResponseEntity<APIMessage<List<RespProduct>>> findProductList(){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품리스트",productService.findProductList()),HttpStatus.OK);
    }

    @GetMapping("/v1/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> findProductInfo(@PathVariable Long productid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품조회",productService.findProductInfo(productid)),HttpStatus.OK);
    }

    /**
    * 카테고리 등록과 삭제
    * */
    @PostMapping("/v1/category")
    private ResponseEntity<APIMessage<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"카테고리 등록",categoryService.createCategory(categoryDto)),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/category/{code}")
    private ResponseEntity<APIMessage<Boolean>> deleteCategory(@PathVariable String code){
        if(categoryService.deleteCategory(code) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),"카테고리 삭제 실패",false),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"카테고리 삭제 성공",true),HttpStatus.OK);
    }
}
