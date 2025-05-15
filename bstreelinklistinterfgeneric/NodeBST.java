/* Se modifico la clase Node para que sus atributos sean protegidos*/

package bstreelinklistinterfgeneric;

public class NodeBST<E> {
    protected E data;
    protected NodeBST<E> right, left;

    public NodeBST(E data){
        this.data = data;
        this.right = null;
        this.left = null;
    }

    public E getData(){
        return data;
    }

    public NodeBST<E> getRight(){
        return right;
    }

    public NodeBST<E> getLeft(){
        return left;
    }
}
