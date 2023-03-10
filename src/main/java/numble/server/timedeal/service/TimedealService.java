package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.product.ProductEntity;
import numble.server.timedeal.domain.product.ProductRepository;
import numble.server.timedeal.domain.timedeal.Timedeal;
import numble.server.timedeal.domain.timedeal.TimedealRepository;
import numble.server.timedeal.dto.request.ReqTimedeal;
import numble.server.timedeal.dto.response.RespTimedeal;
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
        ProductEntity product = productRepository.findByProductId(reqTimedeal.getProduct_id()).get();
        return Timedeal.builder()
                .product(product)
                .limitedAmount(reqTimedeal.getLimited_amount())
                .sale_price(reqTimedeal.getSale_price())
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
        return timedealRepository.findById(timedealId).get();
    }
}
