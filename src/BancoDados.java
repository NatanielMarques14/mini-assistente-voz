package src;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.ResultSet;

//colocar
//INSERT INTO users (name, age, bloodType, username, password, canDonate)
//VALUES ('Admin', 20, 'A', 'admin', '1234', 'yes');
//no sql

//add o sangue no grammars

public class BancoDados{
    private static final String URL = "jdbc:mysql://localhost:3306/bancoDados";
    //private static final String USER = "root@localhost";
    private static final String USER = "root";
    private static final String PASSWORD = "Jo08ma02@";

    public BancoDados() throws SQLException { //construtuor
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // ve se o usuário admin existe
            String checkAdminSQL = "SELECT COUNT(*) FROM usuarios WHERE username = 'admin'";
            PreparedStatement checkAdminStmt = conn.prepareStatement(checkAdminSQL);
            ResultSet rs = checkAdminStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) { // Se admin não existir, cria
                String insertAdminPessoas = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador) VALUES (?, ?, ?, ?)";
                String insertAdminUsuarios = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

                try (PreparedStatement stmtPessoas = conn.prepareStatement(insertAdminPessoas);
                     PreparedStatement stmtUsuarios = conn.prepareStatement(insertAdminUsuarios)) {

                    // Insere dados na tabela "pessoas"
                    stmtPessoas.setString(1, "Admin");
                    stmtPessoas.setInt(2, 20);
                    stmtPessoas.setString(3, "A");
                    stmtPessoas.setString(4, "yes");
                    stmtPessoas.executeUpdate();

                    // Insere dados na tabela "usuarios"
                    stmtUsuarios.setString(1, "admin");
                    stmtUsuarios.setString(2, "1234");
                    stmtUsuarios.executeUpdate();

                    System.out.println("Admin user created successfully!");
                }
            }
        }
    }

    public static void registerUser (String name, int age, String bloodType, String username, String password, String canDonate) throws SQLException{ 
        try(Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)){

        //String colocarDadosPessoas = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador)";
        //String colocarDadosUsuarios = "INSERT INTO usuarios (username, senha)";
        String sqlPessoas = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador) VALUES (?, ?, ?, ?)";
        String sqlUsuarios = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

        //PreparedStatement inserirPessoas = conexao.prepareStatement(colocarDadosPessoas);
        //PreparedStatement inserirUsuarios = conexao.prepareStatement(colocarDadosUsuarios);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmtPessoas = conn.prepareStatement(sqlPessoas);
        PreparedStatement stmtUsuarios = conn.prepareStatement(sqlUsuarios)) {
        
        // Tabela pessoas
        stmtPessoas.setString(1, name);
        stmtPessoas.setInt(2, age);
        stmtPessoas.setString(3, bloodType);
        stmtPessoas.setString(4, canDonate);
        stmtPessoas.executeUpdate();

        // Tabela usuarios
        stmtUsuarios.setString(1, username);
        stmtUsuarios.setString(2, password);
        stmtUsuarios.executeUpdate();

        System.out.println("User successfully registered!");
        
        }
        }
    }

/*public static void main(String[] s){
    try{
        BancoDados.registerUser("Filipe", 19, "AB", "filipeac", "1234", "Sim");
    }
    catch(SQLException e){
        System.out.println("Erro: "+ e.getMessage());
    }
    
*/

public ResultSet login(String username, String password) throws SQLException {
    String sql = "SELECT pessoas.nome FROM usuarios INNER JOIN pessoas ON usuarios.username = ? AND usuarios.senha = ?";
    Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, username);
    stmt.setString(2, password);
    return stmt.executeQuery();
    } 

public ResultSet getAllUsers() throws SQLException {
        String sql = "SELECT * FROM pessoas";
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }


}