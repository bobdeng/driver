package cn.bobdeng.line.driver.domain.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    private int id;
    private String name;
    private String mobile;
    private String number;
    private String internalNumber;
}
