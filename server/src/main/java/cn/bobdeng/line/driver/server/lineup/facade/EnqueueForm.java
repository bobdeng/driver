package cn.bobdeng.line.driver.server.lineup.facade;

import lombok.Data;

@Data
public class EnqueueForm {
    private String name;
    private int businessId;
    private String mobile;
    private String number;
    private String internalNumber;
}
