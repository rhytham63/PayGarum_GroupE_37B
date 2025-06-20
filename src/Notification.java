/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author utpre
 */
public class Notification {
    private final int id;
    private final String message;
    private boolean isRead;
    
    public Notification(int id, String message, boolean isRead) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
    }
    
    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }
}
    
