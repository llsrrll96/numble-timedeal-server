package numble.server.timedeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.purchase.PurchaseUsersDto;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.dto.request.ReqPurchase;
import numble.server.timedeal.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/v1/purchase")
    private ResponseEntity<APIMessage<Boolean>> purchaseTimedeal(@RequestBody ReqPurchase reqPurchase){
        if(purchaseService.purchaseTimedeal(reqPurchase)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 상품 구매 성공",true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 상품 구매 실패",false),HttpStatus.OK);
    }

    @GetMapping("/v1/purchase/product/{productid}")
    private ResponseEntity<APIMessage<PurchaseUsersDto>> usersForTimedealPurchase(@PathVariable Long productid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "상품별 구매한 유저리스트",purchaseService.usersForTimedealPurchase(productid)),HttpStatus.OK);
    }

    @GetMapping("/v1/purchase/user/{userid}")
    private ResponseEntity<APIMessage<List<Long>>> productsForUserPurchase(@PathVariable String userid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "유저가 구매한 상품 리스트 조회",purchaseService.productsForUserPurchase(userid)),HttpStatus.OK);
    }
}
