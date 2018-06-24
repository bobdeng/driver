package cn.bobdeng.line.driver.server.lineup;

import lombok.Data;

@Data
public class EnqueueForm {
    private String name;
    private String mobile;
    private String number;
    private String internalNumber;
}
