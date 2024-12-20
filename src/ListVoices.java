package src;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ListVoices {
    public static void main(String[] args) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager vm = VoiceManager.getInstance();
        Voice[] voices = vm.getVoices();

        if (voices.length == 0) {
            System.err.println("Nenhuma voz foi encontrada.");
        } else {
            System.out.println("Vozes disponíveis:");
            for (Voice voice : voices) {
                System.out.println(" - " + voice.getName());
            }
        }
    }
}
