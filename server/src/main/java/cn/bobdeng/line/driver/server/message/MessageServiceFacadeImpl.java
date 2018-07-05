package cn.bobdeng.line.driver.server.message;

import cn.bobdeng.line.message.domain.Message;
import cn.bobdeng.line.message.domain.MessageRepository;
import cn.bobdeng.line.message.domain.MessageService;
import cn.bobdeng.line.orgnization.domain.OrgRepository;
import cn.bobdeng.line.orgnization.domain.Orgnization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceFacadeImpl implements MessageServiceFacade {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    OrgRepository orgnizationRepository;

    @Override
    public MessageVO getMessage(int id, int userId) {
        return Optional.ofNullable(messageService.getMessage(id, userId))
                .map(message -> toVO(message))
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    private MessageVO toVO(Message message) {
        MessageVO messageVO = MessageVO.builder().content(message.getFinalContent())
                .createTime(message.getCreateTime())
                .id(message.getId())
                .orgId(message.getOrgId())
                .build();
        messageVO.setOrgName(orgnizationRepository.findById(message.getOrgId()).map(Orgnization::getName).orElse(""));
        return messageVO;
    }

    @Override
    public void confirmMessage(int id, int userId) {
        messageService.confirmMessage(id, userId);
    }
}
