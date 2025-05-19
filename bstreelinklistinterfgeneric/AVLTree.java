package bstreelinklistinterfgeneric;

import exceptions.ExceptionIsEmpty;
import exceptions.ItemDuplicated;

//clase generica
public class AVLTree<E extends Comparable<E>> extends LinkedBST<E>{
    //clase interna que extiende de NodeBST y añase un campo bf (factor de equilibrio)
    protected class NodeAVL extends NodeBST<E>{
        // factor de equilibrio -> bf = altura del sub dere - altura de sub izq
        protected int bf;
        // 0 = equilibrado
        // -1 = subarbol izquierdo más alto
        //  1 = subarbol derecho más alto
        // -2 = desequilibrio hacia el subarbol izquierdo (requiere rotacion)
        //  2 = desequilibrio hacia el subarbol derecho (requiere rotacion)         
        
        //constructor
        public NodeAVL(E data){
            super(data); //llama al contructor padre (NodeBST<E>) pasando data
            this.bf = 0; //incializamos el factor de balance en 0

        }
        //metodo para imprimir el nodo 
        @Override
        public String toString(){
            return "Dato: " + data + ", Factor de equilibrio: " + bf;
        }
    }
    private boolean height; //bandera para saber si la altura deñ subarbol ha cambiado
    //metodo publico para insertar un nodo al arbol
    @Override
    public void insert(E x) throws ItemDuplicated{ //lanza una excepcion personalizada en caso el dato este duplicado
        height = false; //inicializamos height en false xq aun no ha cambiado nada
        root = insert(x, (NodeAVL) root); //root almacenara el resultado del metodo recursivo insert , con el dato a insertar (x) y la raiz convertido a tipo NodeAVL
    }
    //metodo protegido (osea que se puede usar dentro de la clase y subclases y clases del mismo paquete)
    //recibe el dato x a insertar, y el nodo actual (raiz)
    protected NodeAVL insert(E x, NodeAVL node) throws ItemDuplicated {
        NodeAVL nodoCurrent = node; //creamos una variable nodoCurrent que apunta al mismo nodo recibido
        //caso base : si el nodo está vacio, llegamos a la posicion donde debe insertarse el nuevo valor
        if (node == null) {
            height = true; //ponemos en true xq la altura del arbol ha aumentado
            nodoCurrent = new NodeAVL(x); //creamos un nuevo nodo con el valor x recibido
        }
        else{ //si el nodo no es nulo 
            int comp = x.compareTo(node.data); //comparamos el valor x con el dato de nodo actual
            //compareTo nos devuelve: 0 si son iguales, < 0 si x es menor, > 0 si x es mayor
            if (comp == 0) //si son iguales emtonces el valor ya existe, entonces lanzamos la excepcion 
                throw new ItemDuplicated(x + " ya se encuentra en el árbol...");
            if (comp < 0) { // si x es menor
                nodoCurrent.left = insert(x, (NodeAVL) node.left); //insertamos recursivamente a la izquierda
                if (height) { //despues de insertar, si el subarbol izq crecio (osea height=true), hay que balancear
                    switch (nodoCurrent.bf) {//revisamos el balance del nodo actual 
                        case 1: //si antes tenia el subar der mas alto (bf = 1)
                            nodoCurrent.bf = 0; //ahora el subar izq crecio -> se equilibra (bf=0)
                            height = false; //ya no aumenta mas la altura asi que height=false
                            break;
                        case 0: //si el nodo estaba equilibrado (bf = 0)
                            nodoCurrent.bf = -1; //el subar izq crecio -> se desequilibra un poco hacia la izquierda (bf=-1)
                            height = true; //hubo cambio y aun puede seguir aumentando la altura asi que height=true
                            break;
                        case -1://si el nodo ya estaba inclinado a la izquierda (bf = -1)
                        //y como se inserto otra vez en el subar izq, entonces ahora esta desequilibrado (bf = -2)
                            nodoCurrent = balanceToRight(nodoCurrent); //llamamos al metodo balanceToRight() para una rotacion derecha y equilibrar
                            height = false; //despues de rotar el arbol ya no crece en altura, por eso height = false
                            break;
                    }
                }
            }
            else{ //si es que comp > 0 , osea el valor x es mayor que el valor del nodo actual
                nodoCurrent.right = insert(x, (NodeAVL) node.right); //llamamos al metodo recursivo insert para insertamos en el subar der
                if (height) { //despues de insertar, si el subarbol der crecio (osea height=true), hay que balancear
                    switch (nodoCurrent.bf) {//revisamos el balance del nodo actual 
                        case -1: //si antes el subar izq era mas alto (bf = -1)
                            nodoCurrent.bf = 0; //ahora el subar der crecio -> se equilibra (bf = 0)
                            height = false; //y no aumenta mas la altura asi que height=false
                            break;
                        case 0: //si el nodo estaba equilibrado (bf = 0)
                            nodoCurrent.bf = 1; //y el subarbol derecho crece -> el nodo se inclina hacia la derecha (bf = 1)
                            height = true; //hubo cambio y aun puede seguir aumentando la altura asi que height=true
                            break;
                        case 1: //si el nodo ya estaba inclinado a la derecha (bf = 1)
                        //y como el subar der crecio otra vez, entonces hay desequilibrio (bf = 2)
                            nodoCurrent = balanceToLeft(nodoCurrent); //llamamos al metodo balanceToLeft() para una rotacion izquierda y equilibrar
                            height = false; //despues de rotar el arbol ya no crece en altura, por eso height = false
                            break;
                    }
                }
            }
        }
        return nodoCurrent; //devuelve el nodo actual
    }
    //metodo privado que recibe el nodo desbalanceado (con bf = 2) y retorna el nodo ya balanceado
    private NodeAVL balanceToLeft(NodeAVL node){
        NodeAVL hijo = (NodeAVL) node.right; //hijo es el subar der del nodo
        switch (hijo.bf) { //evaluamos el bf del hijo (el subar der)
            case 1: //si el hijo tmb esta inclinado a la derecha (bf = 1)
            //solo hacemos una rotacion simple izquierda (rotateSL)
            //ambos nodos quedan balanceados (bf = 0)
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node);
                /*     node (bf = 2)
                            \
                            hijo (bf = 1)
                        despues de la rotacion simple izq:
                            hijo
                            /   \
                        node   ...          */
                break;
            case -1: // si el hijo (subarbol derecho) esta inclinado a la izquierda (bf = -1)
                /* ejemplo de estructura antes de balancear:
                        node (bf = 2)
                            \
                            hijo (bf = -1)
                            /
                        nieto (puede ser -1, 0 o 1)
                Este es un caso de desequilibrio en zig-zag, que se soluciona con una rotación doble:
                primero rotación simple derecha sobre el hijo, luego rotación simple izquierda sobre el node
                */
                NodeAVL nieto = (NodeAVL) hijo.left; // creamos el nieto, que es el hijo izquierdo del hijo derecho
                switch (nieto.bf) { // evaluamos el bf del nieto para actualizar correctamente los balances
                    case -1: // el nieto está inclinado hacia la izquierda (bf=-1)
                        node.bf = 0;    // el nodo principal queda balanceado
                        hijo.bf = 1;    // el hijo se inclina ligeramente a la derecha
                        break;

                    case 0:  // el nieto esta balanceado (bf=0)
                        node.bf = 0;    // tanto el nodo
                        hijo.bf = 0;    // como el hijo quedan balanceados
                        break;

                    case 1:  // el nieto esta inclinado hacia la derecha (bf=1)
                        node.bf = -1;   // el nodo principal se inclina ligeramente a la izquierda
                        hijo.bf = 0;    // el hijo queda balanceado
                        break;
                }
                nieto.bf = 0; // el nieto que subira al centro, siempre queda balanceado
                node.right = rotateSR(hijo); // primera rotacion: rotamos a la derecha el hijo (para subir al nieto)
                node = rotateSL(node);       // segunda rotacion: rotamos a la izquierda el nodo (para subir al nieto)
                break;
        }
        return node; // retornamos el nodo ya balanceado
    }

    private NodeAVL balanceToRight(NodeAVL node){
        NodeAVL hijo = (NodeAVL) node.left; //obtenemos el hijo izquierdo del nodo (subarl izq)
        switch (hijo.bf) {
            case -1:  //si el hijo esta inclinado a la izquierda (bf = -1)
                //caso de rotacion simple a la derecha (rotateSR)
                //despues de rotar, ambos quedan balanceados (bf = 0)
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node);
                /*
                    Ejemplo antes:
                        node (bf = -2)
                        /
                    hijo (bf = -1)
                    despues de rotar a la derecha:
                            hijo
                            \
                            node
                    Ambos quedan equilibrados
                */
                break;
            case 1:  //si el hijo esta inclinado a la derecha (bf = 1)
                //caso de rotacion doble: primero rotacion simple izquierda sobre el hijo, luego rotacion simple derecha sobre el nodo
                NodeAVL nieto = (NodeAVL) hijo.right; //obtenemos al nieto, hijo derecho del hijo izquierdo
                switch (nieto.bf) {// evaluamos el bf del nieto
                    case -1:  //nieto inclinado a la izquierda (bf = -1)
                        node.bf = 1; //nodo principal se inclina a la derecha
                        hijo.bf = 0; //hijo queda balanceado
                        break;

                    case 0: //nieto balanceado (bf = 0)
                        node.bf = 0; //nodo principal balanceado
                        hijo.bf = 0; //hijo balanceado
                        break;

                    case 1: //nieto inclinado a la derecha (bf = 1)
                        node.bf = 0;  //nodo principal balanceado
                        hijo.bf = -1; //hijo se inclina hacia la izquierda
                        break;
                }
                nieto.bf = 0; // el nieto queda siempre balanceado
                node.left = rotateSL(hijo); // primera rotacion: rotacion simple izquierda sobre el hijo (sube el nieto)
                node = rotateSR(node); // segunda rotacion: rotación simple derecha sobre el nodo (sube el nieto)
                /*
                    Ejemplo antes:
                            node (bf = -2)
                        /
                    hijo (bf = 1)
                        \
                        nieto (bf puede variar)
                    Despues de rotaciones:
                            nieto (bf = 0)
                            /      \
                        hijo       node
                */
                break;
        }
        return node; //retornamos el nodo ya balanceado
    }

    //rotacion simple a la izquierda (rotateSL) para balancear el subárbol derecho (bf > 1)
    private NodeAVL rotateSL(NodeAVL node){
        NodeAVL p = (NodeAVL) node.right;  // p es el hijo derecho del nodo (subarbol que subira)
        node.right = p.left;               // el hijo izquierdo de p se convierte en hijo derecho del nodo
        p.left = node;                     // el nodo pasa a ser hijo izquierdo de p
        return p;                          // p ahora es la nueva raiz del subarbol rotado
    }
    /*
    ejemplo antes de rotar (node desbalanceado a la derecha):
        node
           \
            p
           / 
    despues de la rotacion simple a la izquierda:
            p
           /
        node
    */

    //rotacion simple a la derecha (rotateSR) para balancear el subarbol izquierdo (bf < -1)
    private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.left;    // p es el hijo izquierdo del nodo (subarbol que subirá)
        node.left = p.right;                 // el hijo derecho de p se convierte en hijo izquierdo del nodo
        p.right = node;                     // el nodo pasa a ser hijo derecho de p
        return p;                           // p ahora es la nueva raiz del subarbol rotado
    }
    /*
    ejemplo antes de rotar (node desbalanceado a la izquierda):
            node
            /
           p
            \
    despues de la rotacion simple a la derecha:
        p
         \
          node
    */

    // METODO PUBLICO QUE BORRA EL NODO "X" DE UN ARBOL
    @Override
    public void delete(E x) throws ExceptionIsEmpty { //lanza una excepcion personalizada en caso el arbol este vacio
        height = false; //inicializamos height en false xq aun no ha cambiado nada
        root = delete(x, (NodeAVL) root); //root almacenara el resultado del metodo recursivo insert , con el dato a eliminar (x) y la raiz convertido a tipo NodeAVL
    }
    
    // METODO PRIVADO RECURSIVO QUE BUSCA Y ELIMINA EL DATO "X" Y APLICA REBALANCEO SI ES NECESARIO
    private NodeAVL delete(E x, NodeAVL node) throws ExceptionIsEmpty {
        if (node == null) { //si el nodo es nulo, significa que el dato no esta en el arbol
            throw new ExceptionIsEmpty("El dato no se encuentra en el árbol.");
        }
        NodeAVL nodoCurrent = node; //creamos una variable nodoCurrent que apunta al mismo nodo recibido
        int comp = x.compareTo(node.data); //comparamos el valor x con el dato de nodo actual
        //compareTo nos devuelve: 0 si son iguales, < 0 si x es menor, > 0 si x es mayor
        if (comp < 0) {//si x es menor que el dato del nodo actual 
            nodoCurrent.left = delete(x, (NodeAVL) node.left); //seguimos buscando recursivamente en el subar izq
            if (height) { //si despues de eliminar se cambio la altura (height=true)
                nodoCurrent = adjustLeft(nodoCurrent); //balanceamos hacia la izquierda
            }
        } 
        else if (comp > 0) {//si x es mayor que el dato del nodo actual
            nodoCurrent.right = delete(x, (NodeAVL) node.right); //seguimos buscando recursivamente en el subarl der
            if (height) { //si despues de eliminar se cambio la altura (height=true)
                nodoCurrent = adjustRight(nodoCurrent); //balanceamos hacia la derecha
            }
        } 
        else { //si x es igual al dato del nodo actual osea q lo encontramos
            if (node.left == null) { //caso 1: el nodo tiene solo hijo derecho o ninguno
                nodoCurrent = (NodeAVL) node.right; //reemplazamos el nodo por su hijo derecho
                height = true; //indicamos que la altura cambio
            } 
            else if (node.right == null) { //caso 2: el nodo tiene solo hijo izquierdo
                nodoCurrent = (NodeAVL) node.left; //reemplazamos el nodo por su hijo izquierdo
                height = true; //indicamos que la altura cambio
            } 
            else { //caso 3:el nodo tiene dos hijos
                NodeAVL sucesor = buscarMin((NodeAVL) node.right); //buscamos el sucesor (minimo del subarbol derecho)
                node.data = sucesor.data; //reemplazamos el dato del nodo con el del sucesor
                node.right = delete(sucesor.data, (NodeAVL) node.right); //eliminamos el sucesor en el subárbol derecho
                if (height) { //si la altura cambio tras eliminar el sucesor
                    nodoCurrent = adjustRight(node); //balanceamos a la derecha
                }
            }
        }
        return nodoCurrent; //retornamos el nodo (posiblemente modificado) para reconstruir el árbol
    }
    
    // METODO PRIVADO QUE BUSCA EL VALOR MINIMO EN UN SUBARBOL -> SUCESOR
    private NodeAVL buscarMin(NodeAVL node) {
        while (node.left != null) { //mientras exista un hijo izquierdo seguimos bajando
            node = (NodeAVL) node.left; //avanzamos al hijo izquierdo
        }
        return node; //cuando ya no hay mas hijos izquierdos ese nodo es el menor del subarbol
    }

    // METODO QUE AJUSTA EL FACTOR DE BALANCE AL ELIMINAR EN SUBARBOL IZQUIERDO
    private NodeAVL adjustLeft(NodeAVL node){
        switch(node.bf){//evaluamos el bf de node
            case -1: //si el nodo estaba inclinado a la izquierda 
                node.bf = 0; //ahora queda balanceado
                break;
            case 0: //si el nodo estaba balanceado
                node.bf = 1; //ahora se inclina a la derecha
                //aunque el balance del nodo cambio, el arbol no se desbalanceo
                //asi que no es necesario seguir ajustando hacia arriba
                height = false;
                break;
            case 1: //si el nodo ya estaba inclinado a la derecha, debemos verificar si hay desequilibrio
                NodeAVL right = (NodeAVL) node.right; //obtenemos el hijo derecho
                if(right.bf <= 0){ //si el hijo derecho esta balanceado o inclinado a la izquierda
                    node = rotateSR(node); //hacemos una rotacion simple derecha
                    if(right.bf == 0){ //si el hijo estaba balanceado antes de rotar
                        node.bf = -1; //el nuevo nodo raiz se inclina a la izquierda
                        ((NodeAVL) node.left).bf = 1; //el hijo izquierdo se inclina a la derecha
                        height = false; //aunque rotamos, el arbol no pierde mas altura, así que se detiene el ajuste
                    }
                    else{ //si el hijo estaba inclinado a la izquierda, despues de rotar
                        //todo queda balanceado
                        node.bf = 0; 
                        ((NodeAVL) node.right).bf = 0;
                    }
                }
                else{ //si el hijo derecho esta inclinado a la derecha
                    node = balanceToRight(node); //se necesita una rotación doble para balancear
                }
                break;
        }
        return node; //devolvemos el nuevo nodo raiz del subarbol
    }

    // METODO QUE AJUSTA EL FACTOR DE BALANCE AL ELIMINAR EN SUBARBOL DERECHO
    private NodeAVL adjustRight(NodeAVL node){
        switch(node.bf){ //evaluamos el factor de balance (bf) del nodo actual
            case 1: //si el nodo estaba inclinado a la derecha
                node.bf = 0; //ahora queda balanceado
                break;
            case 0: //si el nodo estaba balanceado
                node.bf = -1; //ahora se inclina levemente a la izquierda
                //aunque cambió el bf, el arbol no se desbalanceó realmente
                //así que ya no es necesario seguir ajustando hacia arriba
                height = false;
                break;
            case -1: //si el nodo ya estaba inclinado a la izquierda
                NodeAVL left = (NodeAVL) node.left; //obtenemos el hijo izquierdo
                if(left.bf <= 0){ //si el hijo izquierdo esta balanceado o inclinado a la izquierda
                    node = rotateSR(node); //hacemos una rotacion simple a la derecha
                    if(left.bf == 0){ //si el hijo estaba balanceado antes de rotar
                        node.bf = 1; //el nuevo nodo raiz se inclina un poco a la derecha
                        ((NodeAVL) node.right).bf = -1; //el hijo derecho se inclina a la izquierda
                        height = false; //aunque se roto, el arbol no pierde mas altura, así que detenemos el ajuste
                    }
                    else{ //si el hijo estaba inclinado a la izquierda
                        //despues de rotar, todo queda balanceado
                        node.bf = 0;
                        ((NodeAVL) node.right).bf = 0;
                    }
                }
                else{ //si el hijo izquierdo esta inclinado a la derecha
                    node = balanceToRight(node); //se necesita una rotacion doble para balancear
                }
                break;
        }
        return node; //devolvemos el nodo actualizado (ya sea con su bf ajustado o con rotaciones aplicadas)
    }
    
    //metodo para hallar la altura de un nodo en el arbol AVL
    private int height(NodeAVL node) {
        if (node == null) //si el nodo es nulo 
            return 0; //su altura es 0
        //calculamos la altura del subarbol izquierdo
        int leftHeight = height((NodeAVL) node.left);
        //calculamos la altura del subarbol derecho
        int rightHeight = height((NodeAVL) node.right);
        //la altura del nodo actual es la mayor entre izquierda y derecha, mas 1 (por el mismo nodo)
        return Math.max(leftHeight, rightHeight) + 1;
    }

    //imprimir todos los nodos que estan en un nivel especifico del arbol (usando recursion)
    private void printLevel(NodeAVL node, int level) {
        if (node == null) //si el nodo es nulo, no hay nada que imprimir, así que salimos
            return;
        if (level == 0) { //si estamos en el nivel deseado
            System.out.print(node.data + " "); //imprimimos el dato del nodo actual
        } else {
            //si no estamos en el nivel deseado, bajamos un nivel y llamamos recursivamente a los hijos
            printLevel((NodeAVL) node.left, level - 1);  //subarbol izquierdo, nivel - 1
            printLevel((NodeAVL) node.right, level - 1); //subarbol derecho, nivel - 1
        }
    }

    // metodo público que realiza el recorrido BFS (por niveles) de forma recursiva
    //BFS: busqueda en anchura o recorrido por niveles.
    public void bfsRecursive() {
        //primero, obtenemos la altura total del arbol
        int h = height((NodeAVL) root);
        //recorremos cada nivel del arbol desde 0 hasta altura - 1
        for (int i = 0; i < h; i++) {
            // Imprimimos todos los nodos que están en el nivel 'i'
            printLevel((NodeAVL) root, i);

            // Imprimimos un salto de línea para separar visualmente los niveles
            System.out.println();
        }
    }
    

    // Método público para iniciar el recorrido preorden
    public void preOrderTraversal() {
        System.out.println("Recorrido en preorden:");
        preOrderRecursive((NodeAVL) root);
        System.out.println(); // salto de línea
    }


    // Método privado recursivo
    private void preOrderRecursive(NodeAVL node) {
        if (node != null) {
            System.out.print(node.data + " "); // Visitar nodo
            preOrderRecursive((NodeAVL) node.left); // Subárbol izquierdo
            preOrderRecursive((NodeAVL) node.right); // Subárbol derecho
        }
    }

}