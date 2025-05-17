package bstreelinklistinterfgeneric;

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
}