package objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Utils.ConverterUtils;

@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @TypeConverters(ConverterUtils.class)
    @ColumnInfo(name = "purchaseDate")
    private Date purchaseDate;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "brand")
    private String brand;
    @ColumnInfo(name = "warrantyTime")
    private int warrantyTime;
    @ColumnInfo(name = "invoiceImage")
    private String invoiceImage;
    @ColumnInfo(name = "productImage")
    private String productImage;

    public Product(Date purchaseDate, String name, String brand, int warrantyTime) {
        this.purchaseDate = purchaseDate;
        this.name = name;
        this.brand = brand;
        this.warrantyTime = warrantyTime;
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

    @Override
    public String toString() {
        String converted;
        converted = "Product: \n" +
                "name: " + name + "\n" +
                "brand: " + brand + "\n" +
                "warranty time: " + warrantyTime + "\n" +
                "purchase date: " + ConverterUtils.convertDateToString(purchaseDate);
        return converted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpirationDate() {
        GregorianCalendar temp = new GregorianCalendar();
        temp.setTime(purchaseDate);
        temp.set(Calendar.YEAR, temp.get(Calendar.YEAR) + warrantyTime);
        return ConverterUtils.convertDateToString(temp.getTime());
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
