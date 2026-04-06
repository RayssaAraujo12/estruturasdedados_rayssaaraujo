package Projeto01.RubroNegra;

import java.util.*;

public class RubroNegra {

    private final int RED = 0;
    private final int BLACK = 1;

    class Node {
        int key, color;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
            color = RED;
        }
    }

    private Node root;

    // ================= BUSCA =================
    public Node search(Node root, int key) {
        if (root == null || root.key == key)
            return root;
        return key < root.key ? search(root.left, key) : search(root.right, key);
    }

    // ================= ROTAÇÕES =================
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null) y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != null) x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null) root = x;
        else if (y == y.parent.right) y.parent.right = x;
        else y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    // ================= INSERÇÃO =================
    public void insert(int key) {
        Node node = new Node(key);
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            x = (node.key < x.key) ? x.left : x.right;
        }

        node.parent = y;

        if (y == null) root = node;
        else if (node.key < y.key) y.left = node;
        else y.right = node;

        fixInsert(node);
    }

    private void fixInsert(Node k) {
        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                Node u = k.parent.parent.right;

                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                Node u = k.parent.parent.left;

                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    // ================= REMOÇÃO =================
    private void transplant(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;

        if (v != null) v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void delete(int key) {
        Node z = search(root, key);
        if (z == null) return;

        Node y = z;
        int yOriginalColor = y.color;
        Node x;

        if (z.left == null) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent != z) {
                transplant(y, y.right);
                y.right = z.right;
                if (y.right != null) y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            if (y.left != null) y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK && x != null)
            fixDelete(x);
    }

    private void fixDelete(Node x) {
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                if ((w.left == null || w.left.color == BLACK) &&
                    (w.right == null || w.right.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right == null || w.right.color == BLACK) {
                        if (w.left != null) w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.right != null) w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                if ((w.right == null || w.right.color == BLACK) &&
                    (w.left == null || w.left.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left == null || w.left.color == BLACK) {
                        if (w.right != null) w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.left != null) w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    // ================= ALTURA =================
    public int height(Node node) {
        if (node == null) return -1;
        return Math.max(height(node.left), height(node.right)) + 1;
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
        double[] tRB = new double[exec];
        double[] tTSP = new double[exec];

        Random rand = new Random();

        for (int i = 0; i < exec; i++) {
            RubroNegra tree = new RubroNegra();

            for (int j = 0; j < tamanho; j++)
                tree.insert(rand.nextInt(tamanho * 10));

            int chave = rand.nextInt(tamanho * 10);

            long ini = System.nanoTime();
            tree.search(tree.root, chave);
            tree.delete(chave);
            tree.height(tree.root);
            long fim = System.nanoTime();

            tRB[i] = fim - ini;

            double[][] m = gerarMatriz(Math.max(5, tamanho / 10));

            long ini2 = System.nanoTime();
            tsp(m);
            long fim2 = System.nanoTime();

            tTSP[i] = fim2 - ini2;
        }

        double mRB = media(tRB);
        double dRB = desvio(tRB, mRB);

        double mTSP = media(tTSP);
        double dTSP = desvio(tTSP, mTSP);

        System.out.println("========================");
        System.out.println("Tamanho: " + tamanho);

        System.out.println("Rubro-Negra:");
        System.out.println("Média: " + mRB);
        System.out.println("Desvio: " + dRB);
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


