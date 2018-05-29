package objects;

import java.io.Serializable;

public class Occurrence implements Serializable{
    private String message;

    public Occurrence(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
