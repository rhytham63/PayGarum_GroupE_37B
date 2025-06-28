package Model;

public class Notification {
    private String message;
    private boolean isRead;

    public Notification(String message, boolean isRead) {
        this.message = message;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}