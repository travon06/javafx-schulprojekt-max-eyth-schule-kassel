package models.comments;

public class Comment {
    private final String speaker;
    private final String message;

    public Comment(String speaker, String message) {
        this.speaker = speaker;
        this.message = message;
    }

    public String getSpeaker() {
        return this.speaker;
    }

    public String getMessage() {
        return this.message;
    }
}
