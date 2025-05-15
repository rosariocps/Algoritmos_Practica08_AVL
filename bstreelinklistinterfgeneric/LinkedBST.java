package bstreelinklistinterfgeneric; // este es el paquete donde esta la clase

import actividad2.QueueLink;
import bstreeInterface.BinarySearchTree; // importo la interfaz que voy a implementar
import exceptions.ExceptionIsEmpty; // importo la excepcion si el arbol esta vacio
import exceptions.ItemDuplicated; // importo la excepcion si el dato ya existe
import exceptions.ItemNotFound; // importo la excepcion si el dato no se encuentra

public class LinkedBST<E extends Comparable<E>> implements BinarySearchTree<E> { // esta es la clase generica que implementa la interfaz del arbol

    protected NodeBST<E> root; // este es el nodo raiz del arbol

    public LinkedBST() { // constructor del arbol
        root = null; // al inicio el arbol esta vacio
    }

    @Override
    public void insert(E data) throws ItemDuplicated { // metodo para insertar
        root = insert(root, data); // llamo al metodo recursivo empezando desde la raiz
    }

    private NodeBST<E> insert(NodeBST<E> node, E data) throws ItemDuplicated { // metodo recursivo para insertar
        if (node == null) { // si el nodo apunta null, entonces ahi va el nuevo dato
            return new NodeBST<>(data); // creo el nuevo nodo
        }
        int comp = data.compareTo(node.data); // comparo el nuevo dato con el actual
        /*Negativo: data es menor que node.data
        Positivo: data es mayor que node.data
        Cero: son iguales (dato duplicado)*/
        if (comp < 0) { // si es menor, lo inserto a la izquierda
            node.left = insert(node.left, data);
        } else if (comp > 0) { // si es mayor, lo inserto a la derecha
            node.right = insert(node.right, data);
        } else { // si es igual, lanzo la excepcion porque ya existe
            throw new ItemDuplicated("El dato '" + data + "' ya existe en el arbol.");
        }
        return node; // devuelvo el nodo actualizado
    }

    @Override
    public E search(E data) throws ItemNotFound { // metodo para buscar un dato
        NodeBST<E> result = search(root, data); // empiezo desde la raiz
        if (result == null) { // si no lo encuentro, lanzo la excepcion
            throw new ItemNotFound("El dato '" + data + "' no fue encontrado.");
        }
        return result.data; // si lo encuentro, devuelvo el dato
    }

    private NodeBST<E> search(NodeBST<E> node, E data) { // metodo recursivo para buscar
        if (node == null) { // si llego a null, no esta el dato
            return null;
        }
        int comp = data.compareTo(node.data); // comparo el dato con el actual
        if (comp == 0) { // si son iguales, lo encontre
            return node;
        } else if (comp < 0) { // si es menor, sigo buscando a la izquierda
            return search(node.left, data);
        } else { // si es mayor, sigo buscando a la derecha
            return search(node.right, data);
        }
    }

    @Override
    public void delete(E data) throws ExceptionIsEmpty { // metodo para eliminar
        if (isEmpty()) { // si el arbol esta vacio, lanzo excepcion
            throw new ExceptionIsEmpty("el arbol esta vacio");
        }
        root = delete(root, data); // llamo al metodo recursivo empezando desde la raiz
    }
    /**
     * Elimina un nodo del árbol considerando los tres casos:
     * 1. Nodo sin hijos (hoja)
     * 2. Nodo con un solo hijo
     * 3. Nodo con dos hijos (se reemplaza con el mínimo del subárbol derecho)
     */
    private NodeBST<E> delete(NodeBST<E> node, E data) { // metodo recursivo para eliminar
        if (node == null) { // si no encuentro el nodo, retorno null
            return null;
        }
        int comp = data.compareTo(node.data); // comparo el dato con el nodo actual
        /*Negativo: data es menor que node.data
        Positivo: data es mayor que node.data
        Cero: son iguales (dato duplicado)*/
        if (comp < 0) { // si es menor, busco a la izquierda
            node.left = delete(node.left, data);
        } else if (comp > 0) { // si es mayor, busco a la derecha
            node.right = delete(node.right, data);
        } else { // si son iguales, lo encontre y lo elimino
            //Caso 1 o 2
            if (node.left == null) return node.right; // si no tiene hijo izquierdo, lo reemplazo por el derecho
            if (node.right == null) return node.left; // si no tiene hijo derecho, lo reemplazo por el izquierdo
            //Caso 3: tiene dos hijos
            NodeBST<E> min = min(node.right); // si tiene dos hijos, busco el menor del lado derecho
            node.data = min.data; // hacemos el "swap" copiamos ese valor pequeño al nodo actual
            node.right = delete(node.right, min.data); //eliminamos el nodo que tenia ese valor pequeño
        }
        return node; // devuelvo el nodo actualizado
    }

