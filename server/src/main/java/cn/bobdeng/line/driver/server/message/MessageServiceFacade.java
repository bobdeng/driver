package cn.bobdeng.line.driver.server.message;

import java.util.List;

public interface MessageServiceFacade {
    MessageVO getMessage(int id, int userId);

    void confirmMessage(int id, int userId);

    List<MessageVO> pullMessage(int userId, long last);
}
