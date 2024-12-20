package src;

import java.sql.*;

public class BancoDados {
    private static final String URL = "jdbc:mysql://localhost:3306/bancoDados";
    private static final String USER = "root"; // Substitua pelo seu usuário do banco
    private static final String PASSWORD = "Gusttsug_sl1"; // Substitua pela sua senha do banco

    // Construtor vazio
    public BancoDados() {}

    // Método para registrar um usuário
    public static void registerUser(String name, String age, String bloodType, String pessoa_id, String password, String canDonate) throws SQLException {
        // Conexão com o banco
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sqlPessoas = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador) VALUES (?, ?, ?, ?)";
            String sqlUsuarios = "INSERT INTO usuarios (pessoa_id, senha) VALUES (?, ?)";

            // Inserir na tabela pessoas
            try (PreparedStatement stmtPessoas = conexao.prepareStatement(sqlPessoas);
                 PreparedStatement stmtUsuarios = conexao.prepareStatement(sqlUsuarios)) {

                // Inserção na tabela pessoas
                stmtPessoas.setString(1, name);
                stmtPessoas.setString(2, age);
                stmtPessoas.setString(3, bloodType);
                stmtPessoas.setString(4, canDonate);
                stmtPessoas.executeUpdate();

                // Inserção na tabela usuarios
                stmtUsuarios.setString(1, pessoa_id);
                stmtUsuarios.setString(2, password);
                stmtUsuarios.executeUpdate();

                System.out.println("Usuário registrado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
            throw e;
        }
    }

    // Método de login
    public boolean login(String pessoa_id, String password) throws SQLException {
        String sql = "SELECT pessoas.nome FROM usuarios " +
                     "INNER JOIN pessoas ON usuarios.pessoa_id = pessoas.id " +
                     "WHERE usuarios.pessoa_id = ? AND usuarios.senha = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa_id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // Método para obter todos os usuários
    public ResultSet getAllUsers() throws SQLException {
        String sql = "SELECT * FROM pessoas";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            return stmt.executeQuery();
        }
    }
}