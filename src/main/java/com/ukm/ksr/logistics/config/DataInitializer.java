package com.ukm.ksr.logistics.config;

import com.ukm.ksr.logistics.model.Item;
import com.ukm.ksr.logistics.model.User;
import com.ukm.ksr.logistics.repository.ItemRepository;
import com.ukm.ksr.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize users if none exist
        if (userRepository.count() == 0) {
            initializeUsers();
        }

        // Initialize items if none exist
        if (itemRepository.count() == 0) {
            initializeItems();
        }
    }

    private void initializeUsers() {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@ukmksr.ac.id");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Administrator");
        admin.setRole(User.Role.ADMIN);
        admin.setPhoneNumber("081234567890");
        admin.setAddress("Markas UKM KSR");
        userRepository.save(admin);

        // Create logistics staff
        User logisticsStaff = new User();
        logisticsStaff.setUsername("logistics");
        logisticsStaff.setEmail("logistics@ukmksr.ac.id");
        logisticsStaff.setPassword(passwordEncoder.encode("logistics123"));
        logisticsStaff.setFullName("Staff Logistik");
        logisticsStaff.setRole(User.Role.LOGISTICS_STAFF);
        logisticsStaff.setPhoneNumber("081234567891");
        logisticsStaff.setAddress("Markas UKM KSR");
        userRepository.save(logisticsStaff);

        // Create member user
        User member = new User();
        member.setUsername("member");
        member.setEmail("member@ukmksr.ac.id");
        member.setPassword(passwordEncoder.encode("member123"));
        member.setFullName("Anggota KSR");
        member.setRole(User.Role.MEMBER);
        member.setPhoneNumber("081234567892");
        member.setAddress("Kampus Universitas");
        userRepository.save(member);

        System.out.println("Sample users created:");
        System.out.println("Admin - Username: admin, Password: admin123");
        System.out.println("Logistics Staff - Username: logistics, Password: logistics123");
        System.out.println("Member - Username: member, Password: member123");
    }

    private void initializeItems() {
        // Medical Equipment
        Item stethoscope = new Item("Stetoskop", "Stetoskop untuk pemeriksaan medis", 
                                   Item.ItemCategory.MEDICAL_EQUIPMENT, 5);
        stethoscope.setBrand("Littmann");
        stethoscope.setLocation("Ruang Medis A");
        itemRepository.save(stethoscope);

        Item bloodPressure = new Item("Tensimeter Digital", "Alat pengukur tekanan darah digital", 
                                     Item.ItemCategory.MEDICAL_EQUIPMENT, 3);
        bloodPressure.setBrand("Omron");
        bloodPressure.setLocation("Ruang Medis A");
        itemRepository.save(bloodPressure);

        Item firstAidKit = new Item("Kotak P3K", "Kotak pertolongan pertama lengkap", 
                                   Item.ItemCategory.MEDICAL_EQUIPMENT, 10);
        firstAidKit.setLocation("Ruang Penyimpanan");
        itemRepository.save(firstAidKit);

        // Logistics
        Item tent = new Item("Tenda Dome", "Tenda kapasitas 4 orang untuk kegiatan outdoor", 
                            Item.ItemCategory.LOGISTICS, 8);
        tent.setBrand("Eiger");
        tent.setLocation("Gudang Logistik");
        itemRepository.save(tent);

        Item sleepingBag = new Item("Sleeping Bag", "Kantong tidur untuk kegiatan camping", 
                                  Item.ItemCategory.LOGISTICS, 15);
        sleepingBag.setBrand("Consina");
        sleepingBag.setLocation("Gudang Logistik");
        itemRepository.save(sleepingBag);

        // Uniforms
        Item uniformShirt = new Item("Seragam KSR Lengan Panjang", "Seragam resmi UKM KSR lengan panjang", 
                                    Item.ItemCategory.UNIFORM, 25);
        uniformShirt.setLocation("Ruang Seragam");
        itemRepository.save(uniformShirt);

        Item uniformPants = new Item("Celana Seragam KSR", "Celana seragam resmi UKM KSR", 
                                    Item.ItemCategory.UNIFORM, 25);
        uniformPants.setLocation("Ruang Seragam");
        itemRepository.save(uniformPants);

        // Activity Supplies
        Item megaphone = new Item("Megaphone", "Pengeras suara untuk kegiatan", 
                                 Item.ItemCategory.ACTIVITY_SUPPLIES, 3);
        megaphone.setBrand("TOA");
        megaphone.setLocation("Ruang Audio");
        itemRepository.save(megaphone);

        Item projector = new Item("Proyektor", "Proyektor untuk presentasi dan pelatihan", 
                                 Item.ItemCategory.ACTIVITY_SUPPLIES, 2);
        projector.setBrand("Epson");
        projector.setLocation("Ruang Multimedia");
        itemRepository.save(projector);

        Item whiteboard = new Item("Papan Tulis", "Papan tulis untuk kegiatan pelatihan", 
                                  Item.ItemCategory.ACTIVITY_SUPPLIES, 4);
        whiteboard.setLocation("Ruang Pelatihan");
        itemRepository.save(whiteboard);

        System.out.println("Sample items created successfully!");
    }
}