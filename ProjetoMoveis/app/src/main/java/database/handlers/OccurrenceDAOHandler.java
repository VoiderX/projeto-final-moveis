package database.handlers;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import database.AppDatabase;
import database.daos.OccurrenceDAO;
import objects.Occurrence;

public class OccurrenceDAOHandler {
    private OccurrenceDAO occurrenceDAO;
    private LiveData<List<Occurrence>> allOccurrences;

    public OccurrenceDAOHandler(Application application, int id) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.occurrenceDAO = db.occurrenceDAO();
        allOccurrences=occurrenceDAO.getAllOccurrencesFromProduct(id);
    }

    public LiveData<List<Occurrence>> getAllOccurrences() {
        return allOccurrences;
    }
}
