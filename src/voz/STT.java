

package src.voz;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.IOException;

public class STT {
    private LiveSpeechRecognizer recognizer;

    public STT() throws IOException {
        Configuration config = new Configuration();
        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        recognizer = new LiveSpeechRecognizer(config);
    }

    public String listen() {
        recognizer.startRecognition(true);
        String result = recognizer.getResult().getHypothesis();
        recognizer.stopRecognition();
        return result;
    }
}
