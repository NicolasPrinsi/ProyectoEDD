package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo;

/**
 * Clase Entrada. 
 * @author Faccio, Julio C.
 * @author Fernandez Tierno, Javier.
 */
	public class Entrada<K,V> implements Entry<K,V> {
	private K key;
	private V value;
	
	/**Crea una entrada con clave y con un valor
	 * 
	 * @param Key :clave
	 * @param Value :valor
	 */
	public Entrada(K Key,V Value){
		 key=Key;
		 value=Value;
	}
	
	/**Devuelve la clave de la entrada.
	 * @return clave
	 */
	public K getKey(){
		return key;
	}
	
	/**Devuelve el valor de la entrada.
	 * @return valor
	 */
	public V getValue(){
		return value;
	}
	
	/**Establece la clave.
	 * @param k :clave.
	 */
	public void setKey(K k){
		key=k;
	}
	
	/**
	 * Establece valor.
	 * 
	 * @param v :valor.
	 * 
	 */
	public void setValue(V v){
		value=v;
	}
	/**
	 * Redefine metodo toString() heredado de la clase Object. 
	 * @return Cadena con el formato (clave,valor).
	 */
	public String toString(){
		return new String("("+key.toString()+","+value.toString()+")");
	}
}
