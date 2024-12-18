package src;

import src.BancoDados;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comandos {
    private BancoDados db;

    public Comandos(BancoDados db) {
        this.db = db;
    }

    public boolean register(String name, int age, String bloodType,
                            String username, String password, String canDonate) {
        try {
            db.registerUser(name, age, bloodType, username, password, canDonate);
            return true;
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isAdmin(String username, String password) {
        return username.equals("admin") && password.equals("1234");
    }
    
    public ResultSet getAllUsers() throws SQLException {
    BancoDados banco = new BancoDados();
    return banco.getAllUsers();
    }


    public String login(String username, String password) {
        try {
            ResultSet rs = db.login(username, password);
            if (rs.next()) {
                return rs.getString("name");
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }
}
