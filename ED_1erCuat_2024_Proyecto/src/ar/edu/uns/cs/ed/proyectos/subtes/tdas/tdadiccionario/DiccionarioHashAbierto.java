package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.*;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.Position;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.ListaDoblementeEnlazada;

import java.util.Iterator;


/**
 * Clase DiccionarioConHashAbierto
 * @author Ana Paula Negrin, Camila Rojas.
 */
public class DiccionarioHashAbierto<K,V> implements Dictionary<K,V> {
	
	protected PositionList<Entrada<K,V>>[] arregloBucket;
	protected int n; //cantidad de entradas
	protected int N; //tamaño del arreglo
	protected static final float factor =0.5F;

	/**
	 * Crea una instancia de DiccionarioConHashAbierto
	 */
	@SuppressWarnings("unchecked")
	public DiccionarioHashAbierto() {
		N=11;
		arregloBucket= (PositionList<Entrada<K,V>> []) new ListaDoblementeEnlazada[N];
		for(int i=0; i<N; i++) {
			arregloBucket[i]=new ListaDoblementeEnlazada<Entrada<K,V>>();
		}
		n=0;
	}
	
	/**
	 * Consulta el número de entradas del diccionario.
	 * @return Número de entradas del diccionario.
	 */
	public int size() {
		return n;
	}

	/**
	 * Consulta si el diccionario está vacío.
	 * @return Verdadero si el diccionario está vacío, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return n==0;
	}
	
	/**
	 * Chequea si la clave pasada por parámetro es válida.
	 * @param key Clave a chequear.
	 * @throws InvalidKeyException si la clave es inválida.
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("Clave inválida");
	}
	
	/**
	 * Aplica la función de hash a la clave pasada por parámetro.
	 * @param key Clave a aplicar la función de hash.
	 * @return Resultado de la función de hash.
	 * @throws InvalidKeyException si la clave es inválida.
	 */
	protected int hashThisKey(K key) throws InvalidKeyException {
		checkKey(key); //Por si key es nula
		return Math.abs(key.hashCode() % N); //Por si acaso pasan un negativo
	}

	/**
	 * Busca una entrada con clave igual a una clave dada y la devuelve, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Entrada<K,V> entrada=null;
		try {
			for(Position<Entrada<K,V>> p: arregloBucket[hashThisKey(key)].positions()) {
				if(p.element().getKey().equals(key))
					entrada=p.element();
			}
		}
		catch(InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
		return entrada;	
	}

	/**
	 * Retorna una colección iterable que contiene todas las entradas con clave igual a una clave dada.
	 * @param key Clave de las entradas a buscar.
	 * @return Colección iterable de las entradas encontradas.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> lista= new ListaDoblementeEnlazada<Entry<K,V>>();
		try {
			for(Position<Entrada<K,V>> p: arregloBucket[hashThisKey(key)].positions()) {
				if(p.element().getKey().equals(key))
					lista.addLast(p.element());
			}
		}
		catch(InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	/**
	 * Devuelve el siguiente número primo del número pasado por parámetro.
	 * @param n Número primo.
	 * @return Siguiente número primo.
	 */
	private int nextPrimo(int n) {
		int num= n+1;
		int primo=0;
		while(primo==0) {
			int cantDivisores=0;
			int divisor=1;
			while(cantDivisores<=1 && divisor<num) {
				if(num % divisor ==0)
					cantDivisores++;
				divisor++;
			}
			if(divisor==num && cantDivisores==1)
				primo=num;
			else num++;
		}
		return primo;
	}
	
	/**
	 * Vuelve a distribuir todos los elementos 
	 */
	@SuppressWarnings("unchecked")
	private void reHash() {
		Iterable<Entry<K,V>> entradas= entries();
		N= nextPrimo(N) ; 
		n=0;
		arregloBucket= (PositionList<Entrada<K,V>> []) new ListaDoblementeEnlazada[N];
		for(int i=0; i<N; i++) 
			arregloBucket[i]= new ListaDoblementeEnlazada<Entrada<K,V>>();
		try {
			for(Entry<K,V> e: entradas)
				this.insert(e.getKey(),e.getValue());
		}
		catch(InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Inserta una entrada con una clave y un valor dado en el diccionario y retorna la entrada creada.
	 * @param key Clave de la entrada a crear.
	 * @return value Valor de la entrada a crear.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entrada<K,V> nuevo= new Entrada<K,V> (key,value);
		arregloBucket[hashThisKey(key)].addLast(nuevo);
		n++;
		if(!(n/N < factor))
			reHash();
		return nuevo;
	}

	/**
	 * Remueve una entrada dada en el diccionario y devuelve la entrada removida.
	 * @param e Entrada a remover.
	 * @return Entrada removida.
	 * @throws InvalidEntryException si la entrada no está en el diccionario o es inválida.
	 */
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e==null)
			throw new InvalidEntryException("Entrada inválida");
		Entry<K,V> entrada=null;
		try {
			Iterator<Position<Entrada<K,V>>> it= arregloBucket[hashThisKey(e.getKey())].positions().iterator(); 
			while(it.hasNext() && entrada==null) {
				Position<Entrada<K, V>> p= it.next();
				if(p.element().getKey().equals(e.getKey())
						&& p.element().getValue().equals(e.getValue())) {
					arregloBucket[hashThisKey(e.getKey())].remove(p);
					n--;
					entrada=e;
				}
			}
		}
		catch(InvalidKeyException | InvalidPositionException exc) {
			System.out.println(exc.getMessage());
		}
		if(entrada==null)
			throw new InvalidEntryException("Entrada inválida");
		return entrada;
			

	}

	/**
	 * Retorna una colección iterable con todas las entradas en el diccionario.
	 * @return Colección iterable de todas las entradas.
	 */
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0;i<N ;i++) {
			try {
				if(!arregloBucket[i].isEmpty()) {
					Position<Entrada<K, V>> cursor= arregloBucket[i].first();
					while(cursor!=null) {
						entries.addLast(cursor.element());
						cursor= (cursor==arregloBucket[i].last())? null: arregloBucket[i].next(cursor);
					}
					}
			}
			catch(EmptyListException | BoundaryViolationException | InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
		}
		return entries;
	}
	
	//ejercicio 5
	
	/*Agregue una operaci�n a la clase Diccionario<K,V> programada en el ejercicio 3  con la
	 *  siguiente signatura: Iterable<Entry<K,V>> eliminarTodas(K c,V v) throws invalidKeyException.
	 *   Este m�todo debe eliminar del diccionario todas aquellas entradas con clave c y 
	 *   valor v. Retorna un iterable con todas las entradas eliminadas. 
	 *   Recuerde que est� agregando un m�todo a la clase Diccionario<K,V>, por lo tanto 
	 *   tiene total acceso a su estructura.
*/
	
	public Iterable<Entry<K,V>> eliminarTodas(K c,V v) throws InvalidKeyException{
		if(c==null)
			throw new InvalidKeyException("Clave nula");
		PositionList<Entry<K,V>> ret=new ListaDoblementeEnlazada<Entry<K,V>>();
		try {
			PositionList<Entrada<K,V>>bucket= arregloBucket[hashThisKey(c)];
			for(Position<Entrada<K,V>> p:bucket.positions())
				if(p.element().getKey().equals(c) && p.element().getValue().equals(v))
				{bucket.remove(p);
				 n--;
				 ret.addLast(p.element());}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		return ret;
					
	}
	

}
