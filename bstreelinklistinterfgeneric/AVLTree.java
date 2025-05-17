package bstreelinklistinterfgeneric;

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

}