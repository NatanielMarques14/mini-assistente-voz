package src;


public class Test {
    public static void main(String[] args) {
       
        STT sttC = new STT("junção");
        // Carregar a gramática "commands" antes de iniciar o reconheciment

        // Carregar a gramática "idade" conforme necessário
   
        String comando2 = sttC.getComando();
        System.out.println("Comando reconhecido (idade): " + comando2);

            
        
    }
}
