package src;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comandos {
    private final BancoDados db;

    public Comandos(){
        db = new BancoDados();
    }

    public boolean register(String name, String age, String bloodType, String canDonate, String pessoa_id, String password) {
        try {
            BancoDados.registerUser(name, age, bloodType, canDonate, pessoa_id, password);
            return true;
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String pessoa_id, String password) {
        try {
            ResultSet rs =db.login(pessoa_id, password);
            return rs.next();
        }
        catch (SQLException e) {
                System.err.println("Error during login: " + e.getMessage());
                return false;
            }
        
    }

    public void listUsers() {
        try {
            ResultSet users = db.getAllUsers();
            System.out.println("pessoa_ids:");
            while (users.next()) {
                System.out.println(users.getString("pessoa_id"));
            }
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }
}