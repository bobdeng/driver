package cn.bobdeng.line.driver.server.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPushForm {
    private String token;
    private String type;//ios 极光。。。
}
