package com.literanusa.util;

import com.literanusa.model.Book;
import com.literanusa.model.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WishlistManager {
    private static WishlistManager instance;
    private final Map<Integer, Set<Integer>> userWishlists; // userId -> Set<bookId>
    private final String WISHLIST_FILE = "data/wishlists.ser";
    
    private WishlistManager() {
        this.userWishlists = new ConcurrentHashMap<>();
        loadWishlists();
        
        // Create data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public static WishlistManager getInstance() {
        if (instance == null) {
            synchronized (WishlistManager.class) {
                if (instance == null) {
                    instance = new WishlistManager();
                }
            }
        }
        return instance;
    }
    
    public boolean addToWishlist(User user, Book book) {
        if (user == null || book == null) {
            return false;
        }
        
        Set<Integer> userWishlist = userWishlists.computeIfAbsent(user.getId(), k -> new HashSet<>());
        boolean added = userWishlist.add(book.getId());
        
        if (added) {
            saveWishlists();
        }
        
        return added;
    }
    
    public boolean removeFromWishlist(User user, Book book) {
        if (user == null || book == null) {
            return false;
        }
        
        Set<Integer> userWishlist = userWishlists.get(user.getId());
        if (userWishlist == null) {
            return false;
        }
        
        boolean removed = userWishlist.remove(book.getId());
        
        if (removed) {
            if (userWishlist.isEmpty()) {
                userWishlists.remove(user.getId());
            }
            saveWishlists();
        }
        
        return removed;
    }
    
    public boolean isInWishlist(User user, Book book) {
        if (user == null || book == null) {
            return false;
        }
        
        Set<Integer> userWishlist = userWishlists.get(user.getId());
        return userWishlist != null && userWishlist.contains(book.getId());
    }
    
    public Set<Integer> getUserWishlist(User user) {
        if (user == null) {
            return new HashSet<>();
        }
        
        return new HashSet<>(userWishlists.getOrDefault(user.getId(), new HashSet<>()));
    }
    
    public int getWishlistCount(User user) {
        if (user == null) {
            return 0;
        }
        
        Set<Integer> userWishlist = userWishlists.get(user.getId());
        return userWishlist != null ? userWishlist.size() : 0;
    }
    
    public void clearWishlist(User user) {
        if (user == null) {
            return;
        }
        
        userWishlists.remove(user.getId());
        saveWishlists();
    }
    
    public boolean toggleWishlist(User user, Book book) {
        if (isInWishlist(user, book)) {
            return !removeFromWishlist(user, book); // Return false if removed (not in wishlist anymore)
        } else {
            return addToWishlist(user, book); // Return true if added
        }
    }
    
    public Map<Integer, Integer> getWishlistStatistics() {
        Map<Integer, Integer> stats = new HashMap<>();
        
        for (Set<Integer> wishlist : userWishlists.values()) {
            for (Integer bookId : wishlist) {
                stats.merge(bookId, 1, Integer::sum);
            }
        }
        
        return stats;
    }
    
    public List<Integer> getMostWishlistedBooks(int limit) {
        Map<Integer, Integer> stats = getWishlistStatistics();
        
        return stats.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @SuppressWarnings("unchecked")
    private void loadWishlists() {
        try {
            File file = new File(WISHLIST_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Map<Integer, Set<Integer>> loaded = (Map<Integer, Set<Integer>>) ois.readObject();
                    userWishlists.putAll(loaded);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading wishlists: " + e.getMessage());
            // Continue with empty wishlists
        }
    }
    
    private void saveWishlists() {
        try {
            File file = new File(WISHLIST_FILE);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(new HashMap<>(userWishlists));
            }
        } catch (IOException e) {
            System.err.println("Error saving wishlists: " + e.getMessage());
        }
    }
    
    // Method untuk export wishlist ke file CSV
    public void exportWishlistToCSV(User user, String filename) throws IOException {
        Set<Integer> wishlist = getUserWishlist(user);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Book ID,Date Added");
            
            for (Integer bookId : wishlist) {
                writer.println(bookId + "," + new Date());
            }
        }
    }
    
    // Method untuk import wishlist dari file CSV
    public void importWishlistFromCSV(User user, String filename) throws IOException {
        Set<Integer> currentWishlist = userWishlists.computeIfAbsent(user.getId(), k -> new HashSet<>());
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // Skip header
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    try {
                        int bookId = Integer.parseInt(parts[0].trim());
                        currentWishlist.add(bookId);
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        }
        
        saveWishlists();
    }
    
    // Method untuk mendapatkan rekomendasi berdasarkan wishlist
    public Set<Integer> getRecommendationsBasedOnWishlist(User user, int limit) {
        Set<Integer> userWishlist = getUserWishlist(user);
        if (userWishlist.isEmpty()) {
            return new HashSet<>();
        }
        
        // Simple recommendation: find books that are in other users' wishlists
        // who have similar books in their wishlist
        Map<Integer, Integer> recommendations = new HashMap<>();
        
        for (Map.Entry<Integer, Set<Integer>> entry : userWishlists.entrySet()) {
            Integer otherUserId = entry.getKey();
            Set<Integer> otherWishlist = entry.getValue();
            
            if (otherUserId.equals(user.getId())) {
                continue; // Skip current user
            }
            
            // Calculate similarity (number of common books)
            Set<Integer> commonBooks = new HashSet<>(userWishlist);
            commonBooks.retainAll(otherWishlist);
            
            if (!commonBooks.isEmpty()) {
                // Add books from other user's wishlist that current user doesn't have
                for (Integer bookId : otherWishlist) {
                    if (!userWishlist.contains(bookId)) {
                        recommendations.merge(bookId, commonBooks.size(), Integer::sum);
                    }
                }
            }
        }
        
        return recommendations.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(HashSet::new, (set, bookId) -> set.add(bookId), HashSet::addAll);
    }
    
    // Cleanup method untuk menghapus wishlist user yang sudah tidak ada
    public void cleanupOrphanedWishlists(Set<Integer> validUserIds) {
        userWishlists.entrySet().removeIf(entry -> !validUserIds.contains(entry.getKey()));
        saveWishlists();
    }
}