package cn.bobdeng.line.driver.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private int id;
    private int userId;
    private int orgId;
    private String type;
    private String content;
    private long createTime;
}
