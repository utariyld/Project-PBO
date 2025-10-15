package com.ukm.ksr.logistics.controller;

import com.ukm.ksr.logistics.dto.ApiResponse;
import com.ukm.ksr.logistics.dto.ItemRequest;
import com.ukm.ksr.logistics.dto.ItemResponse;
import com.ukm.ksr.logistics.model.Item;
import com.ukm.ksr.logistics.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Item Management", description = "Item management APIs")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Create new item", description = "Create a new inventory item")
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemRequest itemRequest) {
        try {
            ItemResponse itemResponse = itemService.createItem(itemRequest);
            return ResponseEntity.ok(new ApiResponse("Barang berhasil ditambahkan", itemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    @Operation(summary = "Get all items", description = "Get all inventory items")
    public ResponseEntity<?> getAllItems() {
        try {
            List<ItemResponse> items = itemService.getAllItems();
            return ResponseEntity.ok(new ApiResponse("Daftar barang berhasil diambil", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Get item details by ID")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            ItemResponse itemResponse = itemService.getItemById(id);
            return ResponseEntity.ok(new ApiResponse("Detail barang berhasil diambil", itemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Update item", description = "Update item information")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @Valid @RequestBody ItemRequest itemRequest) {
        try {
            ItemResponse itemResponse = itemService.updateItem(id, itemRequest);
            return ResponseEntity.ok(new ApiResponse("Barang berhasil diperbarui", itemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Delete item", description = "Delete item from inventory")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok(new ApiResponse("Barang berhasil dihapus", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get items by category", description = "Get items filtered by category")
    public ResponseEntity<?> getItemsByCategory(@PathVariable Item.ItemCategory category) {
        try {
            List<ItemResponse> items = itemService.getItemsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("Barang berdasarkan kategori", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get items by status", description = "Get items filtered by status")
    public ResponseEntity<?> getItemsByStatus(@PathVariable Item.ItemStatus status) {
        try {
            List<ItemResponse> items = itemService.getItemsByStatus(status);
            return ResponseEntity.ok(new ApiResponse("Barang berdasarkan status", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search items", description = "Search items by keyword")
    public ResponseEntity<?> searchItems(@RequestParam String keyword) {
        try {
            List<ItemResponse> items = itemService.searchItems(keyword);
            return ResponseEntity.ok(new ApiResponse("Hasil pencarian barang", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Update item status", description = "Update item status")
    public ResponseEntity<?> updateItemStatus(@PathVariable Long id, @RequestParam Item.ItemStatus status) {
        try {
            ItemResponse itemResponse = itemService.updateItemStatus(id, status);
            return ResponseEntity.ok(new ApiResponse("Status barang berhasil diperbarui", itemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/quantity")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS_STAFF')")
    @Operation(summary = "Update item quantity", description = "Update item quantity")
    public ResponseEntity<?> updateItemQuantity(@PathVariable Long id, 
                                               @RequestParam(required = false) Integer totalQuantity,
                                               @RequestParam(required = false) Integer availableQuantity) {
        try {
            ItemResponse itemResponse = itemService.updateItemQuantity(id, totalQuantity, availableQuantity);
            return ResponseEntity.ok(new ApiResponse("Jumlah barang berhasil diperbarui", itemResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }
}