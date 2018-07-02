package cn.bobdeng.line.driver.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private int id;
    private int userId;
    private int orgId;
    private String type;
    private String content;
    private Map<String,String> values;
    private long createTime;
    public String getFinalContent(){
        String result=content;
        Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            result = result .replaceAll("\\{"+entry.getKey()+"\\}",entry.getValue());
        }
        return result;
    }
}
