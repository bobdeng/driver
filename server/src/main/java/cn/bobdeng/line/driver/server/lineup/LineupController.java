package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.rest.CommonResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lineup")
public class LineupController {
    /**
     * 列出可以排队的单位
     */
    @GetMapping("/list_org")
    public CommonResponse<List<OrgVO>> listOrg(@ModelAttribute("user")UserDTO user){
        return null;
    }

    /**
     * 列出正在排队的列表
     */
    @GetMapping("/list_queue/{id}")
    public CommonResponse<List<QueueVO>> listQueue(@ModelAttribute("user")UserDTO user,@PathVariable("id")int orgId){
        return null;
    }

    /**
     * 入队
     */
    @PostMapping("/enqueue/{id}")
    public CommonResponse enqueue(@ModelAttribute("user")UserDTO user,@PathVariable("id")int orgId, @RequestBody @Valid EnqueueForm enqueueForm){
        return null;
    }

}
