package com.example.gabriel.projetomoveis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.GregorianCalendar;

import Utils.ConverterUtils;
import database.DatabaseHandler;
import objects.Product;

public class ProductActivity extends AppCompatActivity {
    private static final int EDIT_MODE = 1;
    private static final int ADD_MODE = 2;
    private static final String MODE = "MODE";
    EditText nameEditText, brandEditText, purchaseEditText, warrantyEditText;
    Button primaryButton, secondaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        startElements();
        Bundle bundle = getIntent().getExtras();
        if (readMode(bundle) == EDIT_MODE) {
            loadProductData(bundle);
            prepareForEdit();
        } else {
            prepareForAdd();
        }
    }
    //Method to read the mode in which the activity was started
    private int readMode(Bundle bundle) {
        int mode;
        mode = bundle.getInt(MODE);
        return mode;
    }

    //Methods to call this activity, if a product is given as argument the activity will edit, otherwise it will add a new product
    public static void call(Context context) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(MODE, ADD_MODE);
        context.startActivity(intent);
    }

    public static void call(Context context, Product product) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(MODE, EDIT_MODE);
        intent.putExtra(MainActivity.PRODUCT_OBJ, product);
        context.startActivity(intent);
    }
    //Methods to set the buttons accordingly with its mode
    private void prepareForAdd() {
        secondaryButton.setVisibility(View.INVISIBLE);
        primaryButton.setText(getResources().getString(R.string.add_product));
        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product= new Product();
                product.setBrand("Samsung");
                product.setName("Galaxy J7");
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(new Date());
                product.setPurchaseDate(gc);
                product.setWarrantyTime(2);
                new DatabaseHandler(getApplication()).insert(product);
            }
        });
    }

    private void prepareForEdit() {
        secondaryButton.setVisibility(View.VISIBLE);
        primaryButton.setText(R.string.save);
        secondaryButton.setText(R.string.add_occurrence);
    }
    //Method to map the interface ids inside the class
    private void startElements() {
        nameEditText = findViewById(R.id.nameEditText);
        brandEditText = findViewById(R.id.brandEditText);
        purchaseEditText = findViewById(R.id.purchaseEditText);
        warrantyEditText = findViewById(R.id.warrantyEditText);

        primaryButton = findViewById(R.id.primaryButton);
        secondaryButton = findViewById(R.id.secondaryButton);
    }

    //Method to load the product data in the fields
    private void loadProductData(Bundle bundle) {
        Product product = (Product) bundle.getSerializable(MainActivity.PRODUCT_OBJ);
        nameEditText.setText(product.getName());
        brandEditText.setText(product.getBrand());
        purchaseEditText.setText(ConverterUtils.convertDateToString(product.getPurchaseDate().getTime()));
        warrantyEditText.setText(Integer.toString(product.getWarrantyTime()));
    }
}