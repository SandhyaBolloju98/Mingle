package com.v.ad.vadenhancerLibrary.LocalRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.v.ad.vadenhancerLibrary.LocalDao.AdNetworksOrderDao;
import com.v.ad.vadenhancerLibrary.LocalDb.VAELD;
import com.v.ad.vadenhancerLibrary.LocalTables.AdNetworksOrderTable;

import java.util.List;

public class AdNetworkOrderRepository {


    private final AdNetworksOrderDao adNetworksOrderDao;

    //constructor
    public AdNetworkOrderRepository(Application application) {
        VAELD db = VAELD.getCHLDInstance(application);
        adNetworksOrderDao = db.adNetworksOrderDao();
    }


    public void insert(AdNetworksOrderTable adNetworksOrderTable) {
        new InsertAdNetworkOrderAsyncTask(adNetworksOrderDao).execute(adNetworksOrderTable);
    }

    public void update(AdNetworksOrderTable adNetworksOrderTable) {
        new UpdateAdNetworkOrderAsyncTask(adNetworksOrderDao).execute(adNetworksOrderTable);

    }

    public LiveData<List<AdNetworksOrderTable>> getAllAdNetworkOrdersLive( ) {
        return adNetworksOrderDao.getAllAdNetworkOrdersLive();
    }

    public List<AdNetworksOrderTable> getAllAdNetworkOrders( ) {
        return adNetworksOrderDao.getAllAdNetworkOrders();
    }

    public int getPreferenceOfAdNetworkWithAdType(String adnetwork, String adtype) {
        return adNetworksOrderDao.getPreferenceOfAdNetworkWithAdType(adnetwork,adtype);
    }

    public boolean isAdNetworkInAdNetworkOrderTable(String adnetwork, String adtype) {
        return adNetworksOrderDao.isAdNetworkOfAdTypeInAdNetworkOrderTable(adnetwork, adtype);
    }

    public int getIdOfAdNetworkInAdNetworkOrderTable(String adnetwork, String adtype) {
        return adNetworksOrderDao.getIdOfAdNetworkOfAdTypeInAdNetworkOrderTable(adnetwork, adtype);
    }


    public void deleteAllAdNetworkOrders() {
        new DeleteAllKeyValuesAsyncTask(adNetworksOrderDao).execute();
    }




    private static class InsertAdNetworkOrderAsyncTask extends AsyncTask<AdNetworksOrderTable, Void, Void> {
        private final AdNetworksOrderDao adNetworksOrderDao;

        private InsertAdNetworkOrderAsyncTask(AdNetworksOrderDao adNetworksOrderDao) {
            this.adNetworksOrderDao = adNetworksOrderDao;
        }

        @Override
        protected Void doInBackground(AdNetworksOrderTable... adNetworksOrderTable) {
            adNetworksOrderDao.insert(adNetworksOrderTable[0]);
            return null;
        }
    }

    private static class UpdateAdNetworkOrderAsyncTask extends AsyncTask<AdNetworksOrderTable, Void, Void> {
        private final AdNetworksOrderDao adNetworksOrderDao;

        private UpdateAdNetworkOrderAsyncTask(AdNetworksOrderDao adNetworksOrderDao) {
            this.adNetworksOrderDao = adNetworksOrderDao;
        }

        @Override
        protected Void doInBackground(AdNetworksOrderTable... adNetworksOrderTable) {
            adNetworksOrderDao.update(adNetworksOrderTable[0]);
            return null;
        }
    }

    private static class DeleteAllKeyValuesAsyncTask extends AsyncTask<AdNetworksOrderTable, Void, Void> {
        private final AdNetworksOrderDao adNetworksOrderDao;

        private DeleteAllKeyValuesAsyncTask(AdNetworksOrderDao adNetworksOrderDao) {
            this.adNetworksOrderDao = adNetworksOrderDao;
        }

        @Override
        protected Void doInBackground(AdNetworksOrderTable... adNetworksOrderTable) {
            adNetworksOrderDao.deleteAllAdNetworkOrders();
            return null;
        }
    }

    
}
