package com.v.ad.vadenhancerLibrary.LocalDb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.v.ad.vadenhancerLibrary.LocalDao.AdNetworksOrderDao;
import com.v.ad.vadenhancerLibrary.LocalDao.KeyValueDao;
import com.v.ad.vadenhancerLibrary.LocalTables.AdNetworksOrderTable;
import com.v.ad.vadenhancerLibrary.LocalTables.KeyValueTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {KeyValueTable.class, AdNetworksOrderTable.class},
        version = 3,
        exportSchema = false)

public abstract class VAELD extends RoomDatabase {

    private static volatile VAELD CHLDINSTANCE;

    public abstract KeyValueDao keyValueDao();

    public abstract AdNetworksOrderDao adNetworksOrderDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized VAELD getCHLDInstance(Context context) {
        if (CHLDINSTANCE == null) {
            CHLDINSTANCE = Room.databaseBuilder(context.getApplicationContext(), VAELD.class, "VAELD")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return CHLDINSTANCE;
    }

    private static final Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new populateDbAsyncTask(CHLDINSTANCE).execute();
            super.onCreate(db);
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private final KeyValueDao keyValueDao;

        private populateDbAsyncTask(VAELD db) {
            keyValueDao = db.keyValueDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           /* chats_dao.insert( new Chats_Table(1, "username", "profile_photo", true, "received", 123,false));
            chats_dao.insert( new Chats_Table(2, "username", "profile_photo", true, "received", 456,true));
           */
            return null;
        }
    }
}
