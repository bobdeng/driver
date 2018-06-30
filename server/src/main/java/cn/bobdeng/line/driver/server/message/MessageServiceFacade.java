package cn.bobdeng.line.driver.server.message;

public interface MessageServiceFacade {
    MessageVO getMessage(int id, int userId);

    void confirmMessage(int id, int userId);
}
