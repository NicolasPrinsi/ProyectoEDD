package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista;


public class DNode<E> implements Position<E> {

	//atributos
	protected E element;
	protected DNode<E> next,prev;
	
	/**Constructor DNode
	 * Inicializa:
	 *  su nodo predecesor con pre.
	 *  su nodo sucesor con nex.
	 *  su elemento con elem.
	 * @param pre:nodo anterior
	 * @param nex:nodo siguiente
	 * @param elem: elemento que almacenara
	 */
	public DNode(DNode<E> pre, DNode<E> nex, E elem) {
		element = elem;
		next = nex;
		prev = pre;
	}
	
	public DNode(E elem) {
		element = elem;
		next = null;
		prev = null;
	}
	
	public E element() {
		return element;
	}
	
	/**
	 * @return el nodo siguiente.
	 */
	public DNode<E> getNext(){
		return next;
	}
	
	/**
	 * @return el nodo anterior.
	 */
	public DNode<E> getPrev(){
		return prev;
	}
	
	//metodos de actualizacion
	/**
	 * Le asigna al nodo actual, un siguiente nodo.
	 * @param n nodo siguiente del nodo que recibe el mensaje.
	 */
	public void setNext(DNode<E> n) {
		next = n;
	}
	
	/**Le asigna al nodo actual, un nodo anterior.
	 * @param n:Nodo anterior a asignar.
	 */
	public void setPrev(DNode<E> n) {
		prev = n;
	}
	
	/**
	 * Asigna un elemento al contenido del nodo actual.
	 * @param elem elemento que contendrá el nodo actual.
	 */
	public void setElement(E elem) {
		element = elem;
	}
	

}