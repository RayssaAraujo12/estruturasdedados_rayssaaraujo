package Projeto2;

import java.util.Random;

public class BuscaSequencialAnalise {

    // Método de busca sequencial
    public static int buscaSequencial(int[] vetor, int chave) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] == chave) {
                return i;
            }
        }
        return -1;
    }

    // Método para calcular média
    public static double calcularMedia(long[] tempos) {
        long soma = 0;
        for (long t : tempos) {
            soma += t;
        }
        return (double) soma / tempos.length;
    }

    // Método para calcular desvio padrão
    public static double calcularDesvioPadrao(long[] tempos, double media) {
        double soma = 0;
        for (long t : tempos) {
            soma += Math.pow(t - media, 2);
        }
        return Math.sqrt(soma / tempos.length);
    }

    // Geração de vetor aleatório
    public static int[] gerarVetor(int tamanho) {
        Random rand = new Random();
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = rand.nextInt(tamanho * 10);
        }
        return vetor;
    }

    public static void executarTeste(int tamanho, int repeticoes) {
        int[] vetor = gerarVetor(tamanho);
        Random rand = new Random();

        long[] tempos = new long[repeticoes];

        for (int i = 0; i < repeticoes; i++) {
            int chave = vetor[rand.nextInt(tamanho)]; // garante que existe

            long inicio = System.nanoTime();
            buscaSequencial(vetor, chave);
            long fim = System.nanoTime();

            tempos[i] = fim - inicio;
        }

        double media = calcularMedia(tempos);
        double desvio = calcularDesvioPadrao(tempos, media);

        System.out.println("Tamanho do vetor: " + tamanho);
        System.out.println("Média (ns): " + media);
        System.out.println("Desvio padrão (ns): " + desvio);
        System.out.println("Complexidade teórica: O(n)");
        System.out.println("-------------------------------");
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 5000, 10000, 50000};
        int repeticoes = 1000;

        for (int tamanho : tamanhos) {
            executarTeste(tamanho, repeticoes);
        }
    }
}
