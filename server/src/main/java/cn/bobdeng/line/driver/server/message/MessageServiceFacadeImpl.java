package cn.bobdeng.line.driver.server.message;

import cn.bobdeng.line.driver.domain.message.Message;
import cn.bobdeng.line.driver.domain.message.MessageRepository;
import cn.bobdeng.line.driver.domain.message.MessageService;
import cn.bobdeng.line.driver.domain.org.Orgnization;
import cn.bobdeng.line.driver.domain.org.OrgnizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceFacadeImpl implements MessageServiceFacade {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    OrgnizationRepository orgnizationRepository;

    @Override
    public MessageVO getMessage(int id, int userId) {
        return messageRepository.findByIdAndUserId(id, userId)
                .map(message -> toVO(message))
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    private MessageVO toVO(Message message) {
        MessageVO messageVO = MessageVO.builder().content(message.getFinalContent())
                .type(message.getType())
                .createTime(message.getCreateTime())
                .build();
        messageVO.setOrgName(orgnizationRepository.findById(message.getOrgId()).map(Orgnization::getName).orElse(""));
        return messageVO;
    }

    @Override
    public void confirmMessage(int id, int userId) {
        messageService.confirmMessage(id, userId);
    }
}
