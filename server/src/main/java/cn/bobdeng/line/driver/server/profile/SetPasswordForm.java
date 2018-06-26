package cn.bobdeng.line.driver.server.profile;

import cn.bobdeng.line.driver.server.config.Messages;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SetPasswordForm {
    @Length(min=6,max = 15,message = Messages.PASSWORD)
    private String password;
    private String olePass;
}
