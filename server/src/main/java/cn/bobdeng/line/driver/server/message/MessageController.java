package cn.bobdeng.line.driver.server.message;

import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.rest.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageServiceFacade messageServiceFacade;

    @GetMapping("/{id}")
    public CommonResponse<MessageVO> getMessage(@PathVariable("id") int id, @RequestAttribute("user") UserDTO user) {
        return CommonResponse.getSuccess(messageServiceFacade.getMessage(id, user.getId()));
    }

    @PostMapping("/confirm/{id}")
    public CommonResponse confirmMessage(@PathVariable("id") int id, @RequestAttribute("user") UserDTO user) {
        messageServiceFacade.confirmMessage(id, user.getId());
        return CommonResponse.getSuccess();
    }
    @GetMapping("/pull")
    public CommonResponse<List<MessageVO>> pullMessage(@RequestAttribute("user") UserDTO user,@RequestParam("last")long last){
        return CommonResponse.getSuccess(messageServiceFacade.pullMessage(user.getId(),last));
    }
}
