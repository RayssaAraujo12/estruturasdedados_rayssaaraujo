package Projeto2;

import java.util.Arrays;
import java.util.Random;

public class BuscaBinariaAnalise {

    // Método de busca binária
    public static int buscaBinaria(int[] vetor, int chave) {
        int inicio = 0;
        int fim = vetor.length - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;

            if (vetor[meio] == chave) {
                return meio;
            } else if (vetor[meio] < chave) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return -1;
    }

    // Média
    public static double calcularMedia(long[] tempos) {
        long soma = 0;
        for (long t : tempos) {
            soma += t;
        }
        return (double) soma / tempos.length;
    }

    // Desvio padrão
    public static double calcularDesvioPadrao(long[] tempos, double media) {
        double soma = 0;
        for (long t : tempos) {
            soma += Math.pow(t - media, 2);
        }
        return Math.sqrt(soma / tempos.length);
    }

    // Gerar vetor ordenado
    public static int[] gerarVetorOrdenado(int tamanho) {
        Random rand = new Random();
        int[] vetor = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            vetor[i] = rand.nextInt(tamanho * 10);
        }

        Arrays.sort(vetor);
        return vetor;
    }

    public static void executarTeste(int tamanho, int repeticoes) {
        int[] vetor = gerarVetorOrdenado(tamanho);
        Random rand = new Random();

        long[] tempos = new long[repeticoes];

        for (int i = 0; i < repeticoes; i++) {
            int chave = vetor[rand.nextInt(tamanho)]; // garante que existe

            long inicio = System.nanoTime();
            buscaBinaria(vetor, chave);
            long fim = System.nanoTime();

            tempos[i] = fim - inicio;
        }

        double media = calcularMedia(tempos);
        double desvio = calcularDesvioPadrao(tempos, media);

        System.out.println("Tamanho do vetor: " + tamanho);
        System.out.println("Média (ns): " + media);
        System.out.println("Desvio padrão (ns): " + desvio);
        System.out.println("Complexidade teórica: O(log n)");
        System.out.println("-------------------------------");
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 5000, 10000, 50000, 100000};
        int repeticoes = 1000;

        for (int tamanho : tamanhos) {
            executarTeste(tamanho, repeticoes);
        }
    }
}
