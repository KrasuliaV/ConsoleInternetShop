package dao;

import model.ChatPM;

import java.util.List;

public interface ChatDao extends Dao<ChatPM> {

    ChatPM getByClientId(Long id);

    List<ChatPM> getChatsWithoutAnswer();
}
