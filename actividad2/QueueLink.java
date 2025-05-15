package actividad2;
import actividad1.ExceptionIsEmpty;

public class QueueLink<E> implements Queue<E> { //Cola implementada con una lista Enlazada
    
    private Node<E> first;  // -> inicio de la cola
    private Node<E> last;   // -> final de la cola
    
    //constructor que inicia first y last apuntando a null xq esta vacia
    public QueueLink() {
        first = last = null;  // -> cola vacía
    }
    
    //verifica si la cola esta vacia
    @Override
    public boolean isEmpty() {
        //si first apunta a null
        return first == null;  // -> true si no hay elementos
    }
    
    //insertar un elemento al FINAL de una cola
    @Override
    public void enqueue(E x) {
        Node<E> aux = new Node<E>(x);  // -> Se crea un nuevo nodo llamado aux que contiene el dato x
        //verifica si la cola está vacía
        if (this.isEmpty()) { //this representa la instancia actual de la clase donde estás trabajando
            //si esta vacia:
            first = last = aux;  // -> El nuevo nodo aux es ahora el primero y el último nodo de la cola
        } else { //si no esta vacia:
            this.last.setNext(aux);  // -> se enlaza el nuevo nodo al final de la cola -  last → aux
            this.last = aux;         // -> se actualiza el puntero last para que ahora apunte al nuevo nodo - last = aux
        }
    }
    
    //eliminar y devolver el primer elemento de la cola
    @Override
    public E dequeue() throws ExceptionIsEmpty {
        //verifica si esta vacia
        if (this.isEmpty()) { //this representa la instancia actual de la clase donde estás trabajando
            //si esta vacia:
            throw new ExceptionIsEmpty("Cola vacía");  // -> no se puede eliminar y lanzamos excepcion
        }
        //se guarda el dato del primer nodo (el que será eliminado) en la variable elemento
        E elemento = first.getData();
        //se actualiza el puntero first para que ahora apunte al siguiente nodo.
        first = first.getNext();

        //Si despues de eliminar el nodo ya no queda ningun nodo en la cola
        if (first == null) {
            //entonces se actualiza last = null para mantener la consistencia de la cola vacía
            last = null;
        }

        return elemento;  //se devuelve el dato que estaba en el primer nodo (el que se elimino)
    }
    
    //para ver que elemento esta al inicio de la cola sin eliminarlo
    @Override
    public E front() throws ExceptionIsEmpty {
        //verifica si esta vacia
        if (this.isEmpty()) { //this representa la instancia actual de la clase donde se esta trabajando
            throw new ExceptionIsEmpty("Cola vacía");  // ->  lanzamos excepcion
        }
        //Si la cola no está vacía, retorna el dato almacenado en el primer nodo - el que esta al frente de la cola)
        return first.getData(); 
    }
    
    //para ver el ultimo elemento insertado en la cola sin eliminarlo
    @Override
    public E back() throws ExceptionIsEmpty {
        //verifica si esta vacia
        if (this.isEmpty()) { //this representa la instancia actual de la clase donde se esta trabajando
            throw new ExceptionIsEmpty("Cola vacía");  // ->  lanzamos excepcion
        }
        // si no esta vacia devuelve el contenido del ultimo nodo - el final de la cola
        return last.getData();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cola vacía";               // -> si no hay elementos
        }
    
        String resultado = "Cola: [";          // -> inicio del texto
    
        Node<E> actual = first;                // -> comenzamos desde el primer nodo
    
        while (actual != null) {
            resultado += actual.getData();     // -> agrega el dato del nodo actual
            if (actual.getNext() != null) {
                resultado += ", ";             // -> separador si hay más elementos
            }
            actual = actual.getNext();         // -> avanza al siguiente nodo
        }
    
        resultado += "]";                      // -> cierra la cadena con corchete
        return resultado;                      // -> retorna la cadena final
    }
    
    //jani agrego este metodo
    //metodo para enocntrar la posicion de un elemento dentro de una cola
    public int indexOf(E elemento) {
        Node<E> actual = first;  // apuntamos al inicio de la cola
        int index = 0;//comenzamos desde la posicion 0
        //mientras no lleguemos al final de la cola
        while (actual != null) {
            //si el dato del nodo actual es igual al elemento que buscamos
            if (actual.getData().equals(elemento)) {
                return index;  //retornamos su posición
            }
            //sino pasamos al siguiente nodo
            actual = actual.getNext();
            //incrementamos la posicion
            index++;
        }
        return -1; // Si no encontramos el elemento, devolvemos -1
    }
    
    // METODO AGREGADO QUE CALCULA LA CANTIDAD DE ELEMENTO DE UNA COLA
    @Override
    public int numeroDeElementos(){
        int contador = 0;
        Node<E> nodoCurrent = first;
        while(nodoCurrent != null){
            contador++;
            nodoCurrent = nodoCurrent.getNext();
        }
        return contador;
    }

    
}
