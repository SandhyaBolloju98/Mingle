package com.v.ad.vadenhancerLibrary.LocalDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.v.ad.vadenhancerLibrary.LocalTables.AdNetworksOrderTable;

import java.util.List;

@Dao
public interface AdNetworksOrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AdNetworksOrderTable adNetworksOrderTable);

    @Update
    void update(AdNetworksOrderTable adNetworksOrderTable);

    @Delete
    void delete(AdNetworksOrderTable adNetworksOrderTable);

    @Query("DELETE FROM AdNetworksOrderTable")
    void deleteAllAdNetworkOrders();

    @Query("SELECT * FROM AdNetworksOrderTable")
    LiveData<List<AdNetworksOrderTable>> getAllAdNetworkOrdersLive();

    @Query("SELECT * FROM AdNetworksOrderTable")
    List<AdNetworksOrderTable> getAllAdNetworkOrders();

    @Query("SELECT * FROM AdNetworksOrderTable WHERE `adtype` = :adtype ORDER BY preference ASC")
    List<AdNetworksOrderTable> getAllAdNetworkOrdersOfAdType(String adtype);

    @Query("SELECT * FROM AdNetworksOrderTable WHERE `adnetwork` = :adnetwork ORDER BY preference ASC")
    List<AdNetworksOrderTable> getAllAdTypeOrdersOfAdNetwork(String adnetwork);

    @Query("SELECT preference FROM AdNetworksOrderTable WHERE `adnetwork`= :adnetwork AND `adtype` = :adtype")
    int getPreferenceOfAdNetworkWithAdType(String adnetwork, String adtype);

    @Query("SELECT id FROM AdNetworksOrderTable WHERE `adnetwork` = :adnetwork AND `adtype` = :adtype LIMIT 1")
    boolean isAdNetworkOfAdTypeInAdNetworkOrderTable(String adnetwork, String adtype);

    @Query("SELECT id FROM AdNetworksOrderTable WHERE `adnetwork` = :adnetwork AND `adtype` = :adtype LIMIT 1")
    int getIdOfAdNetworkOfAdTypeInAdNetworkOrderTable(String adnetwork, String adtype);


}
