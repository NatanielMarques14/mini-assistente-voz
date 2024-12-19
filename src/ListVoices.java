package src;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ListVoices {
    public static void main(String[] args) {
        VoiceManager vm = VoiceManager.getInstance();
        Voice[] voices = vm.getVoices();

        System.out.println("Vozes Disponíveis:");
        for (Voice voice : voices) {
            System.out.println(" - " + voice.getName());
        }
    }
}
