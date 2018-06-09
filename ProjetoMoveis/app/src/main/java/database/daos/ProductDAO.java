package database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import objects.Product;
@Dao
public interface ProductDAO {
    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();
    @Insert
    void insertProducts(Product... products);
    @Delete
    void deleteProducts(Product... products);
    @Update
    void updateProducts(Product... products);
    @Query("SELECT * FROM product WHERE id=:id")
    Product getProduct(int id);
}
