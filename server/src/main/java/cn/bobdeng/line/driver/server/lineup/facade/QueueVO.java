package cn.bobdeng.line.driver.server.lineup.facade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueVO {
    private int orderNumber;
    private String name;
    private String number;
    private String internalNumber;
    private String mobile;
    private long beginTime;
}
