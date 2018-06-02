package com.example.gabriel.projetomoveis;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabriel.projetomoveis.ListAdapters.OccurrenceListAdapter;

import java.util.ArrayList;
import java.util.List;

import Utils.SharedUtils;
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
        SharedUtils.setChosenTheme(this, false);
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

    private void editProduct(Bundle bundle) {
        ProductActivity.call(this, (Product) bundle.getSerializable(PRODUCT_OBJ));
    }

    private void showAbout() {
        AboutActivity.call(this);
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
    private Occurrence[] getAllSelectedOccurrences() {
        Occurrence[] occurrences = new Occurrence[adapter.getSelectedItems().size()];
        for (int i = 0; i < occurrences.length; i++) {
            occurrences[i] = getOccurrences().get(adapter.getSelectedItems().get(i));
        }
        return occurrences;
    }

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
                switch (item.getItemId()) {
                    case R.id.editMenuItem:
                        updateOccurrence(getIntent().getExtras(), adapter.getSelectedItems().get(0));
                        break;
                    case R.id.deleteMenuItem:
                        removeOccurrences(getAllSelectedOccurrences());
                        break;
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.clearSelection();
                adapter.notifyDataSetChanged();
                toolbar.setVisibility(View.VISIBLE);
            }
        };
        return listener;
    }

    //Methods to handle data
    private void removeOccurrences(final Occurrence... occurrences) {
        deleteDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new OccurrenceDAOHandler(getApplication()).delete(occurrences);
            }
        });

    }

    //Menu methods
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected_one_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutMenuItemOccurrence:
                showAbout();
                break;
            case R.id.deleteProductOccurrenceMenuItem:
                deleteProductDialog();
                break;
            case R.id.editProductOccurrenceMenuItem:
                editProduct(getIntent().getExtras());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Dialog
    private void deleteDialog(DialogInterface.OnClickListener action) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.delete_itens);
        alertBuilder.setMessage(R.string.delete_confirm_message);
        alertBuilder.setPositiveButton(R.string.yes, action);
        alertBuilder.setNegativeButton(R.string.no, null);
        alertBuilder.show();
    }

    private void deleteProductDialog() {
        final Product product = (Product) getIntent().getExtras().getSerializable(PRODUCT_OBJ);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(product.getName());
        alertBuilder.setMessage(R.string.delete_this_product);
        alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ProductDAOHandler(getApplication()).delete(product);
                finish();
            }
        });
        alertBuilder.setNegativeButton(R.string.no, null);
        alertBuilder.show();
    }
}
