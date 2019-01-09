package android.mbds.fr.appct.api.model;

import java.util.Date;

public class ReceivedMsg {
    private int id;
    private String author;
    private String msg;
    private Date dateCreated;
    private boolean alreadyReturned;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isAlreadyReturned() {
        return alreadyReturned;
    }

    public void setAlreadyReturned(boolean alreadyReturned) {
        this.alreadyReturned = alreadyReturned;
    }
}
