package com.v.ad.vadenhancerLibrary.LocalViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.v.ad.vadenhancerLibrary.LocalRepositories.KeyValueRepository;
import com.v.ad.vadenhancerLibrary.LocalTables.KeyValueTable;

import java.util.List;

public class KeyValueViewModel extends AndroidViewModel {

    private final KeyValueRepository keyValueRepository;

    public KeyValueViewModel(@NonNull Application application) {
        super(application);
        keyValueRepository = new KeyValueRepository(application);
    }

    public void insert(KeyValueTable keyValueTable) {
        keyValueRepository.insert(keyValueTable);
    }

    public void update(KeyValueTable keyValueTable) {
        keyValueRepository.update(keyValueTable);
    }

    public void deleteAllProfiles() {
        keyValueRepository.deleteAllKeyValues();
    }

    public LiveData<List<KeyValueTable>> getAllKeyValuesLive( ) {
        return keyValueRepository.getAllKeyValuesLive();
    }

    public List<KeyValueTable> getAllKeyValues( ) {
        return keyValueRepository.getAllKeyValues();
    }

    public String getValueWhereKeyIs(String key) {
        return keyValueRepository.getValueWhereKeyIs(key);
    }

    public boolean isProfileInProfileList(String key) {
        return keyValueRepository.isKeyInKeyValueTable(key);
    }

    public int getIdOfProfileFromProfileList(String key) {
        return keyValueRepository.getIdOfKeyInKeyValueTable(key);
    }


}
