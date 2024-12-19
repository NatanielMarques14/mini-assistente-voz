package src;

import src.BancoDados;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comandos {
    private BancoDados db;

    public Comandos(BancoDados db) {
        this.db = db;
    }

    public boolean register(String name, String age, String bloodType, String canDonate, String username, String password) {
        try {
            BancoDados.registerUser(name, age, bloodType, canDonate, username, password);
            return true;
        } catch (Exception e) {
            System.err.println("Erro no registro: " + e.getMessage());
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

    public ResultSet getUserInfo(String username, String password) throws SQLException {
        return db.login(username, password); // Chama o método login da classe BancoDados
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
            System.err.println("Erro no login " + e.getMessage());
            return null;
        }
    }
}
