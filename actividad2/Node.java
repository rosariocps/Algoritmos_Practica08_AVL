package actividad2;

public class Node<E> {
    E data;           // -> dato almacenado
    Node<E> next;     // -> referencia al siguiente nodo

    public Node(E data) {
        this.data = data;
        this.next = null;
    }

    public E getData() {
        return data;  // -> retorna el valor almacenado
    }

    public void setData(E data) {
        this.data = data;  // -> actualiza el valor almacenado
    }

    public Node<E> getNext() {
        return next;  // -> obtiene el siguiente nodo
    }

    public void setNext(Node<E> next) {
        this.next = next;  // -> enlaza con el siguiente nodo
    }
}

