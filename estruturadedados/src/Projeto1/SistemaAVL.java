package Projeto1;

import java.util.*;

public class SistemaAVL {

    // ================= NÓ AVL =================
    static class No {
        int valor, altura;
        No esq, dir;

        No(int v) {
            valor = v;
            altura = 1;
        }
    }

    static int altura(No n) {
        return (n == null) ? 0 : n.altura;
    }

    static int balance(No n) {
        return (n == null) ? 0 : altura(n.esq) - altura(n.dir);
    }

    static No rotDir(No y) {
        No x = y.esq;
        No t2 = x.dir;

        x.dir = y;
        y.esq = t2;

        y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;
        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;

        return x;
    }

    static No rotEsq(No x) {
        No y = x.dir;
        No t2 = y.esq;

        y.esq = x;
        x.dir = t2;

        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;
        y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;

        return y;
    }

    static No inserir(No n, int v) {
        if (n == null) return new No(v);

        if (v < n.valor) n.esq = inserir(n.esq, v);
        else if (v > n.valor) n.dir = inserir(n.dir, v);
        else return n;

        n.altura = 1 + Math.max(altura(n.esq), altura(n.dir));

        int b = balance(n);

        if (b > 1 && v < n.esq.valor) return rotDir(n);
        if (b < -1 && v > n.dir.valor) return rotEsq(n);

        if (b > 1 && v > n.esq.valor) {
            n.esq = rotEsq(n.esq);
            return rotDir(n);
        }

        if (b < -1 && v < n.dir.valor) {
            n.dir = rotDir(n.dir);
            return rotEsq(n);
        }

        return n;
    }

    static No min(No n) {
        while (n.esq != null) n = n.esq;
        return n;
    }

    static No remover(No n, int v) {
        if (n == null) return null;

        if (v < n.valor) n.esq = remover(n.esq, v);
        else if (v > n.valor) n.dir = remover(n.dir, v);
        else {
            if (n.esq == null) return n.dir;
            if (n.dir == null) return n.esq;

            No suc = min(n.dir);
            n.valor = suc.valor;
            n.dir = remover(n.dir, suc.valor);
        }

        n.altura = 1 + Math.max(altura(n.esq), altura(n.dir));
        int b = balance(n);

        if (b > 1 && balance(n.esq) >= 0) return rotDir(n);
        if (b > 1 && balance(n.esq) < 0) {
            n.esq = rotEsq(n.esq);
            return rotDir(n);
        }

        if (b < -1 && balance(n.dir) <= 0) return rotEsq(n);
        if (b < -1 && balance(n.dir) > 0) {
            n.dir = rotDir(n.dir);
            return rotEsq(n);
        }

        return n;
    }

    static boolean buscar(No n, int v) {
        if (n == null) return false;
        if (v == n.valor) return true;
        return v < n.valor ? buscar(n.esq, v) : buscar(n.dir, v);
    }

    // ================= TSP =================
    static double tsp(double[][] d) {
        int n = d.length;
        boolean[] vis = new boolean[n];
        int atual = 0;
        vis[0] = true;
        double custo = 0;

        for (int i = 1; i < n; i++) {
            int prox = -1;
            double menor = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!vis[j] && d[atual][j] < menor) {
                    menor = d[atual][j];
                    prox = j;
                }
            }

            vis[prox] = true;
            custo += menor;
            atual = prox;
        }

        return custo + d[atual][0];
    }

    static double[][] matriz(int n) {
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
        double[] tAVL = new double[exec];
        double[] tTSP = new double[exec];

        Random rand = new Random();

        for (int i = 0; i < exec; i++) {
            No raiz = null;

            for (int j = 0; j < tamanho; j++)
                raiz = inserir(raiz, rand.nextInt(tamanho * 10));

            int chave = rand.nextInt(tamanho * 10);

            long ini = System.nanoTime();
            buscar(raiz, chave);
            remover(raiz, chave);
            altura(raiz);
            long fim = System.nanoTime();

            tAVL[i] = fim - ini;

            double[][] m = matriz(Math.max(5, tamanho / 10));

            long ini2 = System.nanoTime();
            tsp(m);
            long fim2 = System.nanoTime();

            tTSP[i] = fim2 - ini2;
        }

        double mAVL = media(tAVL);
        double dAVL = desvio(tAVL, mAVL);

        double mTSP = media(tTSP);
        double dTSP = desvio(tTSP, mTSP);

        System.out.println("========================");
        System.out.println("Tamanho: " + tamanho);

        System.out.println("AVL:");
        System.out.println("Média: " + mAVL);
        System.out.println("Desvio: " + dAVL);
        System.out.println("Teoria: O(log n)");

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

