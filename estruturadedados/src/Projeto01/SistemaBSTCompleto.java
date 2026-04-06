package Projeto01;

import java.util.*;

public class SistemaBSTCompleto {

    // ================= BST =================
    static class No {
        int valor;
        No esq, dir;

        No(int v) { valor = v; }
    }

    public static No inserir(No r, int v) {
        if (r == null) return new No(v);
        if (v < r.valor) r.esq = inserir(r.esq, v);
        else r.dir = inserir(r.dir, v);
        return r;
    }

    public static No remover(No r, int v) {
        if (r == null) return null;

        if (v < r.valor) r.esq = remover(r.esq, v);
        else if (v > r.valor) r.dir = remover(r.dir, v);
        else {
            if (r.esq == null) return r.dir;
            if (r.dir == null) return r.esq;

            No suc = menor(r.dir);
            r.valor = suc.valor;
            r.dir = remover(r.dir, suc.valor);
        }
        return r;
    }

    public static No menor(No r) {
        while (r.esq != null) r = r.esq;
        return r;
    }

    public static boolean buscar(No r, int v) {
        if (r == null) return false;
        if (v == r.valor) return true;
        return v < r.valor ? buscar(r.esq, v) : buscar(r.dir, v);
    }

    public static int altura(No r) {
        if (r == null) return -1;
        return 1 + Math.max(altura(r.esq), altura(r.dir));
    }

    // ================= TSP =================
    public static double tsp(double[][] dist) {
        int n = dist.length;
        boolean[] visitado = new boolean[n];
        int atual = 0;
        visitado[0] = true;
        double custo = 0;

        for (int i = 1; i < n; i++) {
            int prox = -1;
            double menor = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!visitado[j] && dist[atual][j] < menor) {
                    menor = dist[atual][j];
                    prox = j;
                }
            }

            visitado[prox] = true;
            custo += menor;
            atual = prox;
        }

        return custo + dist[atual][0];
    }

    static double[][] gerarMatriz(int n) {
        Random r = new Random();
        double[][] m = new double[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                m[i][j] = (i == j) ? 0 : 1 + r.nextInt(100);

        return m;
    }

    // ================= ESTATÍSTICA =================
    static double media(double[] v) {
        double s = 0;
        for (double x : v) s += x;
        return s / v.length;
    }

    static double desvio(double[] v, double m) {
        double s = 0;
        for (double x : v) s += Math.pow(x - m, 2);
        return Math.sqrt(s / v.length);
    }

    // ================= EXPERIMENTO =================
    static void experimento(int tamanho) {
        int exec = 30;
        double[] tBST = new double[exec];
        double[] tTSP = new double[exec];

        Random rand = new Random();

        for (int i = 0; i < exec; i++) {

            No raiz = null;

            // Inserção
            for (int j = 0; j < tamanho; j++)
                raiz = inserir(raiz, rand.nextInt(tamanho * 10));

            int chave = rand.nextInt(tamanho * 10);

            long ini = System.nanoTime();
            buscar(raiz, chave);
            remover(raiz, chave);
            altura(raiz);
            long fim = System.nanoTime();

            tBST[i] = fim - ini;

            double[][] m = gerarMatriz(Math.max(5, tamanho / 10));

            long ini2 = System.nanoTime();
            tsp(m);
            long fim2 = System.nanoTime();

            tTSP[i] = fim2 - ini2;
        }

        double mBST = media(tBST);
        double dBST = desvio(tBST, mBST);

        double mTSP = media(tTSP);
        double dTSP = desvio(tTSP, mTSP);

        System.out.println("============================");
        System.out.println("Tamanho: " + tamanho);

        System.out.println("BST:");
        System.out.println("Média: " + mBST);
        System.out.println("Desvio: " + dBST);
        System.out.println("Teoria: O(log n) médio / O(n) pior");

        System.out.println("\nTSP:");
        System.out.println("Média: " + mTSP);
        System.out.println("Desvio: " + dTSP);
        System.out.println("Teoria: O(n²)");
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 5000, 10000};

        for (int t : tamanhos)
            experimento(t);
    }
}
