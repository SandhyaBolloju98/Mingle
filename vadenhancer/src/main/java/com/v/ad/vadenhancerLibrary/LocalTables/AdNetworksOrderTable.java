package com.v.ad.vadenhancerLibrary.LocalTables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AdNetworksOrderTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String adtype;
    private final String adnetwork;
    private final int preference;

    public AdNetworksOrderTable(String adnetwork, String adtype, int preference) {
        this.adnetwork = adnetwork;
        this.adtype = adtype;
        this.preference = preference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdtype() {
        return adtype;
    }

    public String getAdnetwork() {
        return adnetwork;
    }

    public int getPreference() {
        return preference;
    }
}
