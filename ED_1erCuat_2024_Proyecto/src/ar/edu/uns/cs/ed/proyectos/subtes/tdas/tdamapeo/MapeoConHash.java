package ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo;


import java.util.Iterator;

import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.*;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.Position;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.PositionList;

/**
 * Clase MapeoConHash, implementa un mapeo con un arreglo de bucket (Hash abierto). 
 * @author Faccio, Julio C.
 * @author Fernandez Tierno, Javier.
 */
public class MapeoConHash<K, V> implements Map<K,V> {
	
	protected PositionList<Entrada<K,V>> [] arregloBucket;
	protected int n;
	protected int N;
	
	
	/**
	 * Constructor: Inicializa las variables de instancia.
	 */
	public MapeoConHash(){
		n=0;
		N=11;
		arregloBucket= new PositionList[N];
		for(int i=0; i<N; i++){
			arregloBucket[i]=new ListaDoblementeEnlazada<Entrada<K,V>>();
		}
	}
	/**
	 * Consulta el n�mero de entradas del mapeo.
	 * @return N�mero de entradas del mapeo.
	 */
	public int size() {
		return n;
	}
	/**
	 * Consulta si el mapeo est� vac�o.
	 * @return Verdadero si el mapeo est� vac�o, falso en caso contrario.
	 */
	public boolean isEmpty() {	
		return n==0;
	}
	/**
	 * Busca una entrada con clave igual a una clave dada y devuelve el valor asociado, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Valor de la entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn=null;
		boolean encontre=false;
			Iterator<Entrada<K,V>> it=arregloBucket[hashThisKey(key)].iterator();
			while(it.hasNext() && !encontre){
				Entrada<K,V> e=it.next();
				if(e.getKey().equals(key)){
					toReturn=e.getValue();
					encontre=true;
				}
			}			
		return toReturn;
	}
	/**
	 * Si el mapeo no tiene una entrada con clave key, inserta una entrada con clave key y valor value en el mapeo y devuelve null. 
	 * Si el mapeo ya tiene una entrada con clave key, entonces remplaza su valor y retorna el viejo valor.
	 * @param key Clave de la entrada a crear.
	 * @param value Valor de la entrada a crear. 
	 * @return Valor de la vieja entrada.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		boolean existe=false;
		V toReturn=null;
		
			Iterator<Entrada<K,V>> it= arregloBucket[hashThisKey(key)].iterator();
			while(it.hasNext() && !existe){
				Entrada<K,V> e=it.next();
				if(e.getKey().equals(key)){
					toReturn=e.getValue();
					e.setValue(value);					
					existe=true;
				}
			}
		
		

		if(!existe){
			Entrada<K,V> nuevo=new Entrada(key,value);
			arregloBucket[hashThisKey(key)].addLast(nuevo);
			n++;
			if(!(n/N < 0.5)) //Factor de carga GT.
				reHash();
		}
		
		return toReturn;
	}
	/**
	 * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
	 * @param e Entrada a remover.
	 * @return Valor de la entrada removida.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		boolean existe=false;
		try{    
			Position<Entrada<K,V>> pos=arregloBucket[hashThisKey(key)].first();
			while(!existe && pos!=null){
				if(pos.element().getKey().equals(key)){
					V toReturn=pos.element().getValue();
					arregloBucket[hashThisKey(key)].remove(pos);
					existe=true;
					n--;
					return toReturn;
					}
				else{
					if(pos==arregloBucket[hashThisKey(key)].last())
						pos=null;
					else
						pos=arregloBucket[hashThisKey(key)].next(pos);
					}
				}
			}
		catch(InvalidPositionException e){
			e.getMessage();
		}
		catch(BoundaryViolationException e){
			e.getMessage();
		}
		catch(EmptyListException e){
			e.getMessage();
		}
		return null;

	}
	/**
	 * Retorna una colecci�n iterable con todas las claves del mapeo.
	 * @return Colecci�n iterable con todas las claves del mapeo.
	 */
	public Iterable<K> keys() {
		ListaDoblementeEnlazada<K> kList=new ListaDoblementeEnlazada<K>();
		for(int i=0; i<N; i++){
			Iterator<Entrada<K,V>> it=arregloBucket[i].iterator();
			while(it.hasNext()){
				kList.addLast(it.next().getKey());
				}
			}
		return kList;
		}
	/**
	 * Retorna una colecci�n iterable con todas los valores del mapeo.
	 * @return Colecci�n iterable con todas los valores del mapeo.
	 */
	public Iterable<V> values() {
		ListaDoblementeEnlazada<V> vList=new ListaDoblementeEnlazada<V>();
		for(int i=0; i<N; i++){
			Iterator<Entrada<K,V>> it=arregloBucket[i].iterator();
			while(it.hasNext()){
				vList.addLast(it.next().getValue());
				}
			}
		return vList;
		}

