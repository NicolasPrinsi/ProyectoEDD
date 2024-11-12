package ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.util.Par;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Router;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.PrecomputationBuilder;

public interface Proyecto {

	public String getComision();
	public void precomputarLineasDeEstaciones(PrecomputationBuilder pb);
	public Iterable<Linea> getLineas(Estacion estacion);
	public void precomputarCombinaciones(PrecomputationBuilder pb);
	public Iterable<Par<Linea,Estacion>> getCombinaciones(Linea linea);
	public Viaje viajar(Estacion origen, Estacion destino, Router router);
	
}
