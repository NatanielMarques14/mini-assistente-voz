package voz.src;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;


public class STT {
    public static void main(String[] args) throws Exception {
        // Ajusta o nível de log para SEVERE (apenas erros críticos)

        Configuration configuration = new Configuration();
        
        // Configuração do modelo acústico (mesmo que antes)
        configuration.setAcousticModelPath("file:resources/models/en-us");
        
        // Dicionário contendo as palavras usadas na gramática
        configuration.setDictionaryPath("file:resources/models/cmudict-en-us.dict");

        // Especificar a pasta onde estão as gramáticas
        configuration.setGrammarPath("file:resources/grammars/");
        
        // Nome da gramática (sem a extensão .gram)
        configuration.setGrammarName("idade");

        // Habilitar o uso da gramática
        configuration.setUseGrammar(true);

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        recognizer.startRecognition(true);

        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.println("diga: ");
            String comando = result.getHypothesis();
            System.out.println("Você disse: " + comando);
           
            if (comando.equalsIgnoreCase("exit")) {
                System.out.println("Encerrando...");
                break; // Sai do loop
            }
            
            // Outros ifs para lidar com cada comando
        }

        recognizer.stopRecognition();
    }
}
