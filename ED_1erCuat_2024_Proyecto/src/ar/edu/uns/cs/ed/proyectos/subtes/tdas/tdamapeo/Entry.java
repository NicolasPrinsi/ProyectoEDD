package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo;

/**
 * Interfaz Entry. 
 * @author Faccio, Julio C.
 * @author Fernandez Tierno, Javier.
 */
public interface Entry<K,V> {
	/**Devuelve la clave 
	 * @return Key
	 */
	public K getKey();
	/**Devuelve el valor
	 * @return value
	 */
	public V getValue();
}
