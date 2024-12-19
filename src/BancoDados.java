package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;//addei


public class BancoDados{
    private static final String URL = "jdbc:mysql://localhost:3306/bancoDados";
    private static final String USER = "root@localhost";
    private static final String PASSWORD = "Jo08ma02@";



    
    public static boolean registerUser (String name, int age, String bloodType, String username, String password, String canDonate) throws SQLException{ //coloquei pra ser boolean
        try(Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)){

        //String colocarDadosPessoas = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador)";
        //String colocarDadosUsuarios = "INSERT INTO usuarios (username, senha)";
        String inserirPessoa = "INSERT INTO pessoas (nome, idade, tipoSanguineo, seDoador) VALUES (?, ?, ?, ?)";
        String inserirUsuario = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

        


        //PreparedStatement inserirPessoas = conexao.prepareStatement(colocarDadosPessoas);
        //PreparedStatement inserirUsuarios = conexao.prepareStatement(colocarDadosUsuarios);

        try (PreparedStatement inserirPessoas = conexao.prepareStatement(inserirPessoa);
        PreparedStatement inserirUsuarios = conexao.prepareStatement(inserirUsuario)) {
        inserirPessoas.setString(1, name);
        inserirPessoas.setInt(2, age);
        inserirPessoas.setString(3, bloodType);
        inserirPessoas.setString(4, canDonate);


        inserirUsuarios.setString(1, username);
        inserirUsuarios.setString(2, password);


        //inserirPessoas.executeUpdate();
        //inserirUsuarios.executeUpdate();
        int pessoasInseridas = inserirPessoas.executeUpdate();
        int usuariosInseridos = inserirUsuarios.executeUpdate();

        return pessoasInseridas > 0 && usuariosInseridos > 0;
        
    }

        //System.out.println("Dados atualizados");
    }
    catch(SQLException e){
        System.out.println("Erro ao adicionar os dados: " + e.getMessage());
        throw e;
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

public static String loginUser(String username, String password) throws SQLException {
    try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)) {

        String query = "SELECT * FROM usuarios WHERE username = ? AND senha = ?";
        try (PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username"); // Retorna o nome de usuário se a autenticação for bem-sucedida
            } else {
                return null; // Retorna null se as credenciais não forem válidas
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao realizar login: " + e.getMessage());
        throw e;
    }

}
}