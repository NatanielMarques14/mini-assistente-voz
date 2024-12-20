package src;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comandos {
    private final BancoDados db;

    public Comandos(){
        db = new BancoDados();
    }

    public boolean register(String name, String age, String bloodType, String canDonate, String username, String password) {
        try {
            BancoDados.registerUser(name, age, bloodType, canDonate, username, password);
            return true;
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            return db.login(username, password);
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void listUsers() {
        try {
            ResultSet users = db.getAllUsers();
            System.out.println("Usernames:");
            while (users.next()) {
                System.out.println(users.getString("username"));
            }
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }
}