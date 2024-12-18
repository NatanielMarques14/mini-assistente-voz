package src;

import src.voz.TTS;
import src.voz.STT; //classes importadas do pacote voz representam as funcionalidades de entrada e saida de voz
import src.Comandos;//importa a classe Comandos
import src.BancoDados;//pra gerenciar a conexão do banco com as operações de inserir e buscar dados

import java.io.IOException; // para lidar com os erros da entrada e saída de dados
import java.sql.ResultSet;
import java.sql.SQLException; //para lidar com os erros do sql

public class Assistente { //Assistente é a classe principal que vai organizar o comportamento do assistente, as outras classes precisam instância-la para acessar as suas funcionalidades
    private TTS tts;
    private STT stt;
    private Comandos comandos; //sao privates pois são usados pela classe internamente
    
    
    public Assistente() throws SQLException, IOException { //construtor, chamado toda vez que é criado um objeto da classe, também pode lançar exceções
        tts = new TTS();
        stt = new STT("comandos");
        //bancoDados = new BancoDados();
        comandos = new Comandos(new BancoDados());
    }

    public void start() { //inicia o mini-assistente
        tts.speak("Welcome to the mini assistant. Please say a command like register, login or exit");
        String comando = stt.getComando();

        switch (comando.toLowerCase()) {
            case "register":
                handleRegistration();
                break;
            case "login":
                handleLogin();
                break;
            case "exit":
                tts.speak("Exiting the assistant. Goodbye!");
                System.exit(0);
            default:
                tts.speak("Command not recognized. Try again");
                start();
        }
    }

    private void handleRegistration() { //faz o registro
        tts.speak("Say your name");
        String name = stt.getComando();

        tts.speak("Say your age");
        int age = Integer.parseInt(stt.getComando()); //como o listen retorna uma string usamos a função do parseInt para converter em int

        tts.speak("Say your blood type");
        String bloodType = stt.getComando();

        tts.speak("Say your username");
        String username = stt.getComando();

        tts.speak("Say your password");
        String password = stt.getComando();

        tts.speak("Can you donate blood?");
        String canDonate = stt.getComando();

        boolean success = comandos.register(name, age, bloodType, username, password, canDonate); //salva o registro no banco de dados
        if (success) {
            tts.speak("Registration successful!");
        }
        
        else{ 
            tts.speak("Registration failed, try again");

        }
        tts.speak("What do you wanna do now? register, login or exit?"); //dá a possibilidade de escolher outras coisas para fazer após o registro
        start(); 
    }

    private void handleAdminCommands() {
    tts.speak("Say 'list' to list all users, or 'exit' to leave.");
    String comando = stt.getComando();

    switch (comando.toLowerCase()) {
        case "list":
            try {
                ResultSet rs = comandos.getAllUsers();
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("nome") +
                                       ", Age: " + rs.getInt("idade") +
                                       ", Blood Type: " + rs.getString("tipoSanguineo") +
                                       ", Can Donate: " + rs.getString("seDoador"));
                }
            } catch (SQLException e) {
                tts.speak("Error retrieving user list.");
            }
            break;
        case "exit":
            tts.speak("Logging out. Goodbye!");
            return;
        default:
            tts.speak("Command not recognized. Try again.");
            handleAdminCommands();
    }
}

    

    private void handleLogin() {//faz o login 
        tts.speak("Say your username");
        String username = stt.getComando();
    
        tts.speak("Say your password");
        String password = stt.getComando();
    
        String user = comandos.login(username, password);
        if (user != null) {
            if (comandos.isAdmin(username, password)) {
                tts.speak("Welcome administrator! What would you like to do?");
                handleAdminCommands();
            } else {
                tts.speak("Welcome, " + user + "! What would you like to do?");
            }
        } else {
            tts.speak("Incorrect username or password. Please try again.");
        }
    }
}
