package models.conversations;

import java.util.ArrayList;

public class Conversation {
    private static int conversationAmount;
    private final int conversationNumber;
    private final String title;
    private ArrayList<Comment> comments;
    
    public Conversation(String title) {
        this.conversationNumber = ++conversationAmount;
        this.title = title;
    }

    public static int getConversationAmount() {
        return Conversation.conversationAmount;
    }

    public int getConversationNumber() {
        return this.conversationNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
