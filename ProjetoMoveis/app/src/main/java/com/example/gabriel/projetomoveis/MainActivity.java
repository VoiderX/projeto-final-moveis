/*Controle de Garantia de Produtos

Sistema para controle de garantia de produtos comprados, o usuário pode registrar os equipamentos que adquiriu, e manter controle do tempo de garantia restante de cada.  O aplicativo também poderá realizar o armazenamento da nota fiscal do produto.

-Registro do produto
-Registro do tempo de garantia
-Registro da nota fiscal
-Registro de ocorrências referentes ao produto
*/
package com.example.gabriel.projetomoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.net.ConnectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import Utils.ConverterUtils;
import objects.Product;

public class MainActivity extends AppCompatActivity {
    private static final String PRODUCT_KEY="productName";
    private static final String DATE_KEY="remainingWarranty";
    private ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productsList = findViewById(R.id.productsList);
        loadProductList(dummyPopulateList());
    }

    private void loadProductList(ArrayList<Product> products) {
        ArrayList<Map<String, String>> formattedData = new ArrayList<>();
        for (Product product : products){
            Map<String,String> listRow = new HashMap<>(2);
            listRow.put(PRODUCT_KEY,product.getName());
            listRow.put(DATE_KEY, getResources().getString(R.string.expires_on) + " "+ product.getExpirationDate());
            formattedData.add(listRow);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,formattedData,android.R.layout.simple_list_item_2,
                new String[]{PRODUCT_KEY,DATE_KEY},new int[]{android.R.id.text1,android.R.id.text2});
        productsList.setAdapter(simpleAdapter);
    }

    private ArrayList<Product> dummyPopulateList() {
        ArrayList<Product> dummyProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product(new GregorianCalendar(2018, GregorianCalendar.MAY, 30),
                    "Product " + i, "Brand " + i, 5);
            System.out.println(product);
            dummyProducts.add(product);
        }
        return dummyProducts;
    }
}