package cn.bobdeng.line.driver.server.lineup.facade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverVO {
    private String name;
    private String mobile;
    private boolean allowInput;
    private boolean allowEmpty;
    private TruckVO lastTruck;
    private List<TruckVO> trucks;
}
