package src;

import java.sql.*;

public class BancoDados {
    private static final String URL = "jdbc:mysql://localhost:3306/bancoDados";
    private static final String USER = "root@localhost";
    private static final String PASSWORD = "Jo08ma02@";

    public static String getNextUsername() throws SQLException {
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT username FROM usuarios ORDER BY id DESC LIMIT 1";
            PreparedStatement stmt = conexao.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String lastUsername = rs.getString("username");
                if (lastUsername.startsWith("user")) {
                    int lastNumber = Integer.parseInt(lastUsername.replace("user", ""));
                    return "user" + (lastNumber + 1);
                }
            }
            // Caso não haja usuários registrados, retorna o primeiro
            return "user1";
        }
    }


    public static String getPasswordFromUsername(String username) {
        return username.replace("user", ""); // A senha será o número do username
    }

    public static void registerUser(String name, int age, String bloodType, String canDonate) throws SQLException {
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Gerar próximo username e senha
            String username = getNextUsername();
            String password = getPasswordFromUsername(username);


            String insertPerson = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador) VALUES (?, ?, ?, ?)";
            String insertUser = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

            PreparedStatement stmtPerson = conexao.prepareStatement(insertPerson);
            stmtPerson.setString(1, name);
            stmtPerson.setInt(2, age);
            stmtPerson.setString(3, bloodType);
            stmtPerson.setString(4, canDonate);
            stmtPerson.executeUpdate();

            PreparedStatement stmtUser = conexao.prepareStatement(insertUser);
            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            stmtUser.executeUpdate();

            System.out.println("User registered successfully!");
            System.out.println("Username: " + username + ", Password: " + password);
        }

        catch (SQLException e) {
            System.out.println("Error while registering user: " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            BancoDados.registerUser("Filipe", 19, "AB", "Sim");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void registerUser(String name, int age, String bloodType, String username, String password, String canDonate) {
    }
}