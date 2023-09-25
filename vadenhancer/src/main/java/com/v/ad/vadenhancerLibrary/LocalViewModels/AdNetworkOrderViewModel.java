package com.v.ad.vadenhancerLibrary.LocalViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.v.ad.vadenhancerLibrary.LocalRepositories.AdNetworkOrderRepository;
import com.v.ad.vadenhancerLibrary.LocalTables.AdNetworksOrderTable;

import java.util.List;

public class AdNetworkOrderViewModel extends AndroidViewModel {


    private final AdNetworkOrderRepository adNetworkOrderRepository;

    public AdNetworkOrderViewModel(@NonNull Application application) {
        super(application);
        adNetworkOrderRepository = new AdNetworkOrderRepository(application);
    }

    public void insert(AdNetworksOrderTable adNetworksOrderTable) {
        adNetworkOrderRepository.insert(adNetworksOrderTable);
    }

    public void update(AdNetworksOrderTable adNetworksOrderTable) {
        adNetworkOrderRepository.update(adNetworksOrderTable);
    }

    public void deleteAllAdNetworkOrders() {
        adNetworkOrderRepository.deleteAllAdNetworkOrders();
    }

    public LiveData<List<AdNetworksOrderTable>> getAllAdNetworkOrdersLive( ) {
        return adNetworkOrderRepository.getAllAdNetworkOrdersLive();
    }

    public List<AdNetworksOrderTable> getAllAdNetworkOrders( ) {
        return adNetworkOrderRepository.getAllAdNetworkOrders();
    }

    public int getPreferenceOfAdNetworkWithAdType(String adnetwork, String adtype) {
        return adNetworkOrderRepository.getPreferenceOfAdNetworkWithAdType(adnetwork,adtype);
    }

    public boolean isAdNetworkInAdNetworkOrderTable(String adnetwork, String adtype) {
        return adNetworkOrderRepository.isAdNetworkInAdNetworkOrderTable(adnetwork, adtype);
    }

    public int getIdOfAdNetworkInAdNetworkOrderTable(String adnetwork, String adtype) {
        return adNetworkOrderRepository.getIdOfAdNetworkInAdNetworkOrderTable(adnetwork, adtype);
    }


    
}
