package cn.bobdeng.line.driver.server.lineup;

import com.tucodec.rest.CommonResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lineup")
public class LineupController {
    /**
     * 列出可以排队的单位
     * @return
     */
    @GetMapping("/list_org")
    public CommonResponse<List<OrgVO>> listOrg(){
        return null;
    }

    /**
     * 列出正在排队的列表
     * @param orgId
     * @return
     */
    @GetMapping("/list_queue/{id}")
    public CommonResponse<List<QueueVO>> listQueue(@PathVariable("id")int orgId){
        return null;
    }

    /**
     * 入队
     * @param orgId
     * @return
     */
    @PostMapping("/enqueue/{id}")
    public CommonResponse enqueue(@PathVariable("id")int orgId, @RequestBody @Valid EnqueueForm enqueueForm){
        return null;
    }

}
