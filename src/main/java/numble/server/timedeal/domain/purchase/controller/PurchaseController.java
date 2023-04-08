package numble.server.timedeal.domain.purchase.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.purchase.dto.PurchaseUsersDto;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.domain.purchase.dto.ReqPurchase;
import numble.server.timedeal.domain.purchase.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/v1/pessimistic")
    private ResponseEntity<APIMessage<Boolean>> purchaseTimedealWithPessimisticLock(@RequestBody ReqPurchase reqPurchase){
        if(purchaseService.purchaseTimedealWithPessimisticLock(reqPurchase)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 상품 구매 성공",true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "타임딜 상품 구매 실패",false),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/v1/optimistic")
    private ResponseEntity<APIMessage<Boolean>> purchaseTimedealWithOptimisticLock(@RequestBody ReqPurchase reqPurchase){
        if(purchaseService.purchaseTimedealWithOptimisticLock(reqPurchase)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 상품 구매 성공",true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "타임딜 상품 구매 실패",false),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/v1/product/{productid}")
    private ResponseEntity<APIMessage<PurchaseUsersDto>> getTimedealPurchasers(@PathVariable Long productid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "상품별 구매한 유저리스트",purchaseService.getTimedealPurchasers(productid)),HttpStatus.OK);
    }

    @GetMapping("/v1/user/{userid}")
    private ResponseEntity<APIMessage<List<Long>>> getUserPurchasedTimedeal(@PathVariable String userid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "유저가 구매한 상품 리스트 조회",purchaseService.getUserPurchasedTimedeal(userid)),HttpStatus.OK);
    }
}
