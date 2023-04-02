package numble.server.timedeal.domain.timedeal.service;

import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.product.repository.ProductEntity;
import numble.server.timedeal.domain.product.repository.ProductRepository;
import numble.server.timedeal.domain.timedeal.repository.Timedeal;
import numble.server.timedeal.domain.timedeal.repository.TimedealRepository;
import numble.server.timedeal.domain.timedeal.dto.ReqTimedeal;
import numble.server.timedeal.domain.timedeal.dto.RespTimedeal;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimedealService {
    private final TimedealRepository timedealRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public RespTimedeal createTimedeal(ReqTimedeal reqTimedeal) {
        Timedeal timedeal = timedealRepository.save(convertToTimedealEntity(reqTimedeal));
        return convertToRespDto(timedeal);
    }

    private RespTimedeal convertToRespDto(Timedeal timedeal){
        return modelMapper.map(timedeal, RespTimedeal.class);
    }
    private Timedeal convertToTimedealEntity(ReqTimedeal reqTimedeal){
        ProductEntity product = productRepository.findByProductId(reqTimedeal.getProduct_id()).orElseThrow();
        return Timedeal.builder()
                .product(product)
                .limitedAmount(reqTimedeal.getLimited_amount())
                .salePrice(reqTimedeal.getSale_price())
                .startDatetime(reqTimedeal.getStart_datetime())
                .build();
    }

    @Transactional
    public int deleteTimedeal(Long timedealid) {
        return timedealRepository.deleteByTimedealId(timedealid);
    }

    public List<RespTimedeal> findTimedealList() {
        List<Timedeal> timedealList = timedealRepository.findAll(Sort.by(Sort.Direction.DESC,"startDatetime"));
        return timedealList.stream().map(timedeal -> convertToRespDto(timedeal)).collect(Collectors.toList());
    }

    public Timedeal findById(Long timedealId){
        return timedealRepository.findById(timedealId).orElseThrow();
    }

    public Timedeal findByIdWithPessimisticLock(Long timedealId){
        return timedealRepository.findByIdWithPessimisticLock(timedealId);
    }

    public Timedeal findByIdWithOptimisticLock(Long timedealId) {
        return timedealRepository.findByIdWithOptimisticLock(timedealId);
    }

    public Timedeal findByIdWithPessimisticLock(Long timedealId){
        return timedealRepository.findByIdWithPessimisticLock(timedealId);
    }

    public Timedeal findByIdWithOptimisticLock(Long timedealId) {
        return timedealRepository.findByIdWithOptimisticLock(timedealId);
    }
}
