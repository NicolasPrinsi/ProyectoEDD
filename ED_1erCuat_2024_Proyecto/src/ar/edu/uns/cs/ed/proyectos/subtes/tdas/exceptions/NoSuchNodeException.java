package ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions;
/**
 * Clase NoSuchNodeException.
 * @author Ezequiel Ramírez Beltrán.
 * @author Dmytro Shkolyar. 
 */
public class NoSuchNodeException extends Exception{
	
	/**
	 * Excepción ejecutada si el nodo buscado no existe en el árbol.
	 * @param s: mensaje que le queremos mandar al constructor de Exception.
	 */
	public NoSuchNodeException (String s) {
		super(s);
	}
}
