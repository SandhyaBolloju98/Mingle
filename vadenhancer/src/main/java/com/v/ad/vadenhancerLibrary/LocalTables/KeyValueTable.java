package com.v.ad.vadenhancerLibrary.LocalTables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KeyValueTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String key;
    private final String value;

    public KeyValueTable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
