package numble.server.timedeal.domain.purchase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.product.repository.ProductEntity;
import numble.server.timedeal.domain.product.service.ProductService;
import numble.server.timedeal.domain.purchase.repository.Purchase;
import numble.server.timedeal.domain.purchase.repository.PurchaseRepository;
import numble.server.timedeal.domain.purchase.dto.PurchaseUsersDto;
import numble.server.timedeal.domain.timedeal.repository.Timedeal;
import numble.server.timedeal.domain.user.repository.UserEntity;
import numble.server.timedeal.domain.purchase.dto.ReqPurchase;
import numble.server.timedeal.domain.timedeal.service.TimedealService;
import numble.server.timedeal.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final TimedealService timedealService;
    private final ProductService productService;
    private final UserService userService;

    /**
    *  동시성 처리 예정
    *  타임딜 상품 구매시 : 타임딜 상품 갯수 감소
    * */
    @Transactional
    public boolean purchaseTimedeal(ReqPurchase reqPurchase) {
        Timedeal timedeal = timedealService.findById(reqPurchase.getTimedealId());

        if(isPurchaseAllowed(timedeal, reqPurchase)){
            timedeal.setLimitedAmount(timedeal.getLimitedAmount()-reqPurchase.getCount());

            ProductEntity product = timedeal.getProduct();
            purchaseRepository.save(Purchase.builder()
                    .user(new UserEntity(reqPurchase.getUserId()))
                    .product(product)
                    .count(reqPurchase.getCount())
                    .price(timedeal.getSalePrice())
                    .build());
        }
        return false;
    }

    @Transactional
    public boolean purchaseTimedealWithPessimisticLock(ReqPurchase reqPurchase){
        Timedeal timedeal = timedealService.findByIdWithPessimisticLock(reqPurchase.getTimedealId());

        if(isPurchaseAllowed(timedeal, reqPurchase)) {
            timedeal.setLimitedAmount(timedeal.getLimitedAmount()-reqPurchase.getCount());

            ProductEntity product = timedeal.getProduct();
            purchaseRepository.save(Purchase.builder()
                    .user(new UserEntity(reqPurchase.getUserId()))
                    .product(product)
                    .count(reqPurchase.getCount())
                    .price(timedeal.getSalePrice())
                    .build());
        }
        return false;
    }

    @Transactional
    public boolean purchaseTimedealWithOptimisticLock(ReqPurchase reqPurchase){
        Timedeal timedeal = timedealService.findByIdWithOptimisticLock(reqPurchase.getTimedealId());

        if(isPurchaseAllowed(timedeal, reqPurchase)) {
            timedeal.setLimitedAmount(timedeal.getLimitedAmount() - reqPurchase.getCount());

            ProductEntity product = timedeal.getProduct();
            purchaseRepository.save(Purchase.builder()
                    .user(new UserEntity(reqPurchase.getUserId()))
                    .product(product)
                    .count(reqPurchase.getCount())
                    .price(timedeal.getSalePrice())
                    .build());
        }
        return false;
    }

    /**
    * 상품별 구매한 유저 리스트
    * */
    public PurchaseUsersDto getTimedealPurchasers(Long productid) {
        ProductEntity product = productService.findByProductId(productid);
        List<String> listOfUsersForTimedealPurchase = purchaseRepository.findUsersForTimedealPurchase(product);
        return new PurchaseUsersDto(product.getProductId(), listOfUsersForTimedealPurchase);
    }

    /**
    * 유저가 구매한 상품 리스트 조회
     * select product_id from purchase where user_id = String;
    * */
    public List<Long> getUserPurchasedTimedeal(String userid) {
        return purchaseRepository.findProductsForUserPurchase(userService.findById(userid));
    }

    private boolean isPurchaseAllowed(Timedeal timedeal, ReqPurchase reqPurchase){
        if(isOpenTimeForTimedeal(timedeal) && validatePurchaseQuantity(timedeal, reqPurchase)){
            return true;
        }
        return false;
    }

    private boolean isOpenTimeForTimedeal(Timedeal timedeal){
        if(LocalDateTime.now().compareTo(timedeal.getStartDatetime()) >= 0){
            return true;
        }
        return false;
    }

    private boolean validatePurchaseQuantity(Timedeal timedeal, ReqPurchase reqPurchase){
        if(timedeal.getLimitedAmount()-reqPurchase.getCount() >= 0) {
            return true;
        }
        return false;
    }
}
