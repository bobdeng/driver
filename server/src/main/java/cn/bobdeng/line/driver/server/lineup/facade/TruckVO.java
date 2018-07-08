package cn.bobdeng.line.driver.server.lineup.facade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TruckVO {
    private int id;
    private String number;
    private String internalNumber;
}
