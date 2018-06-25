package cn.bobdeng.line.driver.domain.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Queue {
    private int order;
    private int orgId;
    private String name;
    private String number;
    private String internalNumber;
    private String mobile;
}
