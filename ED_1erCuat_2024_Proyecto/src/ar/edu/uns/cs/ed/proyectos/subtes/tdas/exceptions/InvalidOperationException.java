package ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions;
/**
 * Clase InvalidOperationException.
 * @author Ezequiel Ramírez Beltrán.
 * @author Dmytro Shkolyar. 
 */
public class InvalidOperationException extends Exception {

	/**
	 * Excepción ejecutada si la operación que se intentó realizar no fue exitosa.
	 * @param s: mensaje que le queremos mandar al constructor de Exception.
	 */
	public InvalidOperationException(String s) {
		super(s);
	}

}
