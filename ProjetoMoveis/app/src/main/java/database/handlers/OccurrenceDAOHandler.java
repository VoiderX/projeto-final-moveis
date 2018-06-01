package database.handlers;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import database.AppDatabase;
import database.daos.OccurrenceDAO;
import database.daos.ProductDAO;
import objects.Occurrence;

public class OccurrenceDAOHandler {
    private OccurrenceDAO occurrenceDAO;
    private LiveData<List<Occurrence>> allOccurrences;

    public OccurrenceDAOHandler(Application application, int id) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.occurrenceDAO = db.occurrenceDAO();
        allOccurrences = occurrenceDAO.getAllOccurrencesFromProduct(id);
    }

    public OccurrenceDAOHandler(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.occurrenceDAO = db.occurrenceDAO();
    }

    public LiveData<List<Occurrence>> getAllOccurrences() {
        return allOccurrences;
    }

    public void insert(Occurrence... occurrences) {
        new insertAsyncTask(occurrenceDAO).execute(occurrences);
    }

    public void delete(Occurrence... occurrences) {
        new deleteAsyncTask(occurrenceDAO).execute(occurrences);
    }

    private static class insertAsyncTask extends AsyncTask<Occurrence, Void, Void> {
        private OccurrenceDAO occurrenceDAO;

        public insertAsyncTask(OccurrenceDAO occurrenceDAO) {
            this.occurrenceDAO = occurrenceDAO;
        }

        @Override
        protected Void doInBackground(Occurrence... occurrences) {
            occurrenceDAO.insertOccurrences(occurrences);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Occurrence, Void, Void> {
        private OccurrenceDAO occurrenceDAO;

        public deleteAsyncTask(OccurrenceDAO occurrenceDAO) {
            this.occurrenceDAO = occurrenceDAO;
        }

        @Override
        protected Void doInBackground(Occurrence... occurrences) {
            occurrenceDAO.deleteOccurrences(occurrences);
            return null;
        }
    }


}
