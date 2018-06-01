package database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import objects.Occurrence;

@Dao
public interface OccurrenceDAO {
    @Query("SELECT * FROM occurrence WHERE idOwner=:id")
    LiveData<List<Occurrence>> getAllOccurrencesFromProduct(int id);
    @Insert
    void insertOccurrences(Occurrence... occurrences);
    @Delete
    void deleteOccurrences(Occurrence... occurrences);
}
