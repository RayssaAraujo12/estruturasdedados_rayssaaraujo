package Projeto1.CaxeiroV;

import java.util.*;

public class CaxeiroViajante {

    static final int EXECUCOES = 5;
    static final int[] TAMANHOS = {100, 1000, 1500};

    // ================= GERAÇÃO DO GRAFO =================
    public static double[][] gerarGrafo(int n) {
        Random rand = new Random();
        double[][] grafo = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = 1 + rand.nextInt(100);
                grafo[i][j] = dist;
                grafo[j][i] = dist;
            }
        }
        return grafo;
    }

    // ================= TSP - VIZINHO MAIS PRÓXIMO =================
    public static double tspVizinhoMaisProximo(double[][] grafo) {
        int n = grafo.length;
        boolean[] visitado = new boolean[n];

        int atual = 0;
        visitado[0] = true;
        double custoTotal = 0;

        for (int i = 1; i < n; i++) {
            int proximo = -1;
            double menorDist = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!visitado[j] && grafo[atual][j] < menorDist) {
                    menorDist = grafo[atual][j];
                    proximo = j;
                }
            }

            visitado[proximo] = true;
            custoTotal += menorDist;
            atual = proximo;
        }

        // retorna ao início
        custoTotal += grafo[atual][0];

        return custoTotal;
    }

    // ================= TESTES =================
    public static void executarTestes() {
        for (int tamanho : TAMANHOS) {
            System.out.println("\n===============================");
            System.out.println("Tamanho do grafo: " + tamanho);
            System.out.println("===============================");

            double somaTempo = 0;
            double somaCusto = 0;

            for (int i = 1; i <= EXECUCOES; i++) {
                double[][] grafo = gerarGrafo(tamanho);

                long inicio = System.nanoTime();
                double custo = tspVizinhoMaisProximo(grafo);
                long fim = System.nanoTime();

                double tempoMs = (fim - inicio) / 1_000_000.0;

                somaTempo += tempoMs;
                somaCusto += custo;

                System.out.println("Execução " + i +
                        " | Tempo: " + tempoMs + " ms" +
                        " | Custo: " + custo);
            }

            System.out.println("\nMÉDIA:");
            System.out.println("Tempo médio: " + (somaTempo / EXECUCOES) + " ms");
            System.out.println("Custo médio: " + (somaCusto / EXECUCOES));
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        executarTestes();
    }
}