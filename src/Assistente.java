package src;


import src.STT;
import src.Comandos;

import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;

public class Assistente {

    private final STT stt;
    private final Comandos comandos;
    private static int userCounter = 1; // Counter for automatic username and password generation

    public Assistente() throws SQLException {

        stt = new STT("junção");
        comandos = new Comandos();
    }

    public void start() {
        System.out.println("Hello, what would you like to do? Register, login, or exit?");
        String command = stt.getComando("comando");

        switch (command.toLowerCase()) {
            case "register":
                handleRegistration();
                break;
            case "login":
                handleLogin();
                break;
            case "exit":
            System.out.println("Exiting. Goodbye!");
            System.exit(0);
            default:
                System.out.println("Command not recognized. Please try again.");
                start();
                break;
        }
    }

    private void handleRegistration() {
        System.out.println("Please say your name.");
        String name = stt.getComando("nome");

        System.out.println("Please say your age.");
        String age = stt.getComando("idade");

        System.out.println("Please say your blood type.");
        String bloodType = stt.getComando("tipo sanguineo");

        System.out.println("Are you eligible to donate blood? Answer 'Yes' or 'No'.");
        String canDonate = stt.getComando("se é doador");

        String username = "user" + userCounter;
        String password = String.valueOf(userCounter);
        userCounter++;

        boolean success = comandos.register(name, age, bloodType, canDonate, username, password);
        if (success) {
        System.out.println("New user registered: Username: " + username + ", Password: " + password);
        System.out.println("Registration successful! Your username is: " + username + " and your password is: " + password);
        } else {
            System.out.println("Error during registration. Please try again.");
        }
        start();
    }

    private void handleLogin() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your username.");
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.println("Enter your password.");
            System.out.print("Password: ");
            String password = scanner.nextLine();

            boolean loginSuccess = comandos.login(username, password);
            if (loginSuccess) {
                System.out.println("Login successful! Options: 'exit' or 'list'");
                handlePostLogin();
            } else {
                System.out.println("Incorrect username or password. Please try again.");
                start();
            }
        }
    }

    private void handlePostLogin() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "exit":
                    System.out.println("Exiting. Goodbye!");
                    System.exit(0);
                    break;
                case "list":
                    comandos.listUsers();
                    System.out.println("Options: 'exit' or 'list'");
                    handlePostLogin();
                    break;
                default:
                    System.out.println("Command not recognized. Please try again.");
                    handlePostLogin();
            }
        }
    }
}