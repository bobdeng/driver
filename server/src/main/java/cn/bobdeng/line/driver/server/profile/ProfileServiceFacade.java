package cn.bobdeng.line.driver.server.profile;

import cn.bobdeng.line.userclient.UserDTO;

public interface ProfileServiceFacade {
    void setUserName(UserDTO user, SetProfileForm setProfileForm);

    void setUserPush(UserDTO user, SetPushForm setPushForm);

    void setUserPass(UserDTO user, SetPasswordForm setPasswordForm);
}
