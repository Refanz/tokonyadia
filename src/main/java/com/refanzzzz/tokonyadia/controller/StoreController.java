package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.entitiy.Store;
import com.refanzzzz.tokonyadia.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
public class StoreController {

    private StoreService storeService;

    @GetMapping
    public List<Store> getAllStore() {
        return storeService.getAllStore();
    }

    @GetMapping("/{id}")
    public Store getStoreById(@PathVariable String id) {
        return storeService.getStoreById(id);
    }

    @PostMapping
    public Store addStore(@RequestBody Store store) {
        return storeService.addStore(store);
    }

    @DeleteMapping("/{id}")
    public String deleteStoreById(@PathVariable String id) {
        return storeService.deleteStoreById(id);
    }

    @PutMapping("/{id}")
    public Store updateStore(@PathVariable String id, @RequestBody Store store) {
        return storeService.updateStore(id, store);
    }
}
