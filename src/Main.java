package src;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Assistente assistente = new Assistente();
            assistente.start();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    } 
}