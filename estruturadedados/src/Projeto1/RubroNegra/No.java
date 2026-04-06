package Projeto1.RubroNegra;

public class No {

    public static final int RED = 0;
    public static final int BLACK = 1;

    int key;
    No left, right, parent;
    int color;

    public No(int key) {
        this.key = key;
        this.color = RED;
    }
}