package dao.impl;

import dao.ChatDao;
import db.HomeDB;
import model.ChatPM;

import java.util.List;
import java.util.stream.Collectors;

public class ChatDaoImpl implements ChatDao {

    private static Long chatId = 0L;

    @Override
    public ChatPM create(ChatPM chatPM) {
        chatPM.setId(++chatId);
        HomeDB.getChatDB().add(chatPM);
        return chatPM;
    }

    @Override
    public ChatPM getById(Long id) {
        return HomeDB.getChatDB().stream()
                .filter(chat -> chat.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ChatPM delete(ChatPM chatPM) {
        HomeDB.getChatDB().remove(chatPM);
        return chatPM;
    }

    @Override
    public ChatPM update(ChatPM chatPM) {
        return HomeDB.getChatDB().set(HomeDB.getChatDB().indexOf(getById(chatPM.getId())), chatPM);
    }

    @Override
    public ChatPM getByClientId(Long id) {
        return HomeDB.getChatDB().stream()
                .filter(chatPM -> chatPM.getCustomerId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ChatPM> getAll() {
        return HomeDB.getChatDB();
    }

    @Override
    public List<ChatPM> getChatsWithoutAnswer() {
        return HomeDB.getChatDB().stream()
                .filter(chatPM -> !chatPM.isAnswered())
                .collect(Collectors.toList());
    }
}
