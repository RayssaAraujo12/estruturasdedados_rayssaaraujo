package ARN;

class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;

    class Node {
        int key, color;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
            color = RED;
            left = right = parent = null;
        }
    }

    private Node root;

    // Busca
    public Node search(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        if (key < root.key)
            return search(root.left, key);

        return search(root.right, key);
    }

    // Rotação à esquerda
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    // Rotação à direita
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != null)
            x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null)
            root = x;
        else if (y == y.parent.right)
            y.parent.right = x;
        else
            y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    // Inserção
    public void insert(int key) {
        Node node = new Node(key);
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            if (node.key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        node.parent = y;

        if (y == null)
            root = node;
        else if (node.key < y.key)
            y.left = node;
        else
            y.right = node;

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

    // Transplante (auxiliar na remoção)
    private void transplant(Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;

        if (v != null)
            v.parent = u.parent;
    }

    // Mínimo
    private Node minimum(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Remoção
    public void delete(int key) {
        Node z = search(root, key);
        if (z == null)
            return;

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
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK)
            fixDelete(x);
    }

    private void fixDelete(Node x) {
        while (x != root && (x == null || x.color == BLACK)) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (w != null && w.color == RED) {
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
                        if (w.left != null)
                            w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.right != null)
                        w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (w != null && w.color == RED) {
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
                        if (w.right != null)
                            w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.left != null)
                        w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        if (x != null)
            x.color = BLACK;
    }

    // Altura
    public int height(Node node) {
        if (node == null)
            return -1;

        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // Em ordem
    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    // Main
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);

        System.out.println("Árvore em ordem:");
        tree.inOrder(tree.root);

        System.out.println("\nAltura: " + tree.height(tree.root));

        int key = 15;
        if (tree.search(tree.root, key) != null)
            System.out.println("\nValor encontrado!");
        else
            System.out.println("\nValor não encontrado!");

        tree.delete(20);

        System.out.println("\nApós remoção:");
        tree.inOrder(tree.root);
    }
}