package assistente;

import voz.TTS;
import voz.STT;
import comandos.Comandos;
import dados.BancoDados;

import java.io.IOException;
import java.sql.SQLException;

public class Assistente {
    private TTS tts;
    private STT stt;
    private Comandos commands;

    public Assistente() throws SQLException, IOException {
        tts = new TTS();
        stt = new STT();
        BancoDados db = new BancoDados();
        comandos = new Comandos(db);
    }

    public void start() {
        tts.speak("Olá, o que gostaria de fazer? Registro, entrar ou sair?");
        String command = stt.listen();

        switch (command.toLowerCase()) {
            case "registro":
                handleRegistration();
                break;
            case "entrar":
                handleLogin();
                break;
            case "sair":
                tts.speak("Saindo. Até mais!");
                System.exit(0);
            default:
                tts.speak("Comando não reconhecido. Tente novamente.");
        }
    }

    private void handleRegistration() {
        tts.speak("Diga o seu nome.");
        String name = stt.listen();

        tts.speak("Diga sua idade.");
        int age = Integer.parseInt(stt.listen());

        tts.speak("Diga seu tipo sanguíneo.");
        String bloodType = stt.listen();

        tts.speak("Diga seu nome de usuário.");
        String username = stt.listen();

        tts.speak("Diga sua senha.");
        String password = stt.listen();

        tts.speak("Você está apto para doar sangue?");
        String canDonate = stt.listen();

        boolean success = commands.register(name, age, bloodType, username, password, canDonate);
        if (success) tts.speak("Registro concluído com sucesso!");
        else tts.speak("Erro ao registrar. Tente novamente.");
    }

    private void handleLogin() {
        tts.speak("Diga seu nome de usuário.");
        String username = stt.listen();

        tts.speak("Diga sua senha.");
        String password = stt.listen();

        String user = commands.login(username, password);
        if (user != null) {
            tts.speak("Bem-vindo, " + user + "! O que você gostaria de saber?");
        } else {
            tts.speak("Usuário ou senha incorretos. Tente novamente.");
        }
    }
}