	/**
	 * Retorna una colecci�n iterable con todas las entradas del mapeo.
	 * @return Colecci�n iterable con todas las entradas del mapeo.
	 */	
	public Iterable<Entry<K, V>> entries() {
		ListaDoblementeEnlazada<Entry<K,V>> eList=new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0; i<N; i++){
			Iterator<Entrada<K,V>> it=arregloBucket[i].iterator();
			while(it.hasNext()){
				eList.addLast(it.next());
			}
		}
		return eList;
	}
	
	
	//-----------------------------------------METODOS AUXILIARES------------------------------------------------------
	
	/**
	 *	Valida la clave utilizada 
	 * @param k clave a validar
	 * @throws InvalidKeyException si no es una clave v�lida.
	 */
	private void checkKey(K k) throws InvalidKeyException{
		if (k==null){
			throw new InvalidKeyException ("Error en Mapeo: Clave nula");
		}
	}
	
	/**
	 * Calcula funci�n hash a partir de la clave k dada.
	 * @param k clave.
	 * @return posici�n del "bucket" donde se ubicar� la entrada con clave k.
	 * @throws InvalidKeyException
	 */
	private int hashThisKey(K k) throws InvalidKeyException{
		checkKey(k); //por si acaso es nula.
		return Math.abs(k.hashCode() % N); //por si acaso pasan un negativo.
	}
	
	/**
	 * Calcula el siguiente n�mero primo de un n�mero p recibido.
	 * @param p tama�o actual del arreglo de bucket.
	 * @return n�mero primo que servir� para determinar el nuevo tama�o del arreglo de bucket.
	 */
	private static int nextPrimo(int p){
	    int toReturn=p+1;		
	    while (!esPrimo(toReturn)) {
	        toReturn++;
	        }
	    return toReturn;
	}

	/**
	 * Determina si un numero es primo.
	 * @param p n�mero a determinar si es o no primo.
	 * @return <code>true</code> si p es un n�mero primo, <code>false</code> caso contrario. 
	 */
	private static boolean esPrimo(int p){
		boolean toReturn=false;
		boolean divide=false;
		int maxDivisor = (int) Math.ceil(Math.sqrt(p));
		for (int divisor = 2; divisor <= maxDivisor && !divide ; divisor++) {
            if (p % divisor == 0) {
                divide = true;
            	}
			}		
       if(divide)
			toReturn=false;
       		
			else 
				toReturn=true;
		return toReturn;
	}
	
	/**
	 * Incrementa el tama�o del arreglo de bucket al siguiente numero primo del tama�o actual.
	 */
	private void reHash(){		
		Iterable<Entry<K,V>> entradas=entries();
		N=nextPrimo(N);
		n=0;
		arregloBucket= new PositionList[N];
		for(int i=0;i<N;i++){
			arregloBucket[i]=new ListaDoblementeEnlazada<Entrada<K,V>>();
		}
		try{
			for(Entry<K,V> e: entradas){
				this.put(e.getKey(), e.getValue());
			}
		}catch(InvalidKeyException error){
			System.out.println("Error en reHash: "+ error.getMessage());
		}
	}
}