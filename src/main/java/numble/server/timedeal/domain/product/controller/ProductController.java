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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/v1/products")
    private ResponseEntity<APIMessage<RespProduct>> productCreation(@RequestBody ReqProduct reqProduct){
        if(userService.checkUserRole(reqProduct.getUser_id(), UserEnum.ROLE_ADMIN)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"상품등록성공",productService.productCreation(reqProduct)),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "상품등록실패",new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/v1/products/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> updateProduct(@PathVariable Long productid, @RequestBody ReqProduct reqProduct){
        if(userService.checkUserRole(reqProduct.getUser_id(), UserEnum.ROLE_ADMIN)) {
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품수정성공",productService.updateProduct(productid, reqProduct)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "상품수정실패",new RespProduct()),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/v1/products/{productid}")
    private ResponseEntity<APIMessage<Boolean>> deleteProduct(@PathVariable Long productid){
        if(productService.deleteProduct(productid) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품삭제 실패",false),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품삭제 성공",true),HttpStatus.OK);
    }

    @GetMapping("/v1/products")
    private ResponseEntity<?> productList(){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품리스트",productService.findProductList()),HttpStatus.OK);
    }

    @GetMapping("/v1/products/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> productInfo(@PathVariable Long productid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품조회",productService.findProductInfo(productid)),HttpStatus.OK);
    }

    /**
    * 카테고리 등록과 삭제
    * */
    @PostMapping("/v1/products/category")
    private ResponseEntity<APIMessage<CategoryDto>> categoryCreation(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"카테고리 등록",categoryService.categoryCreation(categoryDto)),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/products/category/{code}")
    private ResponseEntity<APIMessage<Boolean>> deleteCategory(@PathVariable String code){
        if(categoryService.deleteCategory(code) == 0){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"카테고리 삭제 실패",false),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"카테고리 삭제 성공",true),HttpStatus.OK);
    }
}
