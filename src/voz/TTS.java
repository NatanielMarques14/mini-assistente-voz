package voz;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TTS {
    private Voice voice;

    public TTS() {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");

        if (voice == null) {
            System.err.println("Erro: Não foi possível carregar a voz.");
            System.exit(1);
        }

        voice.allocate();
    }

    public void speak(String text) {
        voice.speak(text);
    }
}
