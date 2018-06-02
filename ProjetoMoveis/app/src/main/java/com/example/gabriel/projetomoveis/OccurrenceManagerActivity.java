package com.example.gabriel.projetomoveis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Utils.ConverterUtils;
import database.handlers.OccurrenceDAOHandler;
import objects.Occurrence;
import objects.Product;

public class OccurrenceManagerActivity extends AppCompatActivity {
    private static final String PRODUCT_OBJ = "PRODUCT_OBJ=";
    private static final String OCCURRENCE_OBJ = "OCCURRENCE_OBJ=";
    private static final String MODE = "MODE";
    private static final int ADD_MODE = 1;
    private static final int EDIT_MODE = 2;

    private Button primaryButton, secondaryButton;
    private EditText titleEditText, dateEditText, descriptionEditText;

    private Occurrence occurrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence_manager);
        startElements();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt(MODE) == ADD_MODE) {
            prepareForAdd();
        } else {
            prepareForEdit(bundle);
        }

    }

    //Methods to configure and start this activity
    private void startElements() {
        primaryButton = findViewById(R.id.primaryOccurrenceButton);
        secondaryButton = findViewById(R.id.secondaryOccurrenceButton);
        titleEditText = findViewById(R.id.nameOccurrenceEditText);
        dateEditText = findViewById(R.id.dateOccurenceEditText);
        descriptionEditText = findViewById(R.id.descriptionOccurrenceEditText);

        //Date picker configurations
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                dateEditText.setText(ConverterUtils.convertDateToString(calendar.getTime()));
            }
        };
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                new DatePickerDialog(v.getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)).show();
            }
        });
    }

    private void prepareForAdd() {
        dateEditText.setText(ConverterUtils.convertDateToString(new Date()));
        setTitle(R.string.add_occurrence);
        primaryButton.setText(R.string.add_occurrence);
        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Occurrence occurrence = new Occurrence(
                            ConverterUtils.convertStringToDate(dateEditText.getText().toString()),
                            titleEditText.getText().toString(), descriptionEditText.getText().toString(),
                            ((Product) getIntent().getExtras().getSerializable(PRODUCT_OBJ)).getId());
                    addOccurrence(occurrence);
                    finish();
                } catch (ParseException e) {
                    Toast.makeText(OccurrenceManagerActivity.this, R.string.invalid_date, Toast.LENGTH_SHORT).show();
                }
            }
        });
        secondaryButton.setVisibility(View.INVISIBLE);
    }

    private void prepareForEdit(Bundle bundle) {
        setTitle(getString(R.string.manage_occurrence));
        primaryButton.setText(R.string.save_occurrence);
        secondaryButton.setText(R.string.delete_occurrence);
        occurrence = loadOccurrence(bundle);

        primaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Occurrence old = new Occurrence(occurrence.getDate(), occurrence.getTitle(), occurrence.getMessage(), occurrence.getIdOwner());
                try {
                    occurrence.setMessage(descriptionEditText.getText().toString());
                    occurrence.setDate(ConverterUtils.convertStringToDate(dateEditText.getText().toString()));
                    occurrence.setTitle(titleEditText.getText().toString());
                    updateOccurrence(old, occurrence);
                } catch (ParseException e) {
                    Toast.makeText(OccurrenceManagerActivity.this, R.string.invalid_date, Toast.LENGTH_SHORT).show();
                }
            }
        });

        secondaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOccurrence(occurrence);
                finish();
            }
        });
    }

    private Occurrence loadOccurrence(Bundle bundle) {
        Occurrence occurrence = (Occurrence) bundle.getSerializable(OCCURRENCE_OBJ);
        titleEditText.setText(occurrence.getTitle());
        dateEditText.setText(ConverterUtils.convertDateToString(occurrence.getDate()));
        descriptionEditText.setText(occurrence.getMessage());
        return occurrence;
    }

    //Methods to handle data
    private void deleteOccurrence(Occurrence occurrence) {
        new OccurrenceDAOHandler(getApplication()).delete(occurrence);
    }

    private void addOccurrence(Occurrence occurrence) {
        new OccurrenceDAOHandler(getApplication()).insert(occurrence);
    }

    private void updateOccurrence(Occurrence old, Occurrence newer) {
        System.out.println(old);
        System.out.println(newer);
        deleteOccurrence(old);
        addOccurrence(newer);
        finish();
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
        intent.putExtra(MODE, EDIT_MODE);
        intent.putExtra(PRODUCT_OBJ, product);
        intent.putExtra(OCCURRENCE_OBJ, occurrence);
        context.startActivity(intent);
    }

    //Menu methods
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

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

    private void showAbout() {
        AboutActivity.call(this);
    }
}


