package numble.server.timedeal.controller;


import lombok.RequiredArgsConstructor;
import numble.server.timedeal.dto.request.ReqTimedeal;
import numble.server.timedeal.dto.response.RespTimedeal;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.service.TimedealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TimedealController {
    private final TimedealService timedealService;

    /**
     * 타임딜 등록 / 삭제 / 목록
     * */
    @PostMapping("/v1/timedeal")
    private ResponseEntity<APIMessage<RespTimedeal>> timedealCreation(@RequestBody ReqTimedeal reqTimedeal){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.CREATED.toString(),"타임딜 등록",timedealService.createTimedeal(reqTimedeal)),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/timedeal/{timedealid}")
    private ResponseEntity<APIMessage<Boolean>> deleteTimedeal(@PathVariable Long timedealid){
        if(timedealService.deleteTimedeal(timedealid) == 1){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 삭제 성공",true),HttpStatus.OK );
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "타임딜 삭제 실패",false),HttpStatus.OK );
    }

    @GetMapping("/v1/timedeal")
    private ResponseEntity<APIMessage<List<RespTimedeal>>> findTimedealList(){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"타임딜 목록",timedealService.findTimedealList()),HttpStatus.OK);
    }
}
