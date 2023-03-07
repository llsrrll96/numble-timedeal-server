package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.purchase.PurchaseUsersDto;
import numble.server.timedeal.dto.request.ReqPurchase;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PurchaseService {

    public boolean purchaseTimedeal(ReqPurchase reqPurchase) {
        return true;
    }

    /**
    * 상품별 구매한 유저 리스트
     * select user_id from purchase where product_id = 1;
    * */
    public PurchaseUsersDto usersForTimedealPurchase(Long productid) {
        return null;
    }

    /**
    * 유저가 구매한 상품 리스트 조회
     * select product_id from purchase where user_id = String;
    * */
    public List<Long> productsForUserPurchase(String userid) {
        return null;
    }
}
