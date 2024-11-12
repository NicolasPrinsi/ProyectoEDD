package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import java.lang.reflect.InvocationTargetException;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.SalidaDeOrigenSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.util.Par;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.InvalidKeyException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.MapeoConHash;

public class RouterSubte implements Router {

	private Proyecto proyecto;
	private String nombre;
	private Map<String, Linea> redSubte;
	
	private RouterSubte(PrecomputationBuilder b) {
		this.nombre = b.nombre;
		this.proyecto = b.proyecto;
		this.redSubte = b.redSubte;
	}
	
	@Override
	public String getNombre() {
		return this.nombre;
	}
	
	@Override
	public Iterable<Estacion> getAllEstaciones() {
		Map<Estacion,Integer> estaciones = new MapeoConHash<>();
		try {
			for (Linea l: this.redSubte.values()) {
				
				Estacion e = l.getCabeceraInicial();
				while (e != null) {
					estaciones.put(e, 1);
					e = l.viajarHaciaCabeceraFinal(e);
				}
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return estaciones.keys();
	}
	
	@Override
	public Iterable<Linea> getAllLineas() {
		return this.redSubte.values();
	}
	
	@Override
	public Iterable<Linea> getLineas(Estacion estacion) {
		return this.proyecto.getLineas(estacion);
	}
	
	@Override
	public Iterable<Par<Linea,Estacion>> getCombinaciones(Linea linea) {
		return this.proyecto.getCombinaciones(linea);
	}
	
	@Override
	public Viaje viajar(Estacion origen, Estacion destino) {
		if (origen.equals(destino)) throw new IllegalStateException("Error: Las estaciones de origen y destino deben ser diferentes para rutear un viaje.");
		
		return proyecto.viajar(origen, destino, this);
	}
	
	@Override
	public void validarViaje(Viaje viaje) {
		Estacion estacionActual = null;
		Linea lineaActual = null;
		
		/* Sólo se controla que se pueda viajar el viaje sobre la red
		 * El viaje ya es consistente en sí por construcción
		 */
		for(Viaje.Tramo tramoActual:viaje.obtenerDetalleViaje()) {
			
			if (tramoActual instanceof Viaje.SalidaDeOrigen) {
				
				// Inicializa la posición, línea y dirección de inicio del viaje
				estacionActual = tramoActual.getEstacionInicial();
				lineaActual = tramoActual.getLineaInicial();
				
			} else if (tramoActual instanceof Viaje.TramoIntraLinea) { 
				
				// Validación de precondiciones
				if (lineaActual!=tramoActual.getLineaInicial() ||
						lineaActual!=tramoActual.getLineaFinal()) {
					throw new IllegalStateException("Error: La línea del TramoIntraLinea del viaje no es consistente.");
				}
				if (!estacionActual.equals(tramoActual.getEstacionInicial())) {
					throw new IllegalStateException("Error: La estación inicial del TramoIntraLinea del viaje no es consistente.");
				}
				
				// Se "viaja" por la línea actual desde el inicio del tramo hasta el final
				Estacion cabeceraDireccionActual = ((Viaje.TramoIntraLinea) tramoActual).getCabeceraDireccion();
				while (!estacionActual.equals(tramoActual.getEstacionFinal())) {
					if (cabeceraDireccionActual.equals(lineaActual.getCabeceraInicial())) {
						estacionActual = lineaActual.viajarHaciaCabeceraInicial(estacionActual);
					} else if (cabeceraDireccionActual.equals(lineaActual.getCabeceraFinal())) {
						estacionActual = lineaActual.viajarHaciaCabeceraFinal(estacionActual);
					} else {
						throw new IllegalStateException("Error: Cabeceras inconsistentes en el TramoIntraLinea del viaje.");
					}
					
					if (estacionActual == null) {
						throw new IllegalStateException("Error: No se pudo llegar a la estación final del TramoIntraLinea del viaje.");
					}
				}
				
			} else if (tramoActual instanceof Viaje.Combinacion) {
				
				// Validación de precondiciones
				if (lineaActual!=tramoActual.getLineaInicial()) {
					throw new IllegalStateException("Error: La línea inicial del tramo de Combinacion del viaje no es consistente.");
				}
				if (!estacionActual.equals(tramoActual.getEstacionInicial()) ||
						!estacionActual.equals(tramoActual.getEstacionFinal())) {
					throw new IllegalStateException("Error: La estación del tramo de Combinacion del viaje no es consistente.");
				}
				
				// Se conmuta utilizando la combinación
				lineaActual = tramoActual.getLineaFinal();				
				
			} else if (tramoActual instanceof Viaje.LlegadaADestino) {
				
				/* Validación de condiciones de final de viaje (las condiciones 
				 * actuales del "viaje" deberían ser consistenes con el tramo
				 * de LlegadaADestino)
				 */
				if (lineaActual!=tramoActual.getLineaInicial() ||
						lineaActual!=tramoActual.getLineaFinal()) {
					throw new IllegalStateException("Error: La línea del tramo de LlegadaADestino del viaje no es consistente.");
				}
				if (!estacionActual.equals(tramoActual.getEstacionInicial()) ||
						!estacionActual.equals(tramoActual.getEstacionFinal())) {
					throw new IllegalStateException("Error: La estación del tramo de LlegadaADestino del viaje no es consistente.");
				}
				
			} else {
				throw new IllegalStateException("Error: Tramo no soportado para el viaje/ruteo actual.");
			}
		}
	}
	

	public static Builder getBuilder() {
		return new Builder();
	}
	
	
	public static class Builder implements Router.Builder {

		private Proyecto proyecto;
		private String nombre;
		private Map<String, Linea> redSubte = new MapeoConHash<String, Linea>();
		
		@Override
		public Builder agregarLinea(Linea linea) {
			try {
				this.redSubte.put(linea.getNombre(), linea);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public PrecomputationBuilder build(String nombre, Class<Proyecto> proyectoClass) {
			if (nombre==null) throw new IllegalArgumentException("Error: El nombre del router es nulo.");
			if (proyectoClass==null) throw new IllegalArgumentException("Error: Se inyectó una clase de proyecto nula en el router.");
			if (redSubte.size()<1) throw new IllegalStateException("Error: El router requiere al menos una línea de subte para funcionar.");
			
			this.nombre = nombre;
			try {
				this.proyecto = proyectoClass.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new IllegalStateException("Error: Problema al cargar el proyecto: "+proyectoClass+".");
			}
			return new PrecomputationBuilder(this);
		}
	}
		
		
			
	public static class PrecomputationBuilder implements Router.PrecomputationBuilder {

		private Proyecto proyecto;
		private String nombre;
		private Map<String, Linea> redSubte;
		private boolean precomputarLineasDeEstacionesCompleted, precomputarCombinacionesCompleted;
		
		private PrecomputationBuilder(Builder b) {
			this.nombre = b.nombre;
			this.proyecto = b.proyecto;
			this.redSubte = b.redSubte;
			this.precomputarLineasDeEstacionesCompleted = false;
			this.precomputarCombinacionesCompleted = false;
		}
		
		@Override
		public Iterable<Linea> getAllLineas() {
			return this.redSubte.values();
		}
		
		@Override
		public PrecomputationBuilder precomputarLineasDeEstaciones() {
			proyecto.precomputarLineasDeEstaciones(this);
			this.precomputarLineasDeEstacionesCompleted = true;
			return this;
		}

		@Override
		public PrecomputationBuilder precomputarCombinaciones() {
			proyecto.precomputarCombinaciones(this);
			this.precomputarCombinacionesCompleted = true;
			return this;
		}

		@Override
		public Router build() {
			if (!this.precomputarLineasDeEstacionesCompleted ||
				!this.precomputarCombinacionesCompleted) {
				throw new IllegalStateException("Error: No se completaron los procesos de precomputación del Router.");
			}
			return new RouterSubte(this);
		}

	}
}
