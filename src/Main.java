public class TTS {
    public static void main(String[] args) {
        try {
            // Configura a voz
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_arctic_low");

            // Cria a voz
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            if (voice != null) {
                voice.allocate(); // Aloca recursos para a voz
                voice.speak("Olá, como você está?"); // Fala o texto
                voice.deallocate(); // Libera os recursos
            } else {
                System.out.println("Voz não encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
