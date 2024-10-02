package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.entitiy.Store;
import com.refanzzzz.tokonyadia.repository.StoreRepository;
import com.refanzzzz.tokonyadia.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    @Override
    public List<Store> getAllStore() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(String id) {
        Optional<Store> optionalStore = storeRepository.findById(id);

        if (optionalStore.isPresent()) {
            return optionalStore.get();
        }

        throw new RuntimeException("Store tidak ada!");
    }

    @Override
    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(String id, Store store) {
        Optional<Store> optionalStore = storeRepository.findById(id);

        if (optionalStore.isPresent()) {
            Store updateStore = optionalStore.get();
            updateStore.setName(store.getName());
            updateStore.setAddress(store.getAddress());
            updateStore.setNoSiup(store.getNoSiup());
            updateStore.setPhoneNumber(store.getPhoneNumber());

            return storeRepository.save(updateStore);
        }

        throw new RuntimeException("Store tidak ada!");
    }

    @Override
    public String deleteStoreById(String id) {
        Optional<Store> optionalStore = storeRepository.findById(id);

        if (optionalStore.isPresent()) {
            storeRepository.delete(optionalStore.get());
            return "Data store berhasil dihapus dengan id: " + id;
        }

        throw new RuntimeException("Store tidak ada!");
    }
}
