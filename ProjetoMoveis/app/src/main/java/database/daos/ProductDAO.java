package database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import objects.Product;
@Dao
public interface ProductDAO {
    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();
    @Insert
    void insertAll(Product... products);
}
