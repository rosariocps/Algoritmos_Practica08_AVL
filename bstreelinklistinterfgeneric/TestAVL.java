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
        // Ejercicio 1
        // **************** COMPARACION BST VS AVL ****************
        try {
            System.out.println("\n\n**** COMPARACIÓN ENTRE BST Y AVL ****");

            LinkedBST<Integer> bst = new LinkedBST<>();
            AVLTree<Integer> avl = new AVLTree<>();

            // Caso 1: Insertar en orden ascendente (degenera BST)
            bst.insert(1);
            bst.insert(2);
            bst.insert(3);
            bst.insert(4);
            bst.insert(5);

            avl.insert(1);
            avl.insert(2);
            avl.insert(3);
            avl.insert(4);
            avl.insert(5);

            System.out.println("Caso 1: Inserción en orden ascendente");
            System.out.println("Altura BST (subárbol raíz 1): " + bst.height(1));
            System.out.println("Altura AVL (subárbol raíz 1): " + avl.height(1));
            System.out.println("Buscar 3 en BST: " + bst.search(3));
            System.out.println("Buscar 3 en AVL: " + avl.search(3));

            System.out.println("\nDibujo BST Caso 1:");
            System.out.println(bst.drawBST());

            System.out.println("Dibujo AVL Caso 1:");
            System.out.println(avl.drawBST());

            // Caso 2: Insertar en orden aleatorio
            LinkedBST<Integer> bst2 = new LinkedBST<>();
            AVLTree<Integer> avl2 = new AVLTree<>();

            int[] datosCaso2 = {4, 1, 7, 3, 5, 2, 6};
            for (int dato : datosCaso2) {
                bst2.insert(dato);
                avl2.insert(dato);
            }

            System.out.println("\nCaso 2: Inserción en orden aleatorio");
            
            // Se compara la altura del subárbol con raíz en 3 para evidenciar que el AVL mantiene una estructura más balanceada.
            // Aunque ambos árboles insertaron los mismos datos, el AVL realiza rotaciones para reducir su altura,
            // lo que mejora la eficiencia en operaciones como búsqueda, inserción y eliminación.
            System.out.println("Altura BST (subárbol raíz 3): " + bst2.height(3));
            System.out.println("Altura AVL (subárbol raíz 3): " + avl2.height(3));

            
            //La busqueda en un AVL es mas eficiente porque es mas equilibrado: se requieren menos pasos para encontrar un nodo.
            System.out.println("Buscar 5 en BST: " + bst2.search(5));
            System.out.println("Buscar 5 en AVL: " + avl2.search(5));

            System.out.println("\nDibujo BST Caso 2:");
            System.out.println(bst2.drawBST());

            System.out.println("Dibujo AVL Caso 2:");
            System.out.println(avl2.drawBST());

        } catch (Exception e) {
            System.out.println("Error en comparación BST vs AVL: " + e.getMessage());
        }

        // Ejercicio 2
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

            System.out.println("Antes de eliminar 50:");
            System.out.println(arbol2.drawBST());

            arbol2.delete(50);
            System.out.println("Después de eliminar 50:");
            System.out.println(arbol2.drawBST());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ejercicio 3 y 4
        // AMPLITUD
        System.out.println("\nAMPLITUD\n");
        AVLTree<Integer> arbol3 = new AVLTree<>();
        try {
            int[] datosarbol3 = {50,20,10,30,90,40};
            for (int dato : datosarbol3) {
                arbol3.insert(dato);
            }
            System.out.println("\nDibujo del arbol:");
            System.out.println(arbol3.drawBST());
            arbol3.bfsRecursive();
            System.out.println();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // RECORRIDO EN PREORDEN
        System.out.println("\nRECORRIDO EN PREORDEN");
        try {
            // Árbol 1
            AVLTree<Integer> arbolPreOrden1 = new AVLTree<>();
            int[] datospreOrden1 = {40, 20, 10, 30, 60, 50, 70};
            for (int datopre1 : datospreOrden1) {
                arbolPreOrden1.insert(datopre1);
            }
            System.out.println("Árbol 1:");
            System.out.println(arbolPreOrden1.drawBST());
            System.out.print("Preorden: ");
            System.out.println(arbolPreOrden1.recorridoPreOrden());

            // Árbol 2
            AVLTree<Integer> arbolPreOrden2 = new AVLTree<>();
            int[] datospreOrden2 = {30, 10, 5, 40, 35, 50};
            for (int datopre2 : datospreOrden2) {
                arbolPreOrden2.insert(datopre2);
            }
            System.out.println("\nÁrbol 2:");
            System.out.println(arbolPreOrden2.drawBST());
            System.out.print("Preorden: ");
            System.out.println(arbolPreOrden2.recorridoPreOrden());

            // Árbol 3
            AVLTree<Integer> arbolPreOrden3 = new AVLTree<>();
            int[] datospreOrden3 = {15, 5, 3, 7, 20, 25};
            for (int datopre3 : datospreOrden3) {
                arbolPreOrden3.insert(datopre3);
            }
            System.out.println("\nÁrbol 3:");
            System.out.println(arbolPreOrden3.drawBST());
            System.out.print("Preorden: ");
            System.out.println(arbolPreOrden3.recorridoPreOrden());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ejercicio 5
        //Insertar y Eliminar Nodos en un Árbol AVL con Casos de Rotación
        AVLTree<Integer> arbolejer6 = new AVLTree<>();
        System.out.println("\nEjercicio 6");
        try {
            System.out.println("Insertando 30");
            arbolejer6.insert(30);
            arbolejer6.bfsRecursive();

            System.out.println("Insertando 20");
            arbolejer6.insert(20);
            arbolejer6.bfsRecursive();

            System.out.println("Insertando 10 (provoca rotación simple a la derecha)");
            arbolejer6.insert(10);
            arbolejer6.bfsRecursive();

            System.out.println("Insertando 25");
            arbolejer6.insert(25);
            arbolejer6.bfsRecursive();

            System.out.println("Insertando 22 (provoca rotación doble izquierda-derecha)");
            arbolejer6.insert(22);
            arbolejer6.bfsRecursive();

            System.out.println("Eliminando 30 (posible reequilibrio)");
            arbolejer6.delete(30);
            arbolejer6.bfsRecursive();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
