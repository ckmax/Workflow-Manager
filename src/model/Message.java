package model;

/**
 * Created by Derek on 12/15/16.
 */
public class Message {
    private final User sender;
    private final User receiver;
    private final String message;

    public Message(User sender, User receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.sender + " to " + this.receiver + ":\n" + this.getMessage();
    }
}
