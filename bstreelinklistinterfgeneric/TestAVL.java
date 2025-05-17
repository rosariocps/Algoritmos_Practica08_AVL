package bstreelinklistinterfgeneric;

public class TestAVL {
    public static void main(String[] args) {
        AVLTree<Integer> arbol = new AVLTree<>();

        try {
            arbol.insert(30);
            arbol.insert(20);
            System.out.println(arbol.drawBST());
            
            arbol.insert(10); // Rotación simple a la derecha
            System.out.println(arbol.drawBST());
            
            arbol.insert(40);
            arbol.insert(50); // Rotación simple a la izquierda
            System.out.println(arbol.drawBST());

            arbol.insert(25); // Rotación doble a la derecha
            System.out.println(arbol.drawBST());

            arbol.insert(45); // Rotación doble a la izquierda
            System.out.println(arbol.drawBST());

            arbol.insert(5);  // Rotación simple a la derecha
            System.out.println(arbol.drawBST());

            arbol.insert(60);
            arbol.insert(70); // Rotación simple a la izquierda
            System.out.println(arbol.drawBST());

            arbol.insert(12); // Rotación doble a la derecha
            System.out.println(arbol.drawBST());

            arbol.insert(65); // Rotación doble a la izquierda
            System.out.println(arbol.drawBST());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
