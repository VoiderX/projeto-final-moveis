/*Controle de Garantia de Produtos

Sistema para controle de garantia de produtos comprados, o usuário pode registrar os equipamentos que adquiriu, e manter controle do tempo de garantia restante de cada.  O aplicativo também poderá realizar o armazenamento da nota fiscal do produto.

-Registro do produto
-Registro do tempo de garantia
-Registro da nota fiscal
-Registro de ocorrências referentes ao produto
*/
package com.example.gabriel.projetomoveis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import objects.Product;

public class MainActivity extends AppCompatActivity {
    public static final String PRODUCT_OBJ = "PRODUCT_OBJ";
    private static final String PRODUCT_KEY = "productName";
    private static final String DATE_KEY = "remainingWarranty";
    private ArrayList<Product> products=null;
    private ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Mapping elements
        productsList = findViewById(R.id.productsList);
        //Setting individual element click listener
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductActivity.call(view.getContext(),products.get(position));
            }
        });
        //Setting multi-choice configuration
        productsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        productsList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (productsList.isItemChecked(position)) {
                    productsList.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                } else {
                    productsList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.selected_one, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                if (productsList.getCheckedItemCount() > 1) {
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
                        removeProduct();
                        break;
                    case R.id.editMenuItem:
                        updateProduct();
                        break;
                    default:
                        return false;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for (int i = 0; i < productsList.getChildCount(); i++) {
                    productsList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        products= dummyPopulateList();
        loadProductList();
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

    private void addNewProduct() {
        System.out.println("Adicionando produto!");
    }

    private void removeProduct() {
        System.out.println("Removendo produto");
    }

    private void updateProduct() {
        System.out.println("Atualizando produto");
    }

    private void showAbout() {
        System.out.println("Show about!");
    }

    //LIST METHODS
    //method to load the list from an array of products
    private void loadProductList() {
        ArrayList<Map<String, String>> formattedData = new ArrayList<>();
        for (Product product : products) {
            Map<String, String> listRow = new HashMap<>(2);
            listRow.put(PRODUCT_KEY, product.getName());
            listRow.put(DATE_KEY, getResources().getString(R.string.expires_on) + " " + product.getExpirationDate());
            formattedData.add(listRow);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, formattedData, android.R.layout.simple_list_item_2,
                new String[]{PRODUCT_KEY, DATE_KEY}, new int[]{android.R.id.text1, android.R.id.text2});
        productsList.setAdapter(simpleAdapter);
    }

    //basic method to generate sample data
    private ArrayList<Product> dummyPopulateList() {
        ArrayList<Product> dummyProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product(new GregorianCalendar(2018, GregorianCalendar.MAY, 30),
                    "Product " + i, "Brand " + i, 5);
            dummyProducts.add(product);
        }
        return dummyProducts;
    }
}