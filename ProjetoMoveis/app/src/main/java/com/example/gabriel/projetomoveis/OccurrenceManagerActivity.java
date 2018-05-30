package com.example.gabriel.projetomoveis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import objects.Occurrence;
import objects.Product;

public class OccurrenceManagerActivity extends AppCompatActivity {
    private static final String PRODUCT_OBJ = "PRODUCT_OBJ=";
    private static final String OCCURRENCE_OBJ = "OCCURRENCE_OBJ=";
    private static final String MODE = "MODE";
    private static final int ADD_MODE = 1;
    private static final int EDIT_MODE = 2;

    private Button primaryButton, secondaryButton;
    private EditText titleEditText,dateEditText,descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence_manager);
        startElements();
    }

    //Methods to configure and start this activity
    private void startElements() {
        primaryButton = findViewById(R.id.primaryOccurrenceButton);
        secondaryButton = findViewById(R.id.secondaryOccurrenceButton);
        titleEditText= findViewById(R.id.nameOccurrenceEditText);
        dateEditText=findViewById(R.id.dateOccurenceEditText);
        descriptionEditText=findViewById(R.id.descriptionOccurrenceEditText);
    }

    //Methods to call this activity
    public static void call(Context context, Product product) {
        Intent intent = new Intent(context, OccurrenceManagerActivity.class);
        intent.putExtra(MODE, ADD_MODE);
        intent.putExtra(PRODUCT_OBJ, product);
        context.startActivity(intent);
    }

    public static void call(Context context, Product product, Occurrence occurrence) {
        Intent intent = new Intent(context, OccurrenceManagerActivity.class);
        intent.putExtra(MODE, ADD_MODE);
        intent.putExtra(PRODUCT_OBJ, product);
        intent.putExtra(OCCURRENCE_OBJ, occurrence);
        context.startActivity(intent);
    }

}


