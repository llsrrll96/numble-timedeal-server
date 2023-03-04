package numble.server.timedeal.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.category.CategoryDto;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.dto.request.ReqProduct;
import numble.server.timedeal.dto.response.RespProduct;
import numble.server.timedeal.service.CategoryService;
import numble.server.timedeal.service.ProductService;
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

    @PostMapping("/v1/products")
    private ResponseEntity<APIMessage<RespProduct>> productCreation(@RequestBody ReqProduct reqProduct){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"상품등록",productService.productCreation(reqProduct)),HttpStatus.CREATED);
    }

    @PutMapping("/v1/products/{productid}")
    private ResponseEntity<APIMessage<RespProduct>> updateProduct(@PathVariable Long productid, @RequestBody ReqProduct reqProduct){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"상품수정",productService.updateProduct(productid, reqProduct)),HttpStatus.OK);

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

    /**
    * 타임딜 등록 / 삭제 / 목록
    * */
}
