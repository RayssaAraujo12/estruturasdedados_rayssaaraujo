

class BST {

    class Node {
        int key;
        Node left, right;

        Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    // Inserção
    Node insert(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insert(root.left, key);
        else if (key > root.key)
            root.right = insert(root.right, key);

        return root;
    }

    // Busca
    Node search(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        if (key < root.key)
            return search(root.left, key);

        return search(root.right, key);
    }

    // Menor valor da árvore
    Node minValueNode(Node root) {
        Node current = root;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Remoção
    Node deleteNode(Node root, int key) {
        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);
        else if (key > root.key)
            root.right = deleteNode(root.right, key);
        else {
            // Nó com 1 filho ou nenhum
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Nó com 2 filhos
            Node temp = minValueNode(root.right);
            root.key = temp.key;
            root.right = deleteNode(root.right, temp.key);
        }

        return root;
    }

    // Cálculo da altura
    int height(Node root) {
        if (root == null)
            return -1; // altura de árvore vazia

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Percurso em ordem
    void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.key + " ");
            inOrder(root.right);
        }
    }

    // Programa principal
    public static void main(String[] args) {
        BST tree = new BST();

        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 70);
        tree.root = tree.insert(tree.root, 60);
        tree.root = tree.insert(tree.root, 80);

        System.out.println("Árvore em ordem:");
        tree.inOrder(tree.root);

        System.out.println("\nAltura da árvore: " + tree.height(tree.root));

        int key = 40;
        if (tree.search(tree.root, key) != null)
            System.out.println("\nValor " + key + " encontrado!");
        else
            System.out.println("\nValor " + key + " não encontrado!");

        tree.root = tree.deleteNode(tree.root, 20);

        System.out.println("\nÁrvore após remoção:");
        tree.inOrder(tree.root);
    }
}
