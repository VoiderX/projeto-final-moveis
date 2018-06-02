package database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import objects.Occurrence;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface OccurrenceDAO {
    @Query("SELECT * FROM occurrence WHERE idOwner=:id")
    LiveData<List<Occurrence>> getAllOccurrencesFromProduct(int id);
    @Insert(onConflict = REPLACE)
    void insertOccurrences(Occurrence... occurrences);
    @Delete
    void deleteOccurrences(Occurrence... occurrences);
}
