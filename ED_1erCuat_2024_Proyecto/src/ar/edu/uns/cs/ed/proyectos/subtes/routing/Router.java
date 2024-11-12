package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.*;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.util.Par;

public interface Router {

	public String getNombre();
	public Iterable<Estacion> getAllEstaciones();
	public Iterable<Linea> getAllLineas();
	public Iterable<Linea> getLineas(Estacion estacion);
	public Iterable<Par<Linea,Estacion>> getCombinaciones(Linea linea);
	public Viaje viajar(Estacion origen, Estacion destino);
	public void validarViaje(Viaje viaje);
	
	public interface Builder {
		
		public Builder agregarLinea(Linea linea);
		public PrecomputationBuilder build(String nombre, Class<Proyecto> proyectoClass);
	}
	
	public interface PrecomputationBuilder {
		
		public Iterable<Linea> getAllLineas();
		public PrecomputationBuilder precomputarLineasDeEstaciones();
		public PrecomputationBuilder precomputarCombinaciones();
		public Router build();
		
	}
	
}
