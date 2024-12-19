package src;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Instancia o assistente
            Assistente assistente = new Assistente();

            // Inicia o assistente
            assistente.start();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao inicializar o sistema de voz: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}
