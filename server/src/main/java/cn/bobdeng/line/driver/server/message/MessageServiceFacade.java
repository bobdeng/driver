package cn.bobdeng.line.driver.server.message;

public interface MessageServiceFacade {
    MessageVO getMessage(String id, int userId);

    void confirmMessage(String id, int userId);
}
