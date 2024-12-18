package src;

import src.BancoDados;

import java.sql.ResultSet;

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
            return false;
        }
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
            return null;
        }
    }
}
