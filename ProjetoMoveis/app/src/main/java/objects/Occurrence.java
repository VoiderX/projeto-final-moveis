package objects;

import java.io.Serializable;
import java.util.Date;

public class Occurrence implements Serializable{
    private Date date;
    private String title;
    private String message;

    public Occurrence(Date date, String title) {
        this.date = date;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
