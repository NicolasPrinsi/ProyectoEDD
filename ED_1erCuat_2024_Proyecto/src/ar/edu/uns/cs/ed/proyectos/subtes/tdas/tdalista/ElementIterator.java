package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.*;


public class ElementIterator<E> implements Iterator<E> {
	
	protected PositionList<E> list; // lista a iterar
	protected Position<E> cursor; // PosiciР“С–n del elemento corriente donde estР“РЋ iterator
	

	public ElementIterator(PositionList<E> l){
		list = l;
		if(list.isEmpty()) {
			cursor = null;
		} else {
			try {
				cursor = list.first();
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Indica si existe un siguiente elemento, al elemento actual.
	 * @return true, si existe un elemento siguiente al elemento actual en la lista iterable.
	 * @return false, si no existe elemento siguiente al elemento actual en la lista iterable.
	 */
	public boolean hasNext(){
		return cursor != null;
	}
	
	/**
	 * @return el siguiente elemento en la lista iterable.
	 * @throws NoSuchElementException si no hay elemento siguiente al elemento actual, en la lista iterable.
	 */
	public E next() throws NoSuchElementException {
		if(cursor == null){
			throw new NoSuchElementException("No existe un siguiente elemento.");
		}
		E toReturn = cursor.element();
		try {
			cursor = (cursor == list.last()) ? null : list.next(cursor);
		} catch (InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
}