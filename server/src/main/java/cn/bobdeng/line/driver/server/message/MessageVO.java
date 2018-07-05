package cn.bobdeng.line.driver.server.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageVO {
    private int id;
    private int orgId;
    private String content;
    private String orgName;
    private long createTime;
}
