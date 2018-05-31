package com.example.gabriel.projetomoveis.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gabriel.projetomoveis.R;

import java.util.ArrayList;

import objects.Product;

public class ProductListAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private ArrayList<Integer> selectedProducts;
    private String expiresMessage;

    private class LayoutItems {
        TextView productName;
        TextView expirationDate;
    }

    private static LayoutInflater inflater = null;

    public ProductListAdapter(Context context, ArrayList<Product> products, String expiresMessage) {
        Context context1 = context;
        this.products = products;
        inflater = (LayoutInflater) (context.getSystemService(context.LAYOUT_INFLATER_SERVICE));
        selectedProducts = new ArrayList<>();
        this.expiresMessage = expiresMessage;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_product_list_item, null);
        }
        LayoutItems li = new LayoutItems();
        li.productName = convertView.findViewById(R.id.occurrenceTitleListItem);
        li.expirationDate = convertView.findViewById(R.id.occurrenceDateListItem);

        li.productName.setText(products.get(position).getName());
        li.expirationDate.setText(expiresMessage + " " + products.get(position).getExpirationDate());

        changeBackGroundColor(position,convertView);
        return convertView;
    }

    private void changeBackGroundColor(int position, View convertView) {
        if (selectedProducts.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void toggleItemSelection(int position) {
        if (selectedProducts.contains(position)) {
            selectedProducts.remove(new Integer(position));
        } else {
            selectedProducts.add(position);
        }
    }

    public ArrayList<Integer> getSelectedItems() {
        return selectedProducts;
    }

    public void clearSelection() {
        selectedProducts.clear();
    }

}
