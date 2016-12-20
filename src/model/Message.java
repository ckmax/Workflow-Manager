package model;

import java.io.Serializable;

/**
 * Created by Derek on 12/15/16.
 */
public class Message implements Serializable {
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
        try {
            return this.sender.getName() + " to " + this.receiver.getName() + ":\n" + this.getMessage();
        } catch (NullPointerException e) {
            return this.message;
        }
    }
}
