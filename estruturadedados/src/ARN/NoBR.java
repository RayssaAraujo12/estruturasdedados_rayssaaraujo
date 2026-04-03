package ARN;

class NO {
    int key;
    int color;
    NO left, right, parent;

    public static final int RED = 0;
    public static final int BLACK = 1;

    public NO(int key) {
        this.key = key;
        this.color = RED; // novo nó sempre começa vermelho
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}