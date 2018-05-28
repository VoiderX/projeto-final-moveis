package objects;

import android.text.format.DateUtils;
import android.util.JsonWriter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import Utils.ConverterUtils;

public class Product {
    private GregorianCalendar purchaseDate;
    private String name;
    private String brand;
    private int warrantyTime;
    private ArrayList<Occurrence> occurrences;

    public Product(GregorianCalendar purchaseDate, String name, String brand, int warrantyTime) {
        this.purchaseDate = purchaseDate;
        this.name = name;
        this.brand = brand;
        this.warrantyTime = warrantyTime;
    }

    public Product() {

    }

    public GregorianCalendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(GregorianCalendar purchaseDate) {
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

    @Override
    public String toString() {
        String converted;
        converted = "Product: \n" +
                "name: " + name + "\n" +
                "brand: " + brand + "\n" +
                "warranty time: " + warrantyTime + "\n" +
                "purchase date: "+ ConverterUtils.convertDateToString(purchaseDate.getTime());
        return converted;
    }
}
