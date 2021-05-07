package model;

import java.util.ArrayList;
import java.util.List;

public class ChatPM extends AbstractEntity {

    private long clientId;
    private String clientName;
    private boolean isAnswered;
    private List<String> messageList;

    public ChatPM() {
        messageList = new ArrayList<>();
    }

    public ChatPM(long clientId, String clientName, String message) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.isAnswered = false;
        this.messageList = new ArrayList<>(List.of(message));
    }

    public long getCustomerId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getCustomerName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public List<String> getMessageList() {
        return messageList;
    }

}
