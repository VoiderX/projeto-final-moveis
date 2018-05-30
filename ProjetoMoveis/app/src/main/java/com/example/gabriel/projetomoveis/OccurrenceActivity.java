package com.example.gabriel.projetomoveis;

import android.content.Context;
import android.content.Intent;
import android.icu.text.PluralRules;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Utils.ConverterUtils;
import objects.Occurrence;
import objects.Product;

public class OccurrenceActivity extends AppCompatActivity {
    public static final String PRODUCT_OBJ = "PRODUCT_OBJ";
    private static String DATE_KEY = "DATE";
    private static String TITLE_KEY = "TITLE";

    private ListView occurrencesList;
    private ArrayList<Occurrence> occurrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOccurrence(getIntent().getExtras());
            }
        });

        startElements();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Product product = ((Product) getIntent().getExtras().getSerializable(PRODUCT_OBJ));
        setTitle(product.getName());

        generateSampleDate();
        loadOccurrenceList();
    }

    public static void call(Context context, Product product) {
        Intent intent = new Intent(context, OccurrenceActivity.class);
        intent.putExtra(PRODUCT_OBJ, product);
        context.startActivity(intent);
    }

    private void addOccurrence(Bundle bundle) {
        OccurrenceManagerActivity.call(this,(Product) bundle.getSerializable(PRODUCT_OBJ));
    }

    private void loadOccurrenceList() {
        ArrayList<Map<String, String>> formattedData = new ArrayList<>();
        for (Occurrence occurrence : occurrences) {
            Map<String, String> listRow = new HashMap<>();
            listRow.put(DATE_KEY, ConverterUtils.convertDateToString(occurrence.getDate()));
            listRow.put(TITLE_KEY, occurrence.getTitle());
            formattedData.add(listRow);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, formattedData, android.R.layout.simple_list_item_2,
                new String[]{DATE_KEY, TITLE_KEY}, new int[]{android.R.id.text1, android.R.id.text2});
            occurrencesList.setAdapter(simpleAdapter);

    }

    private void startElements() {
        occurrencesList = findViewById(R.id.occurrenceList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void generateSampleDate() {
        occurrences = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            try {
                Occurrence occurrence =new Occurrence(ConverterUtils.convertStringToDate("10/25/2018"), "Occurrence " + i);
                occurrence.setMessage(occurrence.getTitle()+ " Body");
                occurrences.add(occurrence);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
