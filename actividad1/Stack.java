package actividad1;

public interface Stack<E> {
    void push(E x);                     // -> apilar
    E pop() throws ExceptionIsEmpty;    // -> desapilar
    E top() throws ExceptionIsEmpty;    // -> ver tope
    boolean isEmpty();                  // -> verificar si está vacía
    void destroyStack();                // -> eliminar todos los elementos
}
