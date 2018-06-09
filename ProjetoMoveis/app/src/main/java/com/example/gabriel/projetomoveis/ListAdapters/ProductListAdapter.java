package com.example.gabriel.projetomoveis.ListAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabriel.projetomoveis.R;

import java.util.ArrayList;

import Utils.ImageUtils;
import objects.Product;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Product> products;

    private ArrayList<CachedProduct> cachedProducts;

    private ArrayList<Integer> selectedProducts;

    private String expiresMessage;

    private class LayoutItems {
        TextView productName;
        TextView expirationDate;
        ImageView productImage;
    }

    private static LayoutInflater inflater = null;

    public ProductListAdapter(Context context, ArrayList<Product> products, String expiresMessage) {
        this.context = context;
        this.products = products;
        inflater = (LayoutInflater) (context.getSystemService(context.LAYOUT_INFLATER_SERVICE));
        selectedProducts = new ArrayList<>();
        cachedProducts = new ArrayList<>();
        this.expiresMessage = expiresMessage;
        new CacheLoader().execute();
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
        li.productName = convertView.findViewById(R.id.productNameListItem);
        li.expirationDate = convertView.findViewById(R.id.productExpirationListItem);
        li.productImage = convertView.findViewById(R.id.productItemImageView);

        li.productName.setText(products.get(position).getName());
        li.expirationDate.setText(expiresMessage + " " + products.get(position).getExpirationDate());

        li.productImage.setImageBitmap(cachedProducts.get(position).imageProduct);
        //new ImageSetter(li.productImage,cachedProducts.get(position).imageProduct).execute();

        li.productName.setTextColor(Color.WHITE);
        li.expirationDate.setTextColor(Color.WHITE);
        changeBackGroundColor(position, convertView);
        return convertView;
    }

    private void changeBackGroundColor(int position, View convertView) {
        if (selectedProducts.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY);
            convertView.findViewById(R.id.selectedMarkListItem).setVisibility(View.VISIBLE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            convertView.findViewById(R.id.selectedMarkListItem).setVisibility(View.INVISIBLE);
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

    public void loadCache() {
        if (cachedProducts.size() == products.size()) {
            for (int i = 0; i < cachedProducts.size(); i++) {
                if (products.get(i).getProductImage() != null && cachedProducts.get(i).product.getProductImage() != null) {
                    if (!products.get(i).getProductImage().equals(cachedProducts.get(i).product.getProductImage())) {
                        cachedProducts.get(i).imageProduct = ImageUtils.getBitmapFromURI(context, products.get(i).getProductImage());
                    }
                } else {
                    if (products.get(i).getProductImage() != cachedProducts.get(i).product.getProductImage()) {
                        cachedProducts.get(i).imageProduct = ImageUtils.getBitmapFromURI(context, products.get(i).getProductImage());
                    }
                }
            }
        } else if (cachedProducts.size() == 0) {
            for (Product product : products) {
                cachedProducts.add(new CachedProduct(product, ImageUtils.getBitmapFromURI(context, product.getProductImage())));

            }
        } else if (cachedProducts.size() > products.size()) {
            //ArrayList<Integer> notDelete = new ArrayList<>();
            //Removing routine
//            for (int i = 0; i < products.size(); i++) {
//                for (int j = 0; j < cachedProducts.size(); j++) {
//                    if (products.get(i).getId() == cachedProducts.get(j).product.getId()) {
//                        notDelete.add(j);
//                    }
//                }
//            }
//            for (int i = 0; i < cachedProducts.size(); i++) {
//                if (!notDelete.contains(i)) {
//                    cachedProducts.remove(i);
//                }
//            }
            cachedProducts.clear();
            for (Product product : products) {
                cachedProducts.add(new CachedProduct(product, ImageUtils.getBitmapFromURI(context, product.getProductImage())));

            }
            //End of removing routine
        } else {
            cachedProducts.add(new CachedProduct(products.get(products.size() - 1), ImageUtils.getBitmapFromURI(context, products.get(products.size() - 1).getProductImage())));
        }
    }

    private class CachedProduct {
        Product product;
        Bitmap imageProduct;

        public CachedProduct(Product product, Bitmap imageProduct) {
            this.product = product;
            this.imageProduct = imageProduct;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        loadCache();
        super.notifyDataSetChanged();
    }

    private class CacheLoader extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            loadCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            notifyDataSetChanged();
        }
    }

//    private class ImageSetter extends AsyncTask<Integer, Void, Void> {
//        ImageView iv;
//        Bitmap bm;
//
//        public ImageSetter(ImageView iv, Bitmap bm) {
//            this.iv = iv;
//            this.bm = bm;
//        }
//
//        @Override
//        protected Void doInBackground(Integer... integers) {
//            setView(iv, bm);
//            return null;
//        }
//
//    }
//
//    private void setView(ImageView iv, Bitmap image) {
//        iv.setImageBitmap(image);
//    }
}
