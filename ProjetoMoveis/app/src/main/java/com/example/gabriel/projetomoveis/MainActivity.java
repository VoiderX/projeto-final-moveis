/*Controle de Garantia de Produtos

Sistema para controle de garantia de produtos comprados, o usuário pode registrar os equipamentos que adquiriu, e manter controle do tempo de garantia restante de cada.  O aplicativo também poderá realizar o armazenamento da nota fiscal do produto.

-Registro do produto
-Registro do tempo de garantia
-Registro da nota fiscal
-Registro de ocorrências referentes ao produto
*/
package com.example.gabriel.projetomoveis;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabriel.projetomoveis.ListAdapters.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import database.handlers.ProductDAOHandler;
import objects.Product;

public class MainActivity extends AppCompatActivity {
    public static final String PRODUCT_OBJ = "PRODUCT_OBJ";
    private ArrayList<Product> products;
    private ListView productsList;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start application
        startElements();
        startDatabaseObserver();
    }

    //Setting methods
    private void startElements() {
        //Mapping elements
        productsList = findViewById(R.id.productsList);
        //Setting individual element click listener
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOccurrences(products.get(position));
            }
        });
        //Setting multi-choice configuration
        productsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        productsList.setMultiChoiceModeListener(getMultiChoiceModeListener());
        products = new ArrayList<>();
        adapter = new ProductListAdapter(this, products, getString(R.string.expires_on));
        productsList.setAdapter(adapter);
    }


    //Methods to manage data
    private void removeProducts(final Product... products) {
        deleteDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ProductDAOHandler(getApplication()).delete(products);
            }
        });
    }

    //Methods to call another activities
    private void addNewProduct() {
        ProductActivity.call(this);
    }

    private void updateProduct(int position) {
        ProductActivity.call(this, products.get(position));
    }

    private void openOccurrences(Product product) {
        OccurrenceActivity.call(this, product);
    }

    private void showAbout() {
        AboutActivity.call(this);
    }

    //Operational methods
    private void setProducts(List<Product> products) {
        getProducts().clear();
        getProducts().addAll(products);
    }

    private ArrayList<Product> getProducts() {
        return this.products;
    }

    private void startDatabaseObserver() {
        new ProductDAOHandler(this.getApplication()).getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                setProducts(products);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //LIST METHODS
    public Product[] getAllSelectedProducts() {
        Product[] arrayProducts = new Product[adapter.getSelectedItems().size()];
        for (int i = 0; i < adapter.getSelectedItems().size(); i++) {
            arrayProducts[i] = products.get(adapter.getSelectedItems().get(i));
        }
        return arrayProducts;
    }

    //Method to set the multi choice mode on list view
    private AbsListView.MultiChoiceModeListener getMultiChoiceModeListener() {
        AbsListView.MultiChoiceModeListener listener;
        listener = new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                adapter.toggleItemSelection(position);
                adapter.notifyDataSetChanged();
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.selected_one, menu);
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
                    case R.id.deleteMenuItem:
                        removeProducts(getAllSelectedProducts());
                        break;
                    case R.id.editMenuItem:
                        updateProduct(adapter.getSelectedItems().get(0));
                        break;
                    default:
                        return false;
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.clearSelection();
                adapter.notifyDataSetChanged();
            }
        };
        return listener;
    }

    //MENU METHODS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenuItem:
                addNewProduct();
                break;
            case R.id.aboutMenuItem:
                showAbout();
                break;
            default:
                return false;
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

}

