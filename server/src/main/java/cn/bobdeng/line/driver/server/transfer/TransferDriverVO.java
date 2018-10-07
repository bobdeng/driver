package cn.bobdeng.line.driver.server.transfer;

import cn.bobdeng.line.driver.server.lineup.facade.TruckVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDriverVO {
    private String name;
    private String mobile;
}
