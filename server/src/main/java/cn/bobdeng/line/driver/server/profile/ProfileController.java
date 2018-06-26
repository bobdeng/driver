package cn.bobdeng.line.driver.server.profile;

import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.rest.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    ProfileServiceFacade profileServiceFacade;
    @GetMapping("/show")
    public CommonResponse showProfile(@RequestAttribute("user")UserDTO user){
        return CommonResponse.getSuccess(user);
    }
    @PostMapping("/set_name")
    public CommonResponse setName(@RequestBody @Valid SetProfileForm setProfileForm, @RequestAttribute("user")UserDTO user, BindingResult bindingResult){
        profileServiceFacade.setUserName(user,setProfileForm);
        return CommonResponse.getSuccess();
    }
    @PostMapping("/set_push")
    public CommonResponse setPush(@RequestBody @Valid SetPushForm setPushForm, @RequestAttribute("user")UserDTO user, BindingResult bindingResult){
        profileServiceFacade.setUserPush(user,setPushForm);
        return CommonResponse.getSuccess();
    }
}
