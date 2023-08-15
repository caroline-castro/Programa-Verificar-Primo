import java.util.ArrayList;
import java.util.List;

public class ChecagemPrimo implements Runnable {

    private Long numero;  // Altera o tipo do número para Long

    private static List<String> resultados = new ArrayList<>();
    private static final Object lock = new Object();

    public ChecagemPrimo(Long numero) {  // Atualiza o construtor para receber Long
        this.numero = numero;
    }

    public void run() {

        // Bloco synchronized para garantir exclusão mútua ao adicionar resultados
        synchronized (lock) {
            if (isPrime(numero)) {

                resultados.add("O número " + numero + " é primo.");
                // Adiciona mensagem ao resultado indicando que o número é primo

            } else {

                resultados.add("O número " + numero + " não é primo.");
                // Adiciona mensagem ao resultado indicando que o número não é primo
            }
        }
    }

    private boolean isPrime(Long num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Thread[] threads = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            Long num = new Long(args[i]);
            ChecagemPrimo checagemPrimo = new ChecagemPrimo(num);
            threads[i] = new Thread(checagemPrimo);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (String resultado : resultados) {
            System.out.println(resultado);
        }
    }
}
