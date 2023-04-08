package numble.server.timedeal.domain.timedeal.controller;


import lombok.RequiredArgsConstructor;
import numble.server.timedeal.domain.timedeal.dto.ReqTimedeal;
import numble.server.timedeal.domain.timedeal.dto.RespTimedeal;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.domain.timedeal.service.TimedealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "timedeal")
public class TimedealController {
    private final TimedealService timedealService;

    @PostMapping("/v1")
    private ResponseEntity<APIMessage<RespTimedeal>> createTimedeal(@RequestBody ReqTimedeal reqTimedeal){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"타임딜 등록",timedealService.createTimedeal(reqTimedeal)),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/{timedealid}")
    private ResponseEntity<APIMessage<Boolean>> deleteTimedeal(@PathVariable Long timedealid){
        if(timedealService.deleteTimedeal(timedealid) == 1){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 삭제 성공",true),HttpStatus.OK );
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "타임딜 삭제 실패",false),HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/v1")
    private ResponseEntity<APIMessage<List<RespTimedeal>>> findTimedealList(){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"타임딜 목록",timedealService.findTimedealList()),HttpStatus.OK);
    }
}
