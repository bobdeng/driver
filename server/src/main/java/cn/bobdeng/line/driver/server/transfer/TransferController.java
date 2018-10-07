package cn.bobdeng.line.driver.server.transfer;

import cn.bobdeng.line.driver.server.lineup.facade.DriverVO;
import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.rest.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferFacade transferFacade;

    /**
     * 交班功能
     */
    @PostMapping("/transfer")
    public CommonResponse transfer(@RequestAttribute("user") UserDTO user, @RequestBody TransferForm transferForm) {
        transferFacade.transfer(user, transferForm.getOrgId(), transferForm.getMobile());
        return CommonResponse.getSuccess("");
    }

    @GetMapping("/list_driver/{orgId}")
    public CommonResponse<List<DriverVO>> getTransferDrivers(@RequestAttribute("user") UserDTO user, @PathVariable("orgId") int orgId) {
        List<DriverVO> drivers = transferFacade.getDriversCanTransfer(user, orgId);
        return CommonResponse.getSuccess(drivers);
    }
}
