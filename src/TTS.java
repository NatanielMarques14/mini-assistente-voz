package src;

import com.sun.speech.freetts.*;

public class TTS {
    private static final String VOICE_NAME = "kevin16";

    public static void speak(String message) {
        Voice voice = VoiceManager.getInstance().getVoice(VOICE_NAME);
        if (voice == null) {
            System.out.println("Voice not found!");
            return;
        }
        voice.allocate();
        voice.speak(message);
        voice.deallocate();
    }
}

//teste


