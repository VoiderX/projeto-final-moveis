package com.example.gabriel.projetomoveis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Utils.ConverterUtils;
import Utils.ImageUtils;
import database.handlers.ProductDAOHandler;
import objects.Product;

public class ProductActivity extends AppCompatActivity {
    private static final int EDIT_MODE = 1;
    private static final int ADD_MODE = 2;
    private static final String MODE = "MODE";

    private static final int IMAGE_INVOICE_REQUEST_CODE = 12;
    private static final int IMAGE_PRODUCT_REQUEST_CODE = 13;

    private EditText nameEditText, brandEditText, purchaseEditText, warrantyEditText;
    private Button primaryButton, secondaryButton;
    private ImageView invoiceImageView, productImageView;

    private String invoiceImageLocation, productImageLocation;

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
        //Enabling UP button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        purchaseEditText.setText(ConverterUtils.convertDateToString(new Date()));
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
                    product.setProductImage(productImageLocation);
                    product.setInvoiceImage(invoiceImageLocation);

                    addProduct(product);
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
        productImageLocation = null;
        invoiceImageLocation = null;

    }

    private void prepareForEdit(final Bundle bundle) {
        setTitle(getString(R.string.manage_product));
        final Product product = loadProductData(bundle);
        secondaryButton.setVisibility(View.VISIBLE);
        primaryButton.setText(R.string.save);
        secondaryButton.setText(R.string.open_occurrences);

        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateFields();
                    product.setWarrantyTime(Integer.parseInt(warrantyEditText.getText().toString()));
                    product.setPurchaseDate(ConverterUtils.convertStringToDate(purchaseEditText.getText().toString()));
                    product.setBrand(brandEditText.getText().toString());
                    product.setName(nameEditText.getText().toString());
                    product.setInvoiceImage(invoiceImageLocation);
                    product.setProductImage(productImageLocation);
                    productImageLocation = product.getProductImage();
                    invoiceImageLocation = product.getInvoiceImage();
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
                openAddOccurences(getIntent().getExtras());
            }
        });


    }

    //Methods to manage data
    private void addProduct(Product product) {
        new ProductDAOHandler(getApplication()).insert(product);
    }

    private void editProduct(Product product) {
        new ProductDAOHandler(getApplication()).update(product);
    }

    //Method to call another activity
    private void openAddOccurences(Bundle bundle) {
        OccurrenceActivity.call(this, (Product) bundle.getSerializable(OccurrenceActivity.PRODUCT_OBJ));
    }

    private void showAbout() {
        AboutActivity.call(this);
    }

    //Method to open full images
    private void openFullImage(String location) {
        Uri imageUri;
        if (location != null) {
            imageUri = Uri.parse(location);
        } else {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(imageUri, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    //Method to map the interface ids inside the class
    private void startElements() {
        nameEditText = findViewById(R.id.nameEditText);
        brandEditText = findViewById(R.id.brandEditText);
        purchaseEditText = findViewById(R.id.purchaseEditText);
        //Date picker configurations
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                purchaseEditText.setText(ConverterUtils.convertDateToString(calendar.getTime()));
            }
        };
        purchaseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                new DatePickerDialog(v.getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)).show();
            }
        });
        //End of date picker configurations
        warrantyEditText = findViewById(R.id.warrantyEditText);

        primaryButton = findViewById(R.id.primaryButton);
        secondaryButton = findViewById(R.id.secondaryButton);

        invoiceImageView = findViewById(R.id.invoiceImageView);
        productImageView = findViewById(R.id.productImageView);

        invoiceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callImages(IMAGE_INVOICE_REQUEST_CODE);
            }
        });
        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callImages(IMAGE_PRODUCT_REQUEST_CODE);
            }
        });
        //Set image views
        productImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openFullImage(productImageLocation);
                return true;
            }
        });
        invoiceImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openFullImage(invoiceImageLocation);
                return true;
            }
        });
    }

    //Method to load the product data in the fields
    private Product loadProductData(Bundle bundle) {
        Product product = (Product) bundle.getSerializable(MainActivity.PRODUCT_OBJ);
        nameEditText.setText(product.getName());
        brandEditText.setText(product.getBrand());
        //System.out.println(product.getInvoiceImage());
        purchaseEditText.setText(ConverterUtils.convertDateToString(product.getPurchaseDate()));
        warrantyEditText.setText(Integer.toString(product.getWarrantyTime()));
        if (product.getProductImage() != null) {
            productImageView.setImageBitmap(ImageUtils.getBitmapFromURI(this, product.getProductImage()));
            productImageLocation = product.getProductImage();
        }
        if (product.getInvoiceImage() != null) {
            invoiceImageView.setImageBitmap(ImageUtils.getBitmapFromURI(this, product.getInvoiceImage()));
            invoiceImageLocation = product.getInvoiceImage();
        }
        return product;
    }

    //Utility method to validate the fields
    private void validateFields() throws FormatException {
        if (purchaseEditText.getText().toString().isEmpty()
                || nameEditText.getText().toString().isEmpty()
                || brandEditText.getText().toString().isEmpty()
                || warrantyEditText.getText().toString().isEmpty()) {
            throw new FormatException();
        }
    }

    //Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutMenuItem:
                showAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Image methods
    private void loadImage(String location, int requestCode) {
        if (requestCode == IMAGE_INVOICE_REQUEST_CODE) {
            invoiceImageView.setImageBitmap(ImageUtils.getBitmapFromURI(this, location));
        } else {
            productImageView.setImageBitmap(ImageUtils.getBitmapFromURI(this, location));
        }
    }

    private void callImages(int requestCode) {
        Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_INVOICE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                loadImage(data.getData().toString(), IMAGE_INVOICE_REQUEST_CODE);
                invoiceImageLocation = data.getData().toString();
            }
        } else {
            if (data != null) {
                loadImage(data.getData().toString(), IMAGE_PRODUCT_REQUEST_CODE);
                productImageLocation = data.getData().toString();
            }
        }
    }
}