package src;

import src.TTS;
import src.STT;
import src.Comandos;
import src.BancoDados;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Assistente {
    private final TTS tts;
    private final STT stt;
    private final Comandos comandos;
    private static int userCounter = 1; // Counter for automatic pessoa_id and password generation

    public Assistente() throws SQLException, IOException {
        tts = new TTS();
        stt = new STT("junção");
        BancoDados db = new BancoDados();
        comandos = new Comandos(db);
    }

    public void start() {
        tts.speak("Hello, what would you like to do? Register, login, or exit?");
        String command = stt.getComando();

        switch (command.toLowerCase()) {
            case "register":
                handleRegistration();
                break;
            case "login":
                handleLogin();
                break;
            case "exit":
                tts.speak("Exiting. Goodbye!");
                System.exit(0);
            default:
                tts.speak("Command not recognized. Please try again.");
                start();
                break;
            }
    }

    private void handleRegistration() {
        tts.speak("Please say your name.");
        String name = stt.getComando();

        tts.speak("Please say your age.");
        String age = stt.getComando();

        tts.speak("Please say your blood type.");
        String bloodType = stt.getComando();

        tts.speak("Are you eligible to donate blood? Answer 'Yes' or 'No'.");
        String canDonate = stt.getComando();

        String pessoa_id = "user" + userCounter;
        String password = String.valueOf(userCounter);
        userCounter++;

        boolean success = comandos.register(name, age, bloodType, canDonate, pessoa_id, password);
        if (success) {
            System.out.println("New user registered: pessoa_id: " + pessoa_id + ", Password: " + password);
            tts.speak("Registration successful! Your pessoa_id is: " + pessoa_id + " and your password is: " + password);
        } else {
            tts.speak("Error during registration. Please try again.");
        }
        start();
    }

    private void handleLogin() {
        tts.speak("Please say your pessoa_id.");
        String pessoa_id = stt.getComando();

        tts.speak("Please say your password.");
        String password = stt.getComando();

        if (pessoa_id.equals("admin") && password.equals("1234")) {
            handleAdminActions();
        } else {
            try {
                ResultSet userInfo = comandos.getUserInfo(pessoa_id, password);
                if (userInfo.next()) {
                    System.out.println("User information: Name: " + userInfo.getString("name") + ", Age: " + userInfo.getInt("age") + ", Blood Type: " + userInfo.getString("bloodType") + ", Can Donate: " + userInfo.getString("canDonate"));
                    tts.speak("Welcome, " + userInfo.getString("name") + "! Type 'exit' to end the program.");
                    handleUserExit();
                } else {
                    tts.speak("Incorrect pessoa_id or password. Please try again.");
                    start();
                }
            } catch (SQLException e) {
                tts.speak("An error occurred during login. Please try again.");
                e.printStackTrace();
                start();
            }
        }
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

    

    private void handleAdminActions() {
        tts.speak("Welcome, Admin. Type 'list' to list all users or 'exit' to end the program.");

        String adminCommand = stt.getComando();
        switch (adminCommand.toLowerCase()) {
            case "list":
                try {
                    ResultSet allUsers = comandos.getAllUsers();
                    while (allUsers.next()) {
                        System.out.println("Name: " + allUsers.getString("name") + ", Age: " + allUsers.getString("age") + ", Blood Type: " + allUsers.getString("bloodType") + ", Can Donate: " + allUsers.getString("canDonate"));
                    }
                    tts.speak("User list printed to the terminal.");
                } catch (SQLException e) {
                    tts.speak("An error occurred while fetching the user list.");
                    e.printStackTrace();
                }
                handleAdminActions();
                break;
            case "exit":
                tts.speak("Exiting Admin mode. Goodbye!");
                System.exit(0);
            default:
                tts.speak("Command not recognized. Please try again.");
                handleAdminActions();
        }
    }

    private void handleUserExit() {
        String command = stt.getComando();
        if (command.equalsIgnoreCase("exit")) {
            tts.speak("Exiting. Goodbye!");
            System.exit(0);
        } else {
            tts.speak("Invalid command. Please type 'exit' to close the program.");
            handleUserExit();
        }
    }
}

