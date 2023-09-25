package com.v.ad.vadenhancerLibrary.LocalRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.v.ad.vadenhancerLibrary.LocalDao.KeyValueDao;
import com.v.ad.vadenhancerLibrary.LocalDb.VAELD;
import com.v.ad.vadenhancerLibrary.LocalTables.KeyValueTable;

import java.util.List;

public class KeyValueRepository {


    private final KeyValueDao keyValueDao;

    //constructor
    public KeyValueRepository(Application application) {
        VAELD db = VAELD.getCHLDInstance(application);
        keyValueDao = db.keyValueDao();
    }


    public void insert(KeyValueTable keyValueTable) {
        new InsertKeyValueAsyncTask(keyValueDao).execute(keyValueTable);
    }

    public void update(KeyValueTable keyValueTable) {
        new UpdateKeyValueAsyncTask(keyValueDao).execute(keyValueTable);

    }

    public LiveData<List<KeyValueTable>> getAllKeyValuesLive( ) {
        return keyValueDao.getAllKeyValuesLive();
    }

    public List<KeyValueTable> getAllKeyValues( ) {
        return keyValueDao.getAllKeyValues();
    }

    public String getValueWhereKeyIs(String key) {
        return keyValueDao.getValueWhereKeyIs(key);
    }

    public boolean isKeyInKeyValueTable(String key) {
        return keyValueDao.isKeyInKeyValueTable(key);
    }

    public int getIdOfKeyInKeyValueTable(String key) {
        return keyValueDao.getIdOfKeyInKeyValueTable(key);
    }


    public void deleteAllKeyValues() {
        new DeleteAllKeyValuesAsyncTask(keyValueDao).execute();
    }




    private static class InsertKeyValueAsyncTask extends AsyncTask<KeyValueTable, Void, Void> {
        private final KeyValueDao keyValueDao;

        private InsertKeyValueAsyncTask(KeyValueDao keyValueDao) {
            this.keyValueDao = keyValueDao;
        }

        @Override
        protected Void doInBackground(KeyValueTable... keyValueTable) {
            keyValueDao.insert(keyValueTable[0]);
            return null;
        }
    }

    private static class UpdateKeyValueAsyncTask extends AsyncTask<KeyValueTable, Void, Void> {
        private final KeyValueDao keyValueDao;

        private UpdateKeyValueAsyncTask(KeyValueDao keyValueDao) {
            this.keyValueDao = keyValueDao;
        }

        @Override
        protected Void doInBackground(KeyValueTable... keyValueTable) {
            keyValueDao.update(keyValueTable[0]);
            return null;
        }
    }

    private static class DeleteAllKeyValuesAsyncTask extends AsyncTask<KeyValueTable, Void, Void> {
        private final KeyValueDao keyValueDao;

        private DeleteAllKeyValuesAsyncTask(KeyValueDao keyValueDao) {
            this.keyValueDao = keyValueDao;
        }

        @Override
        protected Void doInBackground(KeyValueTable... keyValueTable) {
            keyValueDao.deleteAllKeyValues();
            return null;
        }
    }

}
