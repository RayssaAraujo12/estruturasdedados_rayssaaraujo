package Projeto3;

import java.util.*;

public class BenchmarkOrdenacao {

    // ================= BUBBLE SORT =================
    public static void bubbleSort(int[] v) {
        int n = v.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (v[j] > v[j + 1]) {
                    int temp = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = temp;
                }
            }
        }
    }

    // ================= INSERTION SORT =================
    public static void insertionSort(int[] v) {
        for (int i = 1; i < v.length; i++) {
            int chave = v[i];
            int j = i - 1;

            while (j >= 0 && v[j] > chave) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }
    }

    // ================= ESTATÍSTICA =================
    public static double media(long[] valores) {
        long soma = 0;
        for (long v : valores) soma += v;
        return (double) soma / valores.length;
    }

    public static double desvioPadrao(long[] valores, double media) {
        double soma = 0;
        for (long v : valores) soma += Math.pow(v - media, 2);
        return Math.sqrt(soma / valores.length);
    }

    // ================= CASOS =================
    public static int[] melhorCaso(int tamanho) {
        int[] v = new int[tamanho];
        for (int i = 0; i < tamanho; i++) v[i] = i;
        return v;
    }

    public static int[] piorCaso(int tamanho) {
        int[] v = new int[tamanho];
        for (int i = 0; i < tamanho; i++) v[i] = tamanho - i;
        return v;
    }

    public static int[] casoMedio(int tamanho) {
        Random rand = new Random();
        int[] v = new int[tamanho];
        for (int i = 0; i < tamanho; i++) v[i] = rand.nextInt(tamanho * 10);
        return v;
    }

    // ================= TESTE =================
    public static void testar(String tipoCaso, int tamanho, int repeticoes) {
        long[] temposBubble = new long[repeticoes];
        long[] temposInsertion = new long[repeticoes];

        for (int i = 0; i < repeticoes; i++) {
            int[] base;

            switch (tipoCaso) {
                case "melhor": base = melhorCaso(tamanho); break;
                case "pior": base = piorCaso(tamanho); break;
                default: base = casoMedio(tamanho);
            }

            int[] v1 = base.clone();
            int[] v2 = base.clone();

            // Bubble
            long iniB = System.nanoTime();
            bubbleSort(v1);
            long fimB = System.nanoTime();
            temposBubble[i] = fimB - iniB;

            // Insertion
            long iniI = System.nanoTime();
            insertionSort(v2);
            long fimI = System.nanoTime();
            temposInsertion[i] = fimI - iniI;
        }

        double mediaB = media(temposBubble);
        double desvioB = desvioPadrao(temposBubble, mediaB);

        double mediaI = media(temposInsertion);
        double desvioI = desvioPadrao(temposInsertion, mediaI);

        System.out.println("========================================");
        System.out.println("Caso: " + tipoCaso.toUpperCase());
        System.out.println("Tamanho: " + tamanho);

        System.out.println("\nBubble Sort:");
        System.out.println("Média (ns): " + mediaB);
        System.out.println("Desvio padrão (ns): " + desvioB);
        System.out.println("Teoria: Melhor O(n), Médio/Pior O(n²)");

        System.out.println("\nInsertion Sort:");
        System.out.println("Média (ns): " + mediaI);
        System.out.println("Desvio padrão (ns): " + desvioI);
        System.out.println("Teoria: Melhor O(n), Médio/Pior O(n²)");
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 3000, 5000};
        int repeticoes = 100;

        String[] casos = {"melhor", "medio", "pior"};

        for (String caso : casos) {
            for (int t : tamanhos) {
                testar(caso, t, repeticoes);
            }
        }
    }
}
