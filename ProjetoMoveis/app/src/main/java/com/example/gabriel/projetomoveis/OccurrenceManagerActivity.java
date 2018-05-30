package com.example.gabriel.projetomoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class OccurrenceManagerActivity extends AppCompatActivity {
    private static final String PRODUCT_OBJ="PRODUCT_OBJ=";
    private static final String OCCURRENCE_OBJ="OCCURRENCE_OBJ=";
    private static final String MODE="MODE";
    private static final int ADD_MODE=1;
    private static final int EDIT_MODE=2;

    private Button primaryButton, secondaryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence_manager);
    }

    private void startElements(){
        primaryButton=findViewById(R.id.primaryOccurrenceButton);
        secondaryButton=findViewById(R.id.secondaryOccurrenceButton);
    }
}
