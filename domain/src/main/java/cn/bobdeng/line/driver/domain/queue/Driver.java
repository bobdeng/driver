package cn.bobdeng.line.driver.domain.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    private int id;
    private String name;
    private String mobile;
    private String number;
    private String internalNumber;
}
