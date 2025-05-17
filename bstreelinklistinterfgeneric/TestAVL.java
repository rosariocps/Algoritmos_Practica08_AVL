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

        System.out.println("\nEliminaciones con rotaciones\n");

        AVLTree<Integer> arbol2 = new AVLTree<>();

        try {
            arbol2.insert(50);
            arbol2.insert(30);
            arbol2.insert(70);
            arbol2.insert(20);
            arbol2.insert(40);
            arbol2.insert(60);
            arbol2.insert(80);
            arbol2.insert(10);
            arbol2.insert(25);

            System.out.println("Antes de eliminar 30:");
            System.out.println(arbol2.drawBST());

            arbol2.delete(30);
            System.out.println("Después de eliminar 30:");
            System.out.println(arbol2.drawBST());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
