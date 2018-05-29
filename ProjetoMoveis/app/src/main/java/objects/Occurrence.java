package objects;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Occurrence implements Serializable{
    private GregorianCalendar date;
    private String title;
    private String message;

    public Occurrence(GregorianCalendar date, String message) {
        this.date = date;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
