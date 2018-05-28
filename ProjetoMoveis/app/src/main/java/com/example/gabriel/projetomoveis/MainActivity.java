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
import android.widget.ListView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import objects.Product;

public class MainActivity extends AppCompatActivity {
    ListView productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productsList=findViewById(R.id.productsList);
        dummyPopulateList();
    }
    private void loadProductList(ArrayList<Product> products){

    }
    private ArrayList<Product> dummyPopulateList(){
        ArrayList<Product> dummyProducts = new ArrayList<>();
        for(int i =0; i<10;i++){
            Product product= new Product(new GregorianCalendar(2018,GregorianCalendar.MAY,25),
                    "Product "+i,"Brand "+i,5);
            System.out.println(product);
            dummyProducts.add(product);
        }
        return dummyProducts;
    }
}