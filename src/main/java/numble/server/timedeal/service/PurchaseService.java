package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.product.ProductEntity;
import numble.server.timedeal.domain.purchase.Purchase;
import numble.server.timedeal.domain.purchase.PurchaseRepository;
import numble.server.timedeal.domain.purchase.PurchaseUsersDto;
import numble.server.timedeal.domain.timedeal.Timedeal;
import numble.server.timedeal.domain.user.UserEntity;
import numble.server.timedeal.dto.request.ReqPurchase;
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
        Timedeal timedeal = timedealService.findById(reqPurchase.getTimedeal_id());
        log.info("현재남은 재고: {}",timedeal.getLimitedAmount());
        if(timedeal.getLimitedAmount()-reqPurchase.getCount() < 0) return false;
        timedeal.setLimitedAmount(timedeal.getLimitedAmount()-reqPurchase.getCount());

        ProductEntity product = timedeal.getProduct();
        purchaseRepository.save(Purchase.builder()
                .user(new UserEntity(reqPurchase.getUser_id()))
                .product(product)
                .count(reqPurchase.getCount())
                .price(timedeal.getSale_price())
                .build());
        return true;
    }

    @Transactional
    public boolean purchaseTimedealWithPessimisticLock(ReqPurchase reqPurchase){
        Timedeal timedeal = timedealService.findByIdWithPessimisticLock(reqPurchase.getTimedeal_id());

        if(!isOpenTimeForTimedeal(timedeal) || timedeal.getLimitedAmount()-reqPurchase.getCount() < 0) return false;
        timedeal.setLimitedAmount(timedeal.getLimitedAmount()-reqPurchase.getCount());

        ProductEntity product = timedeal.getProduct();
        purchaseRepository.save(Purchase.builder()
                .user(new UserEntity(reqPurchase.getUser_id()))
                .product(product)
                .count(reqPurchase.getCount())
                .price(timedeal.getSale_price())
                .build());
        return true;
    }

    @Transactional
    public boolean purchaseTimedealWithOptimisticLock(ReqPurchase reqPurchase){
        Timedeal timedeal = timedealService.findByIdWithOptimisticLock(reqPurchase.getTimedeal_id());

        if(!isOpenTimeForTimedeal(timedeal) || timedeal.getLimitedAmount()-reqPurchase.getCount() < 0) return false;
        timedeal.setLimitedAmount(timedeal.getLimitedAmount()-reqPurchase.getCount());

        ProductEntity product = timedeal.getProduct();
        purchaseRepository.save(Purchase.builder()
                .user(new UserEntity(reqPurchase.getUser_id()))
                .product(product)
                .count(reqPurchase.getCount())
                .price(timedeal.getSale_price())
                .build());
        return true;
    }

    /**
    * 상품별 구매한 유저 리스트
    * */
    public PurchaseUsersDto usersForTimedealPurchase(Long productid) {
        ProductEntity product = productService.findByProductId(productid);
        List<String> listOfUsersForTimedealPurchase = purchaseRepository.findUsersForTimedealPurchase(product);
        return new PurchaseUsersDto(product.getProductId(), listOfUsersForTimedealPurchase);
    }

    /**
    * 유저가 구매한 상품 리스트 조회
     * select product_id from purchase where user_id = String;
    * */
    public List<Long> productsForUserPurchase(String userid) {
        return purchaseRepository.findProductsForUserPurchase(userService.findById(userid));
    }

    private boolean isOpenTimeForTimedeal(Timedeal timedeal){
        if(LocalDateTime.now().compareTo(timedeal.getStartDatetime()) >= 0){
            return true;
        }
        return false;
    }
}
