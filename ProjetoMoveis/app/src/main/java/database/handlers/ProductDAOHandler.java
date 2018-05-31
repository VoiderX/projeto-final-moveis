package database.handlers;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import database.AppDatabase;
import database.daos.ProductDAO;
import objects.Product;

public class ProductDAOHandler {
    private ProductDAO productDAO;
    private LiveData<List<Product>> allProducts;

    //Prepares the productDAO handler class
    public ProductDAOHandler(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productDAO = db.productDAO();
        allProducts = productDAO.getAll();
    }
    //Methods to manipulate data
    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product... products) {
        new insertAsyncTask(productDAO).execute(products);
    }

    public void delete(Product... products) {
        new removeAsyncTask(productDAO).execute(products);
    }
    public void update(Product... products){
        new updateAsyncTask(productDAO).execute(products);
    }

    //Classes to run database queries using threads
    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO productDAO;

        public insertAsyncTask(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDAO.insertProducts(products);
            return null;
        }
    }

    private static class removeAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDAO productDAO;

        public removeAsyncTask(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDAO.deleteProducts(products);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDAO productDAO;

        public updateAsyncTask(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDAO.updateProducts(products);
            return null;
        }
    }
}
