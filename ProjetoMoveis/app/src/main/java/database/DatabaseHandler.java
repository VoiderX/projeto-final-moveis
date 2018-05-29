package database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import database.daos.ProductDAO;
import objects.Product;

public class DatabaseHandler {
    private ProductDAO productDAO;
    private LiveData<List<Product>> allProducts;

    public DatabaseHandler(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productDAO=db.productDAO();
        allProducts=productDAO.getAll();
    }

    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }
    public void insert(Product... products){
        new insertAsyncTask(productDAO).execute(products);
    }
    private static class insertAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDAO productDAO;

        public insertAsyncTask(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDAO.insertAll(products);
            return null;
        }
    }
}
