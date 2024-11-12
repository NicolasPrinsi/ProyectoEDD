package ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions;
/**
 * Clase InvalidKeyException.
 * @author Ezequiel Ramírez Beltrán.
 * @author Dmytro Shkolyar. 
 */
public class InvalidKeyException extends Exception{
	
	/**
	 * Excepción ejecutada al ingresar una clave nula en la cola con prioridad.
	 * @param s: mensaje que le queremos mandar al constructor de Exception.
	 */
	public InvalidKeyException(String s) {
		super(s);
	}
}
