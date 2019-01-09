package android.mbds.fr.appct.models;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {

    private String messageTxt;
    private String recipientUsername;
    private long messageTime; // a revoir

    public ChatMessage(String messageTxt, String recipientUsername){
        this.messageTxt = messageTxt;
        this.recipientUsername = recipientUsername;

        messageTime = new Date().getTime();
    }

    public ChatMessage(){}

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String toString(){
        return "[destinataire: " + this.recipientUsername + ", msg: "+ this.messageTxt + "]";
    }
}
