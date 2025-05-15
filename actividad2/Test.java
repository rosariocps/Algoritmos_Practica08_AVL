package actividad2;
import actividad1.ExceptionIsEmpty;

public class Test {
    public static void main(String[] args) {
        // -> Prueba con Integer
        Queue<Integer> colaEnteros = new QueueLink<>();

        try {
            System.out.println("¿Cola vacía?: " + colaEnteros.isEmpty()); // -> true

            colaEnteros.enqueue(10);
            colaEnteros.enqueue(20);
            colaEnteros.enqueue(30);
            System.out.println(colaEnteros);  // -> Cola: [10, 20, 30]

            System.out.println("Cantidad de elementos de la colaEnteros: " + colaEnteros.numeroDeElementos() );

            System.out.println("Frente: " + colaEnteros.front()); // -> 10
            System.out.println("Último: " + colaEnteros.back());  // -> 30

            System.out.println("Eliminando: " + colaEnteros.dequeue()); // -> 10
            System.out.println(colaEnteros);  // -> Cola: [20, 30]

        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }

        // -> Prueba con String
        Queue<String> colaNombres = new QueueLink<>();

        try {
            colaNombres.enqueue("Ana");
            colaNombres.enqueue("Luis");
            colaNombres.enqueue("Carlos");
            System.out.println(colaNombres); // -> Cola: [Ana, Luis, Carlos]

            System.out.println("Frente: " + colaNombres.front()); // -> Ana
            System.out.println("Último: " + colaNombres.back());  // -> Carlos

            colaNombres.dequeue();  // -> elimina "Ana"
            System.out.println(colaNombres); // -> Cola: [Luis, Carlos]



        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }

        // -> Prueba con cola vacía
        Queue<Double> colaVacia = new QueueLink<>();
        try {
            colaVacia.dequeue(); // -> lanza excepción
        } catch (ExceptionIsEmpty e) {
            System.out.println("Excepción esperada: " + e.getMessage());
        }
    }
}
