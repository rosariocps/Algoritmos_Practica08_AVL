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
    
    private NodeAVL balanceToLeft(NodeAVL node){
        NodeAVL hijo = (NodeAVL) node.right;
        switch (hijo.bf) {
            case 1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node);
                break;

            case -1:
                NodeAVL nieto = (NodeAVL) hijo.left;
                switch (nieto.bf) {
                    case -1:
                        node.bf = 0;
                        hijo.bf = 1;
                        break;
                    case 0:
                        node.bf = 0;
                        hijo.bf = 0;
                        break;
                    case 1:
                        node.bf = -1;
                        hijo.bf = 0;
                        break;
                }
                nieto.bf = 0;
                node.right = rotateSR(hijo);
                node = rotateSL(node);
                break;
        }
        return node;
    }

    private NodeAVL balanceToRight(NodeAVL node){
        NodeAVL hijo = (NodeAVL) node.left;
        switch (hijo.bf) {
            case -1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node);
                break;

            case 1:
                NodeAVL nieto = (NodeAVL) hijo.right;
                switch (nieto.bf) {
                    case -1:
                        node.bf = 1;
                        hijo.bf = 0;
                        break;
                    case 0:
                        node.bf = 0;
                        hijo.bf = 0;
                        break;
                    case 1:
                        node.bf = 0;
                        hijo.bf = -1;
                        break;
                }
                nieto.bf = 0;
                node.left = rotateSL(hijo);
                node = rotateSR(node);
                break;
        }
        return node;
    }

    private NodeAVL rotateSL(NodeAVL node){
        NodeAVL p = (NodeAVL) node.right;
        node.right = p.left;
        p.left = node;
        return p; 
    }
    
    private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.left;
        node.left = p.right;
        p.right = node;
        return p;
    }

    // METODO PUBLICO QUE BORRA EL NODO "X" DE UN ARBOL
    @Override
    public void delete(E x) throws ExceptionIsEmpty {
        height = false;
        root = delete(x, (NodeAVL) root);
    }
    
    // METODO PRIVADO RECURSIVO QUE BUSCA Y ELIMINA EL DATO "X" Y APLICA REBALANCEO SI ES NECESARIO
    private NodeAVL delete(E x, NodeAVL node) throws ExceptionIsEmpty {
        if (node == null) {
            throw new ExceptionIsEmpty("El dato no se encuentra en el árbol.");
        }

        NodeAVL nodoCurrent = node;
        int comp = x.compareTo(node.data);

        if (comp < 0) {
            nodoCurrent.left = delete(x, (NodeAVL) node.left);
            if (height) {
                nodoCurrent = adjustLeft(nodoCurrent);
            }
        } 
        else if (comp > 0) {
            nodoCurrent.right = delete(x, (NodeAVL) node.right);
            if (height) {
                nodoCurrent = adjustRight(nodoCurrent);
            }
        } 
        else {
            if (node.left == null) {
                nodoCurrent = (NodeAVL) node.right;
                height = true;
            } 
            else if (node.right == null) {
                nodoCurrent = (NodeAVL) node.left;
                height = true;
            } 
            else {
                NodeAVL sucesor = buscarMin((NodeAVL) node.right);
                node.data = sucesor.data;
                node.right = delete(sucesor.data, (NodeAVL) node.right);
                if (height) {
                    nodoCurrent = adjustRight(node);
                }
            }
        }
        return nodoCurrent;
    }
    
    // METODO PRIVADO QUE BUSCA EL VALOR MINIMO EN UN SUBARBOL -> SUCESOR
    private NodeAVL buscarMin(NodeAVL node) {
        while (node.left != null) {
            node = (NodeAVL) node.left;
        }
        return node;
    }

    // METODO QUE AJUSTA EL FACTOR DE BALANCE AL ELIMINAR EN SUBARBOL IZQUIERDO
    private NodeAVL adjustLeft(NodeAVL node){
        switch(node.bf){
            case -1:
                node.bf = 0;
                break;
            case 0:
                node.bf = 1;
                height = false;
                break;
            case 1:
                NodeAVL right = (NodeAVL) node.right;
                if(right.bf <= 0){
                    node = rotateSR(node);
                    if(right.bf == 0){
                        node.bf = -1;
                        ((NodeAVL) node.left).bf = 1;
                        height = false;
                    }
                    else{
                        node.bf = 0;
                        ((NodeAVL) node.right).bf = 0;
                    }
                }
                else{
                    node = balanceToRight(node);
                }
                break;
        }
        return node;
    }

    // METODO QUE AJUSTA EL FACTOR DE BALANCE AL ELIMINAR EN SUBARBOL DERECHO
    private NodeAVL adjustRight(NodeAVL node){
        switch(node.bf){
            case 1:
                node.bf = 0;
                break;
            case 0:
                node.bf = -1;
                height = false;
                break;
            case -1:
                NodeAVL left = (NodeAVL) node.left;
                if(left.bf <= 0){
                    node = rotateSR(node);
                    if(left.bf == 0){
                        node.bf = 1;
                        ((NodeAVL) node.right).bf = -1;
                        height = false;
                    }
                    else{
                        node.bf = 0;
                        ((NodeAVL) node.right).bf = 0;
                    }
                }
                else{
                    node = balanceToRight(node);
                }
                break;
        }
        return node;
    }
    //Metodo para hallar la altura
    private int height(NodeAVL node) {
        if (node == null)
            return 0;
        int leftHeight = height((NodeAVL) node.left);
        int rightHeight = height((NodeAVL) node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Imprimir nodos de un nivel dado (recursivo)
    private void printLevel(NodeAVL node, int level) {
        if (node == null)
            return;
        if (level == 0) {
            System.out.print(node.data + " ");
        } else {
            printLevel((NodeAVL) node.left, level - 1);
            printLevel((NodeAVL) node.right, level - 1);
        }
    }

    // Método público que realiza el recorrido BFS recursivo
    public void bfsRecursive() {
        int h = height((NodeAVL) root);
        for (int i = 0; i < h; i++) {
            printLevel((NodeAVL) root, i);
            System.out.println(); // salto de línea para separar niveles
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