package com.example.gabriel.projetomoveis;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.icu.text.PluralRules;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.gabriel.projetomoveis.ListAdapters.OccurrenceListAdapter;
import com.example.gabriel.projetomoveis.ListAdapters.ProductListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.ConverterUtils;
import database.handlers.OccurrenceDAOHandler;
import database.handlers.ProductDAOHandler;
import objects.Occurrence;
import objects.Product;

public class OccurrenceActivity extends AppCompatActivity {
    public static final String PRODUCT_OBJ = "PRODUCT_OBJ";
    private ListView occurrencesList;
    private ArrayList<Occurrence> occurrences;
    private OccurrenceListAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);
        //Starting floating button
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOccurrence(getIntent().getExtras());
            }
        });

        startElements();
        //Enabling UP button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Setting title and activating database observer
        Product product = ((Product) getIntent().getExtras().getSerializable(PRODUCT_OBJ));
        setTitle(product.getName());
        startDatabaseObserver(product.getId());
    }

    public static void call(Context context, Product product) {
        Intent intent = new Intent(context, OccurrenceActivity.class);
        intent.putExtra(PRODUCT_OBJ, product);
        context.startActivity(intent);
    }

    //Methods to call another activities
    private void addOccurrence(Bundle bundle) {
        OccurrenceManagerActivity.call(this, (Product) bundle.getSerializable(PRODUCT_OBJ));
    }

    private void updateOccurrence(Bundle bundle, int position) {
        OccurrenceManagerActivity.call(this, (Product) bundle.getSerializable(PRODUCT_OBJ), occurrences.get(position));
    }

    //Method to set this  activity
    private void startElements() {
        occurrencesList = findViewById(R.id.occurrenceList);
        occurrencesList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        occurrencesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateOccurrence(getIntent().getExtras(), position);
            }
        });
        occurrencesList.setMultiChoiceModeListener(getMultiChoiceModeListener());
        occurrences = new ArrayList<>();
        adapter = new OccurrenceListAdapter(this, occurrences);
        occurrencesList.setAdapter(adapter);
    }


    //Operational Methods
    private void setOccurrences(List<Occurrence> occurrences) {
        getOccurrences().clear();
        getOccurrences().addAll(occurrences);
    }

    private ArrayList<Occurrence> getOccurrences() {
        return this.occurrences;
    }

    private void startDatabaseObserver(int id) {
        new OccurrenceDAOHandler(getApplication(), id).getAllOccurrences().observe(this, new Observer<List<Occurrence>>() {
            @Override
            public void onChanged(@Nullable List<Occurrence> occurrences) {
                setOccurrences(occurrences);
                adapter.notifyDataSetChanged();
            }
        });
    }


    //List methods
    private AbsListView.MultiChoiceModeListener getMultiChoiceModeListener() {
        AbsListView.MultiChoiceModeListener listener = new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                adapter.toggleItemSelection(position);
                adapter.notifyDataSetChanged();
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.selected_one, menu);
                toolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                if (adapter.getSelectedItems().size() > 1) {
                    menu.getItem(0).setVisible(false);
                } else {
                    menu.getItem(0).setVisible(true);
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                toolbar.setVisibility(View.VISIBLE);
            }
        };
        return listener;
    }

    //Menu methods
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
