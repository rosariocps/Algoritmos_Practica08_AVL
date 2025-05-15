package actividad1;

public class Test {
    public static void main(String[] args) {
        // Creación de objetos de tipo Stack usando StackArray

        // Prueba con Stack de tipo Integer
        Stack<Integer> pilaInt = new StackArray<>(5);

        // Operaciones sobre pila de Integer
        pilaInt.push(10);
        pilaInt.push(20);
        pilaInt.push(30);
        System.out.println("Pila de Integer: " + pilaInt);  // Debería mostrar [10, 20, 30]

        try {
            System.out.println("Tope de Integer: " + pilaInt.top());  // Debería mostrar 30
            pilaInt.pop();  // Elimina el 30
            System.out.println("Pila de Integer después de pop(): " + pilaInt);  // Debería mostrar [10, 20]
        } catch (ExceptionIsEmpty e) {
            System.out.println(e.getMessage());  // Manejo de excepción si la pila está vacía
        }

        // Test de la operación destroyStack
        pilaInt.destroyStack();
        System.out.println("Pila de Integer después de destroyStack(): " + pilaInt);  // Pila vacía

        // Prueba con Stack de tipo String
        Stack<String> pilaString = new StackArray<>(3);
       
        // Operaciones sobre pila de String
        pilaString.push("A");
        pilaString.push("B");
        pilaString.push("C");
        System.out.println("Pila de String: " + pilaString);  // Debería mostrar [A, B, C]


        try {
            System.out.println("Tope de String: " + pilaString.top());  // Debería mostrar C
            pilaString.pop();  // Elimina el C
            System.out.println("Pila de String después de pop(): " + pilaString);  // Debería mostrar [A, B]
        } catch (ExceptionIsEmpty e) {
            System.out.println(e.getMessage());
        }


        // Test de la operación destroyStack
        pilaString.destroyStack();
        System.out.println("Pila de String después de destroyStack(): " + pilaString);  // Pila vacía


        // Prueba con Stack de tipo Double
        Stack<Double> pilaDouble = new StackArray<>(2);

        // Operaciones sobre pila de Double
        pilaDouble.push(3.14);
        pilaDouble.push(1.618);
        System.out.println("Pila de Double: " + pilaDouble);  // Debería mostrar [3.14, 1.618]


        try {
            System.out.println("Tope de Double: " + pilaDouble.top());  // Debería mostrar 1.618
            pilaDouble.pop();  // Elimina el 1.618
            System.out.println("Pila de Double después de pop(): " + pilaDouble);  // Debería mostrar [3.14]
        } catch (ExceptionIsEmpty e) {
            System.out.println(e.getMessage());
        }

        // Test de pila llena
        pilaDouble.push(2.718);
        pilaDouble.push(1.414);  // Este no se podrá apilar, ya que la pila tiene espacio para solo 2 elementos
        System.out.println("Pila de Double después de intentar apilar más de lo permitido: " + pilaDouble);
    }
}
