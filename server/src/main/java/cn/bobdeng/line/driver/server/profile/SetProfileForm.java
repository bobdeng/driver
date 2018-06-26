package cn.bobdeng.line.driver.server.profile;

import cn.bobdeng.line.driver.server.config.Messages;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SetProfileForm {
    @Length(min=1,max = 10,message = Messages.NAME_LENGTH)
    private String name;
}
