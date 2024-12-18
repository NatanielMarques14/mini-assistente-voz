package src;

import javax.sound.sampled.*;

public class AudioTest {
    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            16000.0f, // Taxa de amostragem
            16,       // Resolução em bits
            1,        // Canais (mono)
            2,        // Tamanho do frame em bytes (16 bits = 2 bytes)
            16000.0f, // Taxa de quadros
            false     // Little-endian
        );

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Formato de áudio não suportado.");
            return;
        }

        try {
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            System.out.println("Capturando áudio. Pressione Ctrl+C para parar.");

            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                // Aqui você pode processar os bytes de áudio conforme necessário
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
