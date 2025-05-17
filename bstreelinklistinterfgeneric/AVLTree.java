package bstreelinklistinterfgeneric;

import exceptions.ExceptionIsEmpty;
import exceptions.ItemDuplicated;


public class AVLTree<E extends Comparable<E>> extends LinkedBST<E>{
    protected class NodeAVL extends NodeBST<E>{
        protected int bf; // balance factor = factor de equilibrio

        public NodeAVL(E data){
            super(data);
            this.bf = 0;

        }
        
        @Override
        public String toString(){
            return "Dato: " + data + ", Factor de equilibrio: " + bf;
        }
    }

    private boolean height;

    @Override
    public void insert(E x) throws ItemDuplicated{
        height = false;
        root = insert(x, (NodeAVL) root);
    }

    protected NodeAVL insert(E x, NodeAVL node) throws ItemDuplicated {
        NodeAVL nodoCurrent = node;

        if (node == null) {
            height = true;
            nodoCurrent = new NodeAVL(x);
        }
        else{
            int comp = x.compareTo(node.data);
            if (comp == 0)
                throw new ItemDuplicated(x + " ya se encuentra en el árbol...");
            if (comp < 0) { // insertar a la izquierda
                nodoCurrent.left = insert(x, (NodeAVL) node.left);
                if (height) {
                    switch (nodoCurrent.bf) {
                        case 1:
                            nodoCurrent.bf = 0;
                            height = false;
                            break;
                        case 0:
                            nodoCurrent.bf = -1;
                            height = true;
                            break;
                        case -1:
                            nodoCurrent = balanceToRight(nodoCurrent);
                            height = false;
                            break;
                    }
                }
            }
            else{
                nodoCurrent.right = insert(x, (NodeAVL) node.right);
                if (height) {
                    switch (nodoCurrent.bf) {
                        case -1:
                            nodoCurrent.bf = 0;
                            height = false;
                            break;
                        case 0:
                            nodoCurrent.bf = 1;
                            height = true;
                            break;
                        case 1:
                            nodoCurrent = balanceToLeft(nodoCurrent);
                            height = false;
                            break;
                    }
                }
            }
        }
        
        return nodoCurrent;
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
}