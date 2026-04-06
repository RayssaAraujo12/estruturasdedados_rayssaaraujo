package Projeto2;

import java.util.Random;

public class BuscaArvoreBSTAnalise {

    // ================= NÓ =================
    static class No {
        int valor;
        No esquerda, direita;

        No(int valor) {
            this.valor = valor;
        }
    }

    // ================= INSERÇÃO =================
    public static No inserir(No raiz, int valor) {
        if (raiz == null) return new No(valor);

        if (valor < raiz.valor)
            raiz.esquerda = inserir(raiz.esquerda, valor);
        else
            raiz.direita = inserir(raiz.direita, valor);

        return raiz;
    }

    // ================= BUSCA =================
    public static boolean buscar(No raiz, int chave) {
        if (raiz == null) return false;

        if (chave == raiz.valor) return true;
        if (chave < raiz.valor) return buscar(raiz.esquerda, chave);
        return buscar(raiz.direita, chave);
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

    // ================= GERAÇÃO DE ÁRVORE =================
    public static No gerarArvore(int tamanho) {
        Random rand = new Random();
        No raiz = null;

        for (int i = 0; i < tamanho; i++) {
            raiz = inserir(raiz, rand.nextInt(tamanho * 10));
        }

        return raiz;
    }

    // ================= TESTE =================
    public static void executarTeste(int tamanho, int repeticoes) {
        Random rand = new Random();

        No raiz = gerarArvore(tamanho);
        long[] tempos = new long[repeticoes];

        for (int i = 0; i < repeticoes; i++) {
            int chave = rand.nextInt(tamanho * 10);

            long inicio = System.nanoTime();
            buscar(raiz, chave);
            long fim = System.nanoTime();

            tempos[i] = fim - inicio;
        }

        double media = media(tempos);
        double desvio = desvioPadrao(tempos, media);

        System.out.println("========================================");
        System.out.println("Tamanho da árvore: " + tamanho);
        System.out.println("Média (ns): " + media);
        System.out.println("Desvio padrão (ns): " + desvio);
        System.out.println("Complexidade teórica:");
        System.out.println("- Melhor caso: O(log n)");
        System.out.println("- Médio caso: O(log n)");
        System.out.println("- Pior caso: O(n)");
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 5000, 10000, 50000};
        int repeticoes = 1000;

        for (int t : tamanhos) {
            executarTeste(t, repeticoes);
        }
    }
}
