package com.example.gabriel.projetomoveis;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import Utils.ConverterUtils;
import database.ProductDAOHandler;
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
            prepareForEdit(bundle);
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
        setTitle(R.string.add_product);
        secondaryButton.setVisibility(View.INVISIBLE);
        primaryButton.setText(getResources().getString(R.string.add_product));
        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateFields();
                    Product product =
                            new Product(ConverterUtils.convertStringToDate(purchaseEditText.getText().toString()),
                                    nameEditText.getText().toString(), brandEditText.getText().toString(),
                                    Integer.parseInt(warrantyEditText.getText().toString()));
                    new ProductDAOHandler(getApplication()).insert(product);
                    finish();
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), R.string.invalid_date, Toast.LENGTH_SHORT).show();
                } catch (FormatException e) {
                    Toast.makeText(v.getContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), R.string.unknow_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void prepareForEdit(Bundle bundle) {
        setTitle(R.string.edit_product);
        final Product product = loadProductData(bundle);
        secondaryButton.setVisibility(View.VISIBLE);
        primaryButton.setText(R.string.save);
        secondaryButton.setText(R.string.add_occurrence);

        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateFields();
                    product.setWarrantyTime(Integer.parseInt(warrantyEditText.getText().toString()));
                    product.setPurchaseDate(ConverterUtils.convertStringToDate(purchaseEditText.getText().toString()));
                    product.setBrand(brandEditText.getText().toString());
                    product.setName(nameEditText.getText().toString());
                    editProduct(product);
                    finish();
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), R.string.invalid_date, Toast.LENGTH_SHORT).show();
                } catch (FormatException e) {
                    Toast.makeText(v.getContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), R.string.unknow_error, Toast.LENGTH_SHORT).show();
                }

            }
        });

        secondaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddOccurences();
            }
        });
    }

    public void editProduct(Product product) {
        System.out.println("Edit product!");
        new ProductDAOHandler(getApplication()).update(product);
    }

    private void openAddOccurences() {
        System.out.println("Open Occurrences");
    }

    private void validateFields() throws FormatException {
        if (purchaseEditText.getText().toString().isEmpty()
                || nameEditText.getText().toString().isEmpty()
                || brandEditText.getText().toString().isEmpty()
                || warrantyEditText.getText().toString().isEmpty()) {
            throw new FormatException();
        }
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
    private Product loadProductData(Bundle bundle) {
        Product product = (Product) bundle.getSerializable(MainActivity.PRODUCT_OBJ);
        nameEditText.setText(product.getName());
        brandEditText.setText(product.getBrand());
        purchaseEditText.setText(ConverterUtils.convertDateToString(product.getPurchaseDate()));
        warrantyEditText.setText(Integer.toString(product.getWarrantyTime()));
        return product;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}