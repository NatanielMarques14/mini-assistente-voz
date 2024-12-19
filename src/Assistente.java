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
    private static int userCounter = 1; // Counter for automatic username and password generation

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
        }
    }

    private void handleRegistration() {
        tts.speak("Please say your name.");
        String name = stt.getComando();

        tts.speak("Please say your age.");
        int age = Integer.parseInt(stt.getComando());

        tts.speak("Please say your blood type.");
        String bloodType = stt.getComando();

        tts.speak("Are you eligible to donate blood? Answer 'Yes' or 'No'.");
        String canDonate = stt.getComando();

        String username = "user" + userCounter;
        String password = String.valueOf(userCounter);
        userCounter++;

        boolean success = comandos.register(name, age, bloodType, canDonate, username, password);
        if (success) {
            System.out.println("New user registered: Username: " + username + ", Password: " + password);
            tts.speak("Registration successful! Your username is: " + username + " and your password is: " + password);
        } else {
            tts.speak("Error during registration. Please try again.");
        }
        start();
    }

    private void handleLogin() {
        tts.speak("Please say your username.");
        String username = stt.getComando();

        tts.speak("Please say your password.");
        String password = stt.getComando();

        if (username.equals("admin") && password.equals("1234")) {
            handleAdminActions();
        } else {
            try {
                ResultSet userInfo = comandos.getUserInfo(username, password);
                if (userInfo.next()) {
                    System.out.println("User information: Name: " + userInfo.getString("name") + ", Age: " + userInfo.getInt("age") + ", Blood Type: " + userInfo.getString("bloodType") + ", Can Donate: " + userInfo.getString("canDonate"));
                    tts.speak("Welcome, " + userInfo.getString("name") + "! Type 'exit' to end the program.");
                    handleUserExit();
                } else {
                    tts.speak("Incorrect username or password. Please try again.");
                    start();
                }
            } catch (SQLException e) {
                tts.speak("An error occurred during login. Please try again.");
                e.printStackTrace();
                start();
            }
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
                        System.out.println("Name: " + allUsers.getString("name") + ", Age: " + allUsers.getInt("age") + ", Blood Type: " + allUsers.getString("bloodType") + ", Can Donate: " + allUsers.getString("canDonate"));
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

