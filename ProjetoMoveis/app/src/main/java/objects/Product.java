package objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.v4.widget.CircularProgressDrawable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import Utils.ConverterUtils;

@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(ConverterUtils.class)
    @ColumnInfo(name="purchaseDate")
    private Date databaseDate;
    @Ignore
    private GregorianCalendar purchaseDate;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "brand")
    private String brand;
    @ColumnInfo(name = "warrantyTime")
    private int warrantyTime;
    @Ignore
    private ArrayList<Occurrence> occurrences;

    public Product(GregorianCalendar purchaseDate, String name, String brand, int warrantyTime) {
        this.purchaseDate = purchaseDate;
        this.name = name;
        this.brand = brand;
        this.warrantyTime = warrantyTime;
        databaseDate=purchaseDate.getTime();
    }

    public Product() {
    }

    public GregorianCalendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(GregorianCalendar purchaseDate) {
        databaseDate=purchaseDate.getTime();
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(int warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public ArrayList<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(ArrayList<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public String getExpirationDate() {
        GregorianCalendar expirationDate =
                new GregorianCalendar(purchaseDate.get(Calendar.YEAR) + warrantyTime, purchaseDate.get(Calendar.MONTH),
                        purchaseDate.get(Calendar.DAY_OF_MONTH));

        return ConverterUtils.convertDateToString(expirationDate.getTime());
        //return "nao tá funfando";
    }

    @Override
    public String toString() {
        String converted;
        converted = "Product: \n" +
                "name: " + name + "\n" +
                "brand: " + brand + "\n" +
                "warranty time: " + warrantyTime + "\n" +
                "purchase date: " + ConverterUtils.convertDateToString(purchaseDate.getTime());
        return converted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatabaseDate() {
        return databaseDate;
    }

    public void setDatabaseDate(Date databaseDate) {
        purchaseDate = new GregorianCalendar();
        this.databaseDate = databaseDate;
        purchaseDate.setTime(databaseDate);
    }
}
