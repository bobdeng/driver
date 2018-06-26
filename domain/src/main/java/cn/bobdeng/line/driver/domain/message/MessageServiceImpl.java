package cn.bobdeng.line.driver.domain.message;

import lombok.Setter;

@Setter
public class MessageServiceImpl implements MessageService {
    MessageRepository messageRepository;
    @Override
    public void confirmMessage(String id, int userId) {
        messageRepository.updateConfirmTime(id,userId,System.currentTimeMillis());
        messageRepository.removeMessagePool(id,userId);
    }
}
