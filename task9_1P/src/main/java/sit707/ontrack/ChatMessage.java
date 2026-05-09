package sit707.ontrack;

import java.util.Objects;

public class ChatMessage {
    private final String sender;
    private final String message;

    public ChatMessage(String sender, String message) {
        if (sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException("sender is required");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("message is required");
        }
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChatMessage)) {
            return false;
        }
        ChatMessage that = (ChatMessage) other;
        return Objects.equals(sender, that.sender)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, message);
    }
}
