package cn.bobdeng.line.driver.server.message;

import cn.bobdeng.line.db.MessageDO;
import cn.bobdeng.line.driver.domain.message.Message;
import cn.bobdeng.line.driver.domain.message.MessageRepository;
import cn.bobdeng.line.driver.server.dao.MessageDAO;
import cn.bobdeng.line.driver.server.dao.MessagePoolDAO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MessageRepositoryImpl implements MessageRepository {
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    MessagePoolDAO messagePoolDAO;

    @Override
    public Optional<Message> findByIdAndUserId(int id, int userId) {
        return Optional.ofNullable(messageDAO.findByIdAndUserId(id, userId))
                .map(this::fromDO);
    }

    private Message fromDO(MessageDO messageDO) {
        Message message = new Message();
        BeanUtils.copyProperties(messageDO, message);
        message.setValues((Map<String, String>) JSONObject.parse(messageDO.getValues()));
        return message;
    }

    @Override
    public void updateConfirmTime(int id, int userId, long millis) {
        messageDAO.updateConfirmTime(id, userId, millis);
    }

    @Override
    public void removeMessagePool(int id, int userId) {
        messagePoolDAO.deleteByMessageId(id);
    }
}
