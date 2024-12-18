package src;


public class Test {
    public static void main(String[] args) {
       
        STT sttC = new STT("idade");
        STT sttI = new STT("commands");
        // Carregar a gramática "commands" antes de iniciar o reconhecimento
   
        String comando1 = sttI.getComando();
        System.out.println("Comando reconhecido (commands): " + comando1);

        // Carregar a gramática "idade" conforme necessário
   
        String comando2 = sttC.getComando();
        System.out.println("Comando reconhecido (idade): " + comando2);

            
        
    }
}
