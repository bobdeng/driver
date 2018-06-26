package cn.bobdeng.line.driver.server.profile;

import cn.bobdeng.line.userclient.SetNameForm;
import cn.bobdeng.line.userclient.UserClient;
import cn.bobdeng.line.userclient.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceFacadeImpl implements ProfileServiceFacade {
    @Autowired
    UserClient userClient;

    @Override
    public void setUserName(UserDTO user, SetProfileForm setProfileForm) {
        userClient.setUserName(SetNameForm.builder()
                .userId(user.getId())
                .name(setProfileForm.getName())
                .build());
    }

    @Override
    public void setUserPush(UserDTO user, SetPushForm setPushForm) {
        cn.bobdeng.line.userclient.SetPushForm form=new cn.bobdeng.line.userclient.SetPushForm();
        form.setUserId(user.getId());
        form.setTokenPush(setPushForm.getType()+":"+setPushForm.getToken());
        userClient.setPush(form);
    }
}
