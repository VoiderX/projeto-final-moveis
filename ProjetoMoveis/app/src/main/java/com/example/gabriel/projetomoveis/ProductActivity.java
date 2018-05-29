package com.example.gabriel.projetomoveis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import Utils.ConverterUtils;
import objects.Product;

public class ProductActivity extends AppCompatActivity {
    EditText nameEditText, brandEditText, purchaseEditText, warrantyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        startElements();
        loadProductData(getIntent().getExtras());
    }


    public static void call(Context context, Product product) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(MainActivity.PRODUCT_OBJ,product);
        context.startActivity(intent);
    }

    private void startElements() {
        nameEditText = findViewById(R.id.nameEditText);
        brandEditText = findViewById(R.id.brandEditText);
        purchaseEditText = findViewById(R.id.purchaseEditText);
        warrantyEditText = findViewById(R.id.warrantyEditText);
    }

    private void loadProductData(Bundle bundle){
        Product product = (Product) bundle.getSerializable(MainActivity.PRODUCT_OBJ);
        nameEditText.setText(product.getName());
        brandEditText.setText(product.getBrand());
        purchaseEditText.setText(ConverterUtils.convertDateToString(product.getPurchaseDate().getTime()));
        warrantyEditText.setText(Integer.toString(product.getWarrantyTime()));
    }
}