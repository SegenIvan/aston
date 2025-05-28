package com.aston.task2.util;

import com.aston.task2.dao.UserDaoImpl;
import com.aston.task2.entity.User;
import com.aston.task2.service.UserService;
import com.aston.task2.service.UserServiceImpl;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Scanner;

@Log4j2
public class ConsoleRunner {
    private static final UserService userService = new UserServiceImpl(new UserDaoImpl());
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        try {
            boolean running = true;
            while (running) {
                log.info("1. Create User");
                log.info("2. View All Users");
                log.info("3. View User by ID");
                log.info("4. Update User");
                log.info("5. Delete User");
                log.info("6. Exit");
                log.info("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> createUser();
                    case 2 -> viewAllUsers();
                    case 3 -> viewUserById();
                    case 4 -> updateUser();
                    case 5 -> deleteUser();
                    case 6 -> running = false;
                    default -> log.info("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            log.info("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
        }
    }

    private static void createUser() {
        log.info("Enter name: ");
        String name = scanner.nextLine();

        log.info("Enter email: ");
        String email = scanner.nextLine();

        log.info("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, email, age);
        userService.save(user);
        log.info("User created successfully: " + user);
    }

    private static void viewAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            log.info("No users found.");
        } else {
            log.info("List of Users:");
            users.forEach(log::info);
        }
    }

    private static void viewUserById() {
        log.info("Enter user ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userService.findById(id);
        if (user != null) {
            log.info("User found: " + user);
        } else {
            log.info("User not found with ID: " + id);
        }
    }

    private static void updateUser() {
        log.info("Enter user ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userService.findById(id);
        if (user == null) {
            log.info("User not found with ID: " + id);
            return;
        }

        log.info("Enter new name (current: " + user.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            user.setName(name);
        }

        log.info("Enter new email (current: " + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        log.info("Enter new age (current: " + user.getAge() + "): ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isEmpty()) {
            user.setAge(Integer.parseInt(ageInput));
        }

        userService.update(user);
        log.info("User updated successfully: " + user);
    }

    private static void deleteUser() {
        log.info("Enter user ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userService.findById(id);
        if (user != null) {
            userService.delete(user);
            log.info("User deleted successfully with ID: " + id);
        } else {
            log.info("User not found with ID: " + id);
        }
    }
}
