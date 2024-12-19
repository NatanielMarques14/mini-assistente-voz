package src;

import java.io.IOException;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] var0) throws SQLException, IOException 
    {
        System.out.println("Bem-vindo ao seu Mini-Assistente de Voz!");
        Comandos comandos = new Comandos(null);
        Assistente assistente = new Assistente();
        BancoDados bancoDeDados = new BancoDados();
        STT stt = new STT(null);
        TTS tts = new TTS();
        System.out.println("Bem-vindo ao seu Mini-Assistente de Voz!");
    }
}
