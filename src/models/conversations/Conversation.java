package models.comments;

import java.util.ArrayList;

public class Conversation {
    private static int conversationAmount;
    private final int conversationNumber;
    private final String title;
    private final ArrayList<Comment> comments;
    
    public Conversation(ArrayList<Comment> comments, String title) {
        this.conversationNumber = ++conversationAmount;
        this.title = title;
        this.comments = comments;
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
}
