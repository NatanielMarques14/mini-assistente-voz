package src;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class STT {
    private LiveSpeechRecognizer recognizer;
    private Configuration configuration;
    private boolean isRecognizing;

    // Construtor: Inicializa a configuração e o reconhecedor
    public STT(String gramatica) {
        try {
            // Ajusta o nível de log para SEVERE (apenas erros críticos)
            Logger.getLogger("edu.cmu.sphinx").setLevel(Level.SEVERE);

            configuration = new Configuration();

            // Configuração do modelo acústico
            configuration.setAcousticModelPath("file:resources/models/en-us");

            // Dicionário contendo as palavras usadas na gramática
            configuration.setDictionaryPath("file:resources/models/cmudict-en-us.dict");

            // Especificar a pasta onde estão as gramáticas
            configuration.setGrammarPath("file:resources/grammars/");

            // Nome da gramática (sem a extensão .gram)
            configuration.setGrammarName(gramatica);

            // Habilitar o uso da gramática
            configuration.setUseGrammar(true);

            // Inicializa o reconhecedor
            recognizer = new LiveSpeechRecognizer(configuration);
            isRecognizing = false;

        } catch (Exception e) {
            System.err.println("Erro ao configurar o reconhecedor de voz:");
            e.printStackTrace();
        }
    }

    public String getComando() {
        String fala = null;
        SpeechResult result;
        try {
            recognizer.startRecognition(true);
            isRecognizing = true;
            System.out.println("Diga o comando:");

            // Obtém o resultado da fala
            while ((result = recognizer.getResult()) != null) {
                String comando = result.getHypothesis();
                System.out.println("Você disse: " + comando);

                if (comando != null) {
                    System.out.println("Encerrando...");
                    fala = comando;
                    break; // Sai do loop
                }

                // Outros ifs para lidar com cada comando
            }

        } catch (Exception e) {
            System.err.println("Erro durante o reconhecimento de voz:");
            e.printStackTrace();
        } finally {
            if (isRecognizing) {
                try {
                    recognizer.stopRecognition();
                    isRecognizing = false;
                } catch (IllegalStateException e) {
                    System.err.println("Tentativa de parar reconhecimento quando não estava ativo.");
                }
            }
        }

        return fala;
    }
}
