package src;

import src.TTS;
import src.STT; //classes importadas do pacote voz representam as funcionalidades de entrada e saida de voz
import src.Comandos;//importa a classe Comandos
import src.BancoDados;//pra gerenciar a conexão do banco com as operações de inserir e buscar dados

import java.io.IOException; // para lidar com os erros da entrada e saída de dados
import java.sql.SQLException; //para lidar com os erros do sql

public class Assistente { //Assistente é a classe principal que vai organizar o comportamento do assistente, as outras classes precisam instância-la para acessar as suas funcionalidades
    private final TTS tts;
    private final STT stt;
    private Comandos comandos; //sao privates pois são usados pela classe internamente

    public Assistente() throws SQLException, IOException { //construtor, chamado toda vez que é criado um objeto da classe, também pode lançar exceções
        tts = new TTS();
        stt = new STT();
        BancoDados db = new BancoDados();
        comandos = new Comandos(db);
    }

    public void start() { //inicia o mini-assistente
        tts.speak("Olá, o que gostaria de fazer? Registro, entrar ou sair?");
        String comando = stt.listen();

        switch (comando.toLowerCase()) {
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

    private void handleRegistration() { //faz o registro
        tts.speak("Diga o seu nome.");
        String name = stt.listen();

        tts.speak("Diga sua idade.");
        int age = Integer.parseInt(stt.listen()); //como o listen retorna uma string usamos a função do parseInt para converter em int

        tts.speak("Diga seu tipo sanguíneo.");
        String bloodType = stt.listen();

        tts.speak("Diga seu nome de usuário.");
        String username = stt.listen();

        tts.speak("Diga sua senha.");
        String password = stt.listen();

        tts.speak("Você está apto para doar sangue?");
        String canDonate = stt.listen();

        boolean success = comandos.register(name, age, bloodType, username, password, canDonate); //salva o registro no banco de dados
        if (success) {
            tts.speak("Registro concluído com sucesso!");
        }
        
        else{ tts.speak("Erro ao registrar. Tente novamente.");
        }
        tts.speak("O que você gostaria de fazer agora? Registro, entrar ou sair?"); //dá a possibilidade de escolher outras coisas para fazer após o registro
        start(); 
    }

    private void handleLogin() {//faz o login 
        tts.speak("Diga seu nome de usuário.");
        String username = stt.listen();

        tts.speak("Diga sua senha.");
        String password = stt.listen();

        String user = comandos.login(username, password);
        if (user != null) {
            tts.speak("Bem-vindo, " + user + "! O que você gostaria de saber?");
        } else {
            tts.speak("Usuário ou senha incorretos. Tente novamente.");
        }
    }
}
