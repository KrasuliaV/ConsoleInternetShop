package service;

import model.ChatPM;
import model.User;

import java.util.List;

public interface ChatService {

    void sendMessageToManager(User user, String next);

    List<String> getMassageByClientId(Long clientId);

    List<ChatPM> getChatsWithoutAnswer();

    void sendMessageToCustomer(ChatPM chatPM, String answerToClient);
}
