package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.driver.server.lineup.facade.*;
import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.rest.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lineup")
public class LineupController {
    @Autowired
    LineupServiceFacade lineupServiceFacade;

    /**
     * 列出可以排队的单位
     */
    @GetMapping("/list_org")
    public CommonResponse<List<OrgVO>> listOrg(@RequestAttribute("user") UserDTO user) {
        return CommonResponse.getSuccess(lineupServiceFacade.findOrgs(user.getMobile()));
    }

    /**
     * 列出正在排队的列表
     */
    @GetMapping("/list_queue/{id}")
    public CommonResponse<List<QueueVO>> listQueue(@RequestAttribute("user") UserDTO user, @PathVariable("id") int orgId) {
        return CommonResponse.getSuccess(lineupServiceFacade.listQueue(orgId));
    }

    /**
     * 入队
     */
    @PostMapping("/enqueue/{id}")
    public CommonResponse<EnQueueResult> enqueue(@RequestAttribute("user") UserDTO user,
                                                 @PathVariable("id") int orgId,
                                                 @RequestBody @Valid EnqueueForm enqueueForm) {
        return CommonResponse.getSuccess(lineupServiceFacade.enqueue(user, orgId, enqueueForm));
    }

}
