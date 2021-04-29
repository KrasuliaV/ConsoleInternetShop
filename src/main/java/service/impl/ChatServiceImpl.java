package service.impl;

import dao.ChatDao;
import dao.impl.ChatDaoImpl;
import model.ChatPM;
import model.User;
import service.ChatService;

import java.util.List;
import java.util.Optional;

public class ChatServiceImpl implements ChatService {

    private final ChatDao chatDao;

    public ChatServiceImpl() {
        this.chatDao = new ChatDaoImpl();
    }

    @Override
    public void sendMessageToManager(User user, String message) {
        ChatPM chatPM = chatDao.getByClientId(user.getId());
        if (chatPM == null) {
            chatDao.create(new ChatPM(user.getId(), user.getUserName(), message));
        } else {
            updatingAnswer(chatPM, message, false);
        }
    }

    @Override
    public List<ChatPM> getChatsWithoutAnswer() {
        return Optional.ofNullable(chatDao.getChatsWithoutAnswer())
                .orElse(null);
    }

    @Override
    public List<String> getMassageByClientId(Long clientId) {
        return Optional.ofNullable(chatDao.getByClientId(clientId))
                .orElse(new ChatPM()).getMessageList();
    }

    @Override
    public void sendMessageToCustomer(ChatPM chatPM, String answerToClient) {
        updatingAnswer(chatPM, answerToClient, true);
    }

    private void updatingAnswer(ChatPM chatPM, String message, boolean status) {
        chatPM.setAnswered(status);
        chatPM.getMessageList().add(message);
        chatDao.update(chatPM);
    }

    public static void main(String[] args) {
        System.out.println(new ChatServiceImpl().getMassageByClientId(1L));
    }
}
