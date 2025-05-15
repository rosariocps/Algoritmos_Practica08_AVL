package actividad1;

public class StackArray<E> implements Stack<E> {
    private E[] array; // Arreglo genérico que usaremos para almacenar los elementos de la pila
    private int tope; // Representa la posición del último elemento agregado en la pila

    public StackArray(int n) {
        this.array = (E[]) new Object[n];
        this.tope = -1;
    }
    //agregar un elemento a la pila
    @Override
    public void push(E x) {
        //aso en el que ya la pila esta llena
        if (tope < array.length - 1) { // -> si hay espacio en la pila
            array[++tope] = x;  // -> 1ro incrementa tope y luego asigna el valor al indice incrementado
        } else {
            System.out.println("Pila llena");
        }
    }
    //eliminar y devolver el ULTIMO elemento agregado a la pila
    @Override    
    public E pop() throws ExceptionIsEmpty {
        if (isEmpty()) {  // -> si la pila está vacía
            throw new ExceptionIsEmpty("Array vacío");
        }
        return array[tope--];  // -> 1ro devuelve el elemento y luego decrementa
    }
    //devuelve el elemento en la cima (tope) de la pila sin eliminarlo
    @Override    
    public E top() throws ExceptionIsEmpty {
        if (isEmpty()) {  // -> si la pila está vacía
            throw new ExceptionIsEmpty("Array vacío");
        }
        return array[tope];    // -> devuelve el elemento del tope sin eliminarlo
    }
    //verifica si la pila está vacía
    @Override    
    public boolean isEmpty() {
        return tope == -1;  // -> retorna true si no hay elementos
    }
    //vacía la pila
    @Override    
    public void destroyStack() {
        tope = -1;  // -> reinicia la pila dejándola vacía (tope=-1 -> vacía)
    }
    //si la pila esta llena
    public boolean isFull() {
        return tope == array.length - 1; // -> retorna true si ya no hay espacio para más elementos
    }
    //representar visualmente el contenido de la pila en forma de una cadena
    @Override    
    public String toString() {
        if (isEmpty()) {
            return "Pila vacía";              // -> si no hay elementos
        }
        //comenzamos a construir el texto
        String resultado = "Pila: [";
        //Recorremos desde la base hasta el tope
        for (int i = 0; i <= tope; i++) {
            resultado += array[i];            // ->  Agregamos el elemento actual
            if (i < tope) {
                resultado += ", ";            // -> Agregamos coma si no es el último
            }
        }

        resultado += "]";  // -> cierra la cadena con corchete
        return resultado;  // -> retorna la cadena final
    }
    
}
