package cn.bobdeng.line.driver.server.numbers;

import com.tucodec.rest.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/numbers")
public class NumbersController {
    @GetMapping("/list")
    public CommonResponse<List<PhoneNumberVO>> list(){
        return CommonResponse.getSuccess(Arrays.asList(
                PhoneNumberVO.builder().name("秩序宝叫号通知（勿接）").number("057128854560").build()
        ));
    }
}
