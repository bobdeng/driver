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
    private String content;
    private String orgName;
    private long createTime;
    private String type;
}