package com.ukm.ksr.logistics.repository;

import com.ukm.ksr.logistics.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(Item.ItemCategory category);
    List<Item> findByStatus(Item.ItemStatus status);
    List<Item> findByCategoryAndStatus(Item.ItemCategory category, Item.ItemStatus status);
    
    @Query("SELECT i FROM Item i WHERE i.name LIKE %?1% OR i.description LIKE %?1% OR i.brand LIKE %?1% OR i.model LIKE %?1%")
    List<Item> searchItems(String keyword);
    
    @Query("SELECT COUNT(i) FROM Item i WHERE i.status = ?1")
    Long countByStatus(Item.ItemStatus status);
    
    @Query("SELECT COUNT(i) FROM Item i WHERE i.category = ?1")
    Long countByCategory(Item.ItemCategory category);
    
    @Query("SELECT i.category, COUNT(i) FROM Item i GROUP BY i.category")
    List<Object[]> countItemsByCategory();
    
    @Query("SELECT i.status, COUNT(i) FROM Item i GROUP BY i.status")
    List<Object[]> countItemsByStatus();
}