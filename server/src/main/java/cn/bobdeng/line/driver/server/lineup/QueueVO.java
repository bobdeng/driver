package cn.bobdeng.line.driver.server.lineup;

import lombok.Data;

@Data
public class QueueVO {
    private int order;
    private String name;
    private String number;
    private String internalNumber;
    private String mobile;
}
