package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista;

import java.util.Iterator;

import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.*;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {

	//atributos
	protected int cantElems;
	protected DNode<E> header,trailer;
	
	/**
	 *  Constructor de listaDoblementeEnlazada.
	 *  Inicializa:
	 *   cantidad de elementos en 0.
	 *   header y trailer se referencian el uno al otro.
	 */
	public ListaDoblementeEnlazada() {
		cantElems = 0;
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(header, null, null);
		header.setNext(trailer);
	}
	
	
	private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if(p == null) {
			throw new InvalidPositionException("Posición nula recibida.");
		}
		if(p == header) {
			throw new InvalidPositionException("El nodo header no es una posisción válida.");
		}
		if(p == trailer) {
			throw new InvalidPositionException("El nodo trailer no es una posisción válida.");
		}
		try {
			DNode<E> temp = (DNode<E>) p;//casteo para devolver la posicion casteada a DNode
			if((temp.getPrev() == null) || (temp.getNext() == null)) {
				throw new InvalidPositionException("La posición recibida no pertenece a una lista válida");
			}
			return temp;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("La posición es de un tipo erróneo para esta lista.");
		}
	}
	
	public int size() {
		return cantElems;
	}

	
	public boolean isEmpty() {
		return cantElems == 0;
	}

	
	public Position<E> first() throws EmptyListException {
		if(isEmpty()) {
			throw new EmptyListException("La lista esta vacía.");
		}
		return header.getNext() ;
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty()) {
			throw new EmptyListException("La lista esta vacía.");
		}
		return trailer.getPrev();
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> n = checkPosition(p);
		if(n.getNext() == trailer) {
			throw new BoundaryViolationException("Le esta pidiendo el siguiente a la última posición de la lista.");
		}
		return n.getNext();
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> n = checkPosition(p);
		DNode<E> prev = n.getPrev();
		if(prev == header) {
			throw new BoundaryViolationException("Le esta pidiendo el anterior a la primer posicion de la lista");
		}
		return prev;
	}

	public void addFirst(E element) {
		DNode<E> n = new DNode<E>(header, header.getNext(), element);
		n.getNext().setPrev(n);
		header.setNext(n);
		cantElems++;
	}

	public void addLast(E element) {
		if(isEmpty()) {
			addFirst(element);
		}
		else {
			DNode<E> n = new DNode<E>(trailer.getPrev(),trailer,element);
			trailer.getPrev().setNext(n);
			trailer.setPrev(n);
			cantElems++;
		}
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> n = checkPosition(p);
		DNode<E> newNode = new DNode<E>(n, n.getNext(), element);
		n.setNext(newNode);
		newNode.getNext().setPrev(newNode);
		cantElems++;
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> n = checkPosition(p);
		DNode<E> newNode = new DNode<E>(n.getPrev(),n,element);
		n.setPrev(newNode);
		newNode.getPrev().setNext(newNode);
		cantElems++;
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		DNode<E> n = checkPosition(p);
		E elem = n.element;
		n.getPrev().setNext(n.getNext());
		n.getNext().setPrev(n.getPrev());
		n.setNext(null);
		n.setPrev(null);
		cantElems--;
		return elem;
	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> n = checkPosition(p);
		E elem = n.element();
		n.setElement(element);
		return elem;
	}

	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if(!isEmpty()) {
				Position<E> pos = first();
				while( pos != last() ) {
					p.addLast(pos);
					pos = next(pos);
				}
				p.addLast(pos);
				}
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return p;
	}	
	
	//ejercicio 2
	public void agregar(E e1, E e2) {
		
		DNode<E> n1= new DNode<E>(e1);
		DNode<E> n2= new DNode<E>(e1);
		if(cantElems>=2) //al menos tenemos un primero y un ultimo
		{	//agregamos n1
			n1.setPrev(header.getNext());
			n1.setNext(header.getNext().getNext());
			header.getNext().setNext(n1);
			n1.getNext().setPrev(n1);
			cantElems++;
			//agergamos n2
			n2.setNext(trailer.getPrev());
			n2.setPrev(trailer.getPrev().getPrev());
			trailer.getPrev().getPrev().setNext(n2);
			n2.getNext().setPrev(n2);
			cantElems++;
						
		}
		if(cantElems==1) //si tenemos un solo elemento agregamos solo e1 (puede habermas soluciones)
		{
			n1.setPrev(header.getNext());
			n1.setNext(header.getNext().getNext());
			header.getNext().setNext(n1);
			n1.getNext().setPrev(n1);
			cantElems++;
		}
		if(cantElems==0) // si la lista est� vac�a agregamos e1 y e2
		{//enlazamos n1 y n2
			n1.setNext(n2);
			n2.setPrev(n1);
			header.setNext(n1);
			n1.setPrev(header);
			trailer.setPrev(n2);
			n2.setNext(trailer);
			cantElems=cantElems+2;
		}
	}
}
