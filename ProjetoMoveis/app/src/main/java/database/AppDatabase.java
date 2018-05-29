package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import database.daos.ProductDAO;
import objects.Product;

@Database(entities = {Product.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "rmA_database").fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
