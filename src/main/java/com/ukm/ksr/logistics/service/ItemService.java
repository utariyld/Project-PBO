package com.ukm.ksr.logistics.service;

import com.ukm.ksr.logistics.dto.ItemRequest;
import com.ukm.ksr.logistics.dto.ItemResponse;
import com.ukm.ksr.logistics.model.Item;
import com.ukm.ksr.logistics.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemResponse createItem(ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setCategory(itemRequest.getCategory());
        item.setStatus(itemRequest.getStatus());
        item.setTotalQuantity(itemRequest.getTotalQuantity());
        item.setAvailableQuantity(itemRequest.getAvailableQuantity() != null ? 
                                  itemRequest.getAvailableQuantity() : itemRequest.getTotalQuantity());
        item.setLocation(itemRequest.getLocation());
        item.setBrand(itemRequest.getBrand());
        item.setModel(itemRequest.getModel());
        item.setSerialNumber(itemRequest.getSerialNumber());

        Item savedItem = itemRepository.save(item);
        return new ItemResponse(savedItem);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));
        return new ItemResponse(item);
    }

    public ItemResponse updateItem(Long id, ItemRequest itemRequest) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));

        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setCategory(itemRequest.getCategory());
        item.setStatus(itemRequest.getStatus());
        item.setTotalQuantity(itemRequest.getTotalQuantity());
        item.setAvailableQuantity(itemRequest.getAvailableQuantity());
        item.setLocation(itemRequest.getLocation());
        item.setBrand(itemRequest.getBrand());
        item.setModel(itemRequest.getModel());
        item.setSerialNumber(itemRequest.getSerialNumber());

        Item updatedItem = itemRepository.save(item);
        return new ItemResponse(updatedItem);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));
        itemRepository.delete(item);
    }

    public List<ItemResponse> getItemsByCategory(Item.ItemCategory category) {
        return itemRepository.findByCategory(category).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getItemsByStatus(Item.ItemStatus status) {
        return itemRepository.findByStatus(status).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> searchItems(String keyword) {
        return itemRepository.searchItems(keyword).stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }

    public ItemResponse updateItemStatus(Long id, Item.ItemStatus status) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));
        item.setStatus(status);
        Item updatedItem = itemRepository.save(item);
        return new ItemResponse(updatedItem);
    }

    public ItemResponse updateItemQuantity(Long id, Integer totalQuantity, Integer availableQuantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));
        
        if (totalQuantity != null) {
            item.setTotalQuantity(totalQuantity);
        }
        if (availableQuantity != null) {
            item.setAvailableQuantity(availableQuantity);
        }
        
        Item updatedItem = itemRepository.save(item);
        return new ItemResponse(updatedItem);
    }

    // Statistics methods
    public Long getItemCountByStatus(Item.ItemStatus status) {
        return itemRepository.countByStatus(status);
    }

    public Long getItemCountByCategory(Item.ItemCategory category) {
        return itemRepository.countByCategory(category);
    }

    public List<Object[]> getItemStatsByCategory() {
        return itemRepository.countItemsByCategory();
    }

    public List<Object[]> getItemStatsByStatus() {
        return itemRepository.countItemsByStatus();
    }
}