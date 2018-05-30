package objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import Utils.ConverterUtils;
@Entity(foreignKeys = @ForeignKey(entity = Product.class,parentColumns = "id",childColumns = "title"))
public class Occurrence implements Serializable{
    @TypeConverters(ConverterUtils.class)
    @ColumnInfo(name ="date")
    private Date date;
    @PrimaryKey
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="message")
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
