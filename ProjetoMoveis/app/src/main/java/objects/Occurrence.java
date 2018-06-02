package objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import Utils.ConverterUtils;
@Entity(primaryKeys ={"idOwner","title"},
        tableName = "occurrence",foreignKeys = @ForeignKey(entity = Product.class,parentColumns = "id",childColumns = "idOwner",onDelete = ForeignKey.CASCADE))
public class Occurrence implements Serializable{
    @TypeConverters(ConverterUtils.class)
    @ColumnInfo(name ="date")
    private Date date;
    @NonNull
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="message")
    private String message;
    @ColumnInfo(name = "idOwner")
    private int idOwner;

    public Occurrence(Date date, String title, String message, int idOwner) {
        this.date = date;
        this.title = title;
        this.message = message;
        this.idOwner = idOwner;
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

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }
}
