package com.v.ad.vadenhancerLibrary.LocalDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.v.ad.vadenhancerLibrary.LocalTables.KeyValueTable;

import java.util.List;

@Dao
public interface KeyValueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(KeyValueTable keyValueTable);

    @Update
    void update(KeyValueTable keyValueTable);

    @Delete
    void delete(KeyValueTable keyValueTable);

    @Query("DELETE FROM KeyValueTable")
    void deleteAllKeyValues();

    @Query("SELECT * FROM KeyValueTable")
    LiveData<List<KeyValueTable>> getAllKeyValuesLive();

    @Query("SELECT * FROM KeyValueTable")
    List<KeyValueTable> getAllKeyValues();

    @Query("SELECT value FROM KeyValueTable WHERE `key`= :key ")
    String getValueWhereKeyIs(String key);

    @Query("SELECT id FROM KeyValueTable WHERE `key` = :key LIMIT 1")
    boolean isKeyInKeyValueTable(String key);

    @Query("SELECT id FROM KeyValueTable WHERE `key` = :key LIMIT 1")
    int getIdOfKeyInKeyValueTable(String key);
}
