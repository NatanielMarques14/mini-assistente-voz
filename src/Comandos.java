package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public boolean login(int username, String password) {
        try {
            return db.login(username, password);
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void listUsers() {
    try {
        List<String> nomes = db.getAllUsers();
        System.out.println("Nomes / se é doadores: ");
        for (String nome : nomes) {
            System.out.println(nome);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching users: " + e.getMessage());
        e.printStackTrace();
    }
}

}