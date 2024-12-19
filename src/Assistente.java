package src;

import src.TTS;
import src.STT;
import src.Comandos;

import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;

public class Assistente {
    private final TTS tts;
    private final STT stt;
    private final Comandos comandos;
    private static int userCounter = 1; // Counter for automatic username and password generation

    public Assistente() throws SQLException {
        tts = new TTS();
        stt = new STT("junção");
        comandos = new Comandos();
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
        String age = stt.getComando();

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
        try (Scanner scanner = new Scanner(System.in)) {
            tts.speak("Enter your username.");
            System.out.print("Username: ");
            String username = scanner.nextLine();

            tts.speak("Enter your password.");
            System.out.print("Password: ");
            String password = scanner.nextLine();

            boolean loginSuccess = comandos.login(username, password);
            if (loginSuccess) {
                System.out.println("Login successful! Options: 'exit' or 'list'");
                handlePostLogin();
            } else {
                tts.speak("Incorrect username or password. Please try again.");
                start();
            }
        }
    }

    private void handlePostLogin() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "exit":
                    tts.speak("Exiting. Goodbye!");
                    System.exit(0);
                    break;
                case "list":
                    comandos.listUsers();
                    System.out.println("Options: 'exit' or 'list'");
                    handlePostLogin();
                    break;
                default:
                    tts.speak("Command not recognized. Please try again.");
                    handlePostLogin();
            }
        }
    }
}