    private NodeBST<E> min(NodeBST<E> node) { // metodo para encontrar el menor de un subarbol
        while (node.left != null) { // voy bajando por la izquierda
            node = node.left;
        }
        return node; // cuando ya no hay mas izquierda, ese es el menor
    }

    @Override
    public boolean isEmpty() { // metodo para saber si el arbol esta vacio
        return root == null; // si la raiz es null, esta vacio
    }

    @Override
    public String toString() { // metodo para mostrar los datos del arbol
        StringBuilder sb = new StringBuilder(); // creo un objeto para construir el texto
        inorder(root, sb); // llamo al recorrido inorden desde la raiz
        return sb.toString().trim(); // devuelvo el resultado sin espacios al final
    }

    private void inorder(NodeBST<E> node, StringBuilder sb) { // metodo recursivo para recorrer en inorden
        if (node != null) { // si el nodo no es null
            inorder(node.left, sb); // primero voy a la izquierda
            sb.append(node.data).append(" "); // luego agrego el dato actual
            inorder(node.right, sb); // y luego voy a la derecha
        }
    }

    public String recorridoPreOrden() {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString().trim();
    }
    
    // metodo que recorre el arbol en pre-orden y guarda los datos en el StringBuilder
    // En pre-orden, primero se visita la raíz, luego el subárbol izquierdo y finalmente el subárbol derecho
    // En este caso, se utiliza un StringBuilder para construir la cadena de texto
    private void preOrder(NodeBST<E> node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.data).append(" ");   // visita la raiz primero
            preOrder(node.left, sb);            // luego recorre subarbol izquierdo
            preOrder(node.right, sb);           // luego recorre subarbol derecho
        }
    }

    public String recorridoPostOrden() {
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        return sb.toString().trim();
    } 

    // metodo que recorre el arbol en post-orden y guarda los datos en el StringBuilder
    private void postOrder(NodeBST<E> node, StringBuilder sb) {
        if (node != null) {
            postOrder(node.left, sb);   // primero recorre subárbol izquierdo
            postOrder(node.right, sb);  // luego recorre subárbol derecho
            sb.append(node.data).append(" "); // al final visita la raíz
        }
    }

    private E findMinNode(NodeBST<E> node) throws ItemNotFound {
        // Si el nodo es null, lanzamos excepción: no hay subárbol donde buscar
        if (node == null) {
            throw new ItemNotFound("Subárbol vacío, no se puede encontrar el mínimo");
        }
        // Creamos una referencia para recorrer desde el nodo dado
        NodeBST<E> current = node;
        // En un BST, el valor mínimo siempre está en el extremo más a la izquierda
        while (current.left != null) {
            current = current.left;
        }
        // Cuando current.left es null, current contiene el valor mínimo
        return current.data; // Retornamos su data
    }

    private E findMaxNode(NodeBST<E> node) throws ItemNotFound {
        // Si el nodo es null, lanzamos una excepción: no hay subárbol donde buscar
        if (node == null) {
            throw new ItemNotFound("Subárbol vacío, no se puede encontrar el máximo");
        }
        // Creamos una referencia para recorrer desde el nodo dado
        NodeBST<E> current = node;
        // En un BST, el valor maximo siempre está en el extremo más a la derecha
        while (current.right != null) {
            current = current.right;
        }
        // Cuando current.right es null, current contiene el valor maximo
        return current.data;// Retornamos su data
    }

    public E getMin() throws ItemNotFound {
        return findMinNode(root);
    }
    
    public E getMax() throws ItemNotFound {
        return findMaxNode(root);
    }

    //Método que elimina todos los nodos de un BST
    public void destroyNodes() throws ExceptionIsEmpty{
        if(isEmpty()){
            throw new ExceptionIsEmpty("No se puede eliminar todos los nodos porque el arbol ya esta vacío.");
        }
        root = null;
    }

    // METODO QUE CUENTA TODOS LOS NODOS DE UN ARBOL
    public int countAllNodes(){
        if(isEmpty()){ // Verifica si el arbol esta vacío, pues de estarlo, no tendría caso llamar al método recursivo
            return 0; // Devolvería cero y ahi queda
        }
        return countAllNodes(root); // Se llama al metodo recursivo y se inicializa con el primer nodo del árbol

    }

    private int countAllNodes(NodeBST<E> nodo){ // Método privado recursivo que cuenta todos los nodos
        if(nodo == null){ // Si el nodo es nulo significa que hemos llegado al final de una rama
            return 0;
        } 
        else{
            int contador = 1; // Si no es nulo contamos el nodo
            /* A contador le agregamos lo que retorna de aplicar este mismo método pero ahora el
            parametro es el hijo izquierdo. Y hace lo mismo con el derecho. */
            contador += countAllNodes(nodo.left);
            contador += countAllNodes(nodo.right);
            return contador; // Finalmente se retorna el valor que se ha acumulado
        }
    }

    // METODO QUE CUENTA TODOS LOS NODOS NO-HOJA DE UN ARBOL
    public int countNoHojas(){
        // Si el árbol está vacío, no hay nodos que contar
        if(isEmpty()){
            return 0;
        }
        // Llama al método recursivo privado, comenzando desde la raíz
        return countNoHojas(root);
    }

    // MÉTODO RECURSIVO PRIVADO QUE CUENTA LOS NODOS NO-HOJA A PARTIR DE UN NODO DADO
    private int countNoHojas(NodeBST<E> nodo){
        // Caso base: si el nodo es null, retornamos 0
        if(nodo == null){
            return 0;
        }

        // Si el nodo es hoja (no tiene hijos), no lo contamos
        if(nodo.left == null && nodo.right == null){
            return 0;
        }
        else{
            // Si tiene al menos un hijo, entonces es un nodo no-hoja, contamos 1
            int contador = 1;

            // Seguimos recorriendo recursivamente hacia ambos lados
            contador += countNoHojas(nodo.left);
            contador += countNoHojas(nodo.right);

            // Retornamos la suma total
            return contador;
        }
    }
    
    // METODO QUE CUENTA LOS NODOS HOJA DE UN ARBOL
    public int countHojas(){
        if(isEmpty()){ // Si el árbol está vacío retorna 0
            return 0;
        }
        // Llamamos al método recursivo privado, comenzando desde la raíz
        return countHojas(root);
    }

    // MÉTODO PRIVADO RECURSIVO QUE CUENTA LAS HOJAS A PARTIR DE UN NODO DADO
    private int countHojas(NodeBST<E> nodo){
        // Caso base: si el nodo es null, retornamos 0 porque no hay nada que contar
        if(nodo == null){
            return 0;
        }

        // Si el nodo actual no tiene hijos, entonces es una hoja: contamos 1
        if(nodo.left == null && nodo.right == null){
            return 1;
        }
        /* Si tiene al menos un hijo, seguimos recorriendo recursivamente sus ramas
           La suma de las hojas del subárbol izquierdo y derecho será el total */
        return countHojas(nodo.left) + countHojas(nodo.right);
    }

    // METODO QUE HALLA LA ALTURA DE UN SUBARBOL CON RAIZ DE DATA "x"
    public int height(E x) throws ItemNotFound {
        NodeBST<E> nodoRaiz = getNode(x); // Obtenemos el nodo con la data x con el metodo getNode()

        if (nodoRaiz == null) {
            return -1; // Si no existe el subárbol, devolvemos -1
        }

        //Utilizamos la clase QueueLink implementada en el laboratorio 6
        QueueLink<NodeBST<E>> cola = new QueueLink<>(); // Creamos una cola para recorrido por niveles
        cola.enqueue(nodoRaiz); // Insertamos el nodo raíz del subárbol
        int altura = -1; // Inicializamos la altura

        // Mientras haya nodos en la cola, seguimos procesando nivel por nivel
        while (!cola.isEmpty()) {
            int numElementosNivel = cola.numeroDeElementos(); // Obtenemos el número de nodos en el nivel actual
            altura++; // Aumentamos la altura por cada nivel recorrido

            // Recorremos todos los nodos del nivel actual
            for (int i = 0; i < numElementosNivel; i++) {
                try {
                    NodeBST<E> nodoCurrent = cola.dequeue(); // Extraemos el nodo actual

                    if (nodoCurrent.left != null) {
                        cola.enqueue(nodoCurrent.left); // Encolamos hijo izquierdo si existe
                    }
                    if (nodoCurrent.right != null) {
                        cola.enqueue(nodoCurrent.right); // Encolamos hijo derecho si existe
                    }
                // Capturamos la excepción por si algo fallara al sacar de la cola
                } catch (actividad1.ExceptionIsEmpty e) {
                    System.out.println("Error al intentar quitar de la cola: " + e.getMessage());
                }
                    
            }
        }
    return altura; // Devolvemos la altura final calculada
    }

    // MÉTODO PRIVADO QUE BUSCA Y RETORNA EL NODO QUE CONTIENE LA DATA 'data'
    private NodeBST<E> getNode(E data) throws ItemNotFound {
        // Empezamos desde la raíz del árbol
        NodeBST<E> nodo = root;

        // Recorremos el árbol iterativamente buscando el nodo con la data indicada
        while(nodo != null){
            int comp = data.compareTo(nodo.data);

            if(comp == 0){
                return nodo; // Si encontramos el nodo con la data exacta, lo retornamos
            }
            else if(comp > 0){
                nodo = nodo.right; // Si 'data' es mayor, vamos al subárbol derecho
            }
            else{
                nodo = nodo.left; // Si 'data' es menor, vamos al subárbol izquierdo
            }
        }
        // Si el nodo no fue encontrado, lanzamos la excepción correspondiente
        throw new ItemNotFound("El nodo con data " + data + " no existe.");
    }

    // MÉTODO QUE RETORNA LA CANTIDAD DE NODOS DE UN NIVEL EN ESPECÍFICO
    public int amplitude(int nivel) throws ItemNotFound {
        // Verificamos si el árbol está vacío
        if (isEmpty()) {
            return 0; // Si lo está, no hay nodos, entonces su amplitud es 0
        }

        // Calculamos la altura del árbol desde la raíz, usando el método height()
        int altura = height(root.data);
        

        // Si el nivel solicitado es menor que 0 o mayor que la altura del árbol, el nivel no existe, así que retornamos 0
        if(nivel < 0 || nivel > altura){
            return 0;
        }
        // Si el nivel es válido, llamamos al método recursivo que cuenta los nodos en ese nivel
        return contarNodosEnNivel(root,nivel);
    }

    // METODO QUE RETORNA LA AMPLITUD TOTAL DEL ARBOL
    public int amplitudTotal() throws ItemNotFound {
        if (isEmpty()){
            return 0;
        }

        int altura = height(root.data);
        int max = 0;

        for (int i = 0; i <= altura; i++) {
            int nodosEnNivel = contarNodosEnNivel(root, i);
            if (nodosEnNivel > max) {
                max = nodosEnNivel;
            }
        }

        return max;
    }

    // MÉTODO RECURSIVO PARA CONTAR CUANTOS NODOS HAY EN UN NIVEL EN ESPECÍFICO
    private int contarNodosEnNivel(NodeBST<E> nodo, int nivel) {
        // Caso base 1: si el nodo es null, ya no hay más que recorrer en esta rama
        if (nodo == null) {
            return 0;
        }
        // Caso base 2: si el nivel es 0, significa que el nodo actual está justo en el nivel que buscamos, la raiz
        if (nivel == 0) {
            return 1;
        } else {
            /* Caso recursivo: si aún no estamos en el nivel deseado,
            bajamos al siguiente nivel tanto por la izquierda como por la derecha.*/
            return contarNodosEnNivel(nodo.left, nivel - 1) +
                    contarNodosEnNivel(nodo.right, nivel - 1);
        }
    }
    
    // METODO QUE RETORNA EL ÁREA DE UN ARBOL
    public int areaBST() throws ItemNotFound {
        // Si el árbol está vacío, su área es 0
        if (isEmpty()) {
            return 0;
        }

        // Paso 1: Se calcula el número de hojas
        int nodosHoja = 0; // Inicializamos el contador de hojas en 0
        // Se utiliza la clas QueueLink implementada del laborario 6
        QueueLink<NodeBST<E>> cola = new QueueLink<>(); // Se crea una cola enlazada vacia 
        cola.enqueue(root); // Se agrega la raiz a la cola

        // Mientras haya nodos por recorrer en la cola
        while (!cola.isEmpty()) {
            try {
                // Quitamos el primer nodo de la cola y lo analizamos
                NodeBST<E> nodoCurrent = cola.dequeue();

                // Verificamos si el nodo actual es una hoja
                if (nodoCurrent.left == null && nodoCurrent.right == null) {
                    nodosHoja++; // Si es hoja, incrementamos el contador
                } else {
                    // Si tiene hijo izquierdo, lo agregamos a la cola para analizarlo después
                    if (nodoCurrent.left != null){
                        cola.enqueue(nodoCurrent.left);
                    }
                    // Si tiene hijo derecho, también lo agregamos a la cola
                    if (nodoCurrent.right != null){ 
                        cola.enqueue(nodoCurrent.right);
                    }
                }
            // Capturamos la excepción en caso de un error inesperado al quitar de la cola
            } catch (actividad1.ExceptionIsEmpty e) {
                System.out.println("Error al quitar de la cola: " + e.getMessage());
            }
        }

        // Paso 2: Se calcula la altura con el metodo que ya se implemento
        int altura = height(root.data);

        // Paso 3: Retorna la multiplicación
        return nodosHoja * altura;
    }

    public String drawBST() throws ItemNotFound {
        if (isEmpty()) {
            return "Árbol vacío.";
        }

        StringBuilder sb = new StringBuilder();
        QueueLink<NodeBST<E>> cola = new QueueLink<>();
        cola.enqueue(root);
        int altura = height(root.data);
        int nivel = 0;

        while (!cola.isEmpty()) {
            int numElementos = cola.numeroDeElementos();
            sb.append("Nivel ").append(nivel).append(":   ");

            // Calculamos espacio inicial proporcional al nivel
            int espacios = (int) Math.pow(2, altura - nivel + 1);
            sb.append(" ".repeat(espacios / 2));

            for (int i = 0; i < numElementos; i++) {
                try {
                    NodeBST<E> actual = cola.dequeue();
                    if (actual != null) {
                        sb.append(actual.data).append(" ".repeat(espacios));
                        cola.enqueue(actual.left);
                        cola.enqueue(actual.right);
                    } else {
                        // Nodo nulo: imprimimos espacio para mantener alineación
                        sb.append(" ".repeat(espacios));
                        cola.enqueue(null);
                        cola.enqueue(null);
                    }
                } catch (actividad1.ExceptionIsEmpty e) {
                    sb.append(" ?");
                }
            }

            sb.append("\n");
            nivel++;

            // Si todos los elementos en la cola son null, detenemos el recorrido
            boolean todosNulos = true;
            for (int i = 0; i < cola.numeroDeElementos(); i++) {
                try {
                    NodeBST<E> temp = cola.dequeue();
                    if (temp != null) todosNulos = false;
                    cola.enqueue(temp); // volverlo a insertar
                } catch (actividad1.ExceptionIsEmpty e) {
                    // nada
                }
            }
            if (todosNulos) break;
        }

        return sb.toString();
    }



    // METODO QUE DIBUJA UN ARBOL ROTADO 90° A LA IZQUIERDA
    public String drawBSTRotado() {
        StringBuilder sb = new StringBuilder(); // Creamos un StringBuilder para construir el texto del árbol
        drawSubtree(root, sb, 0); // Llamamos al método auxiliar desde la raíz con nivel 0
        return sb.toString(); // Convertimos el StringBuilder a String y lo retornamos
    }

    // MÉTODO RECURSIVO QUE DIBUJA EL SUBÁRBOL A PARTIR DE UN NODO DADO
    private void drawSubtree(NodeBST<E> nodo, StringBuilder sb, int nivel) {
        if (nodo == null){ // Cuando llegamos al final de una rama
            return;
        }

        drawSubtree(nodo.right, sb, nivel + 1); // Primero se dibuja el subárbol derecho
        
        /* Se agrega sangría dependiendo del nivel (más profundo, más espacio)
        Se agrega el conector visual del nodo
        Se agrega el valor del nodo
        Se agrega salto de línea */
        sb.append("      ".repeat(nivel)).append("|--- ").append(nodo.data).append("\n");
        
        drawSubtree(nodo.left, sb, nivel + 1); // Luego se dibuja el subárbol izquierdo
    }

    // METODO QUE IMPRIME LA REPRESENTACION ENTRE PARENTESIS CON SANGRIA DE UN ARBOL
    public String parenthesize(){
        StringBuilder sb = new StringBuilder(); // Se crea un StringBuilder para construir el texto final
        parenthesize(root, sb, 0); // Se llama al método recursivo empezando desde la raíz, nivel 0
        return sb.toString(); // Se convierte el StringBuilder a String y se retorna
    }

    // MÉTODO PRIVADO RECURSIVO QUE CONSTRUYE LA REPRESENTACIÓN ENTRE PARÉNTESIS
    private void parenthesize(NodeBST<E> nodo, StringBuilder sb, int nivel){
        if (nodo == null){
            return; // Si el nodo es nulo, no hay nada que procesar y se termina esta rama
        }
        // Para la sangría, se agregan espacios según el nivel y seguidamente se agrega el dato del nodo actual
        sb.append("   ".repeat(nivel)).append(nodo.data);

        // Si el nodo tiene al menos un hijo (izquierdo o derecho), abrimos paréntesis
        if (nodo.left != null || nodo.right != null) {
            sb.append(" (\n"); // Se abre paréntesis y se hace salto de línea

            // Si el nodo tiene hijo izquierdo, lo procesamos recursivamente
            if (nodo.left != null) {
                parenthesize(nodo.left, sb, nivel + 1); // Llamada recursiva para el hijo izquierdo
                sb.append("\n"); // Agregamos salto de línea después del hijo izquierdo
            }

            // Si el nodo tiene hijo derecho, lo procesamos recursivamente
            if (nodo.right != null) {
                parenthesize(nodo.right, sb, nivel + 1); // Llamada recursiva para el hijo derecho
                sb.append("\n");  // Agregamos salto de línea después del hijo derecho
            }

            sb.append("   ".repeat(nivel)).append(")"); // Cerramos el paréntesis al nivel actual, que tiene la misma sangría que el nodo
        }
    }
}
