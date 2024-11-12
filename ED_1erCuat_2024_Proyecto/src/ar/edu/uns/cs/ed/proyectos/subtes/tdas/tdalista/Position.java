package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista;

/**
 * Interfaz Position.
 * @author Ezequiel Ramírez Beltrán.
 * @author Dmytro Shkolyar.
 * @param <E> tipo de dato con el que trabaja la position.
 */
public interface Position<E>{
	/**
	 * Retorna el valor del elemento ubicado en la posición.
	 * @return el elemento ubicado en la posición.
	 */
	public E element();
}