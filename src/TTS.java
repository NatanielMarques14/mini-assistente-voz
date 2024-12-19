package src;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TTS {
    private Voice voice;

    public TTS() {
        try {
            VoiceManager vm = VoiceManager.getInstance();
            // Escolhe a voz padrão do sistema
            voice = vm.getVoice("kevin16");
            if (voice == null) {
                throw new IllegalStateException("Voz 'kevin16' não encontrada.");
            }
            voice.allocate();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar TTS: " + e.getMessage());
        }
    }

    public void speak(String text) {
        try {
            if (voice != null) {
                voice.speak(text);
            } else {
                System.err.println("Voz não alocada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao falar: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (voice != null) {
                voice.deallocate();
            }
        } catch (Exception e) {
            System.err.println("Erro ao encerrar TTS: " + e.getMessage());
        }
    }
}
