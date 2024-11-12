package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.EstacionSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.LineaSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.Builder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.PrecomputationBuilder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto;

public class SimpleSubteNetworkRouterBuilder extends RouterSubte.Builder {
	
	public static SimpleSubteNetworkRouterBuilder getBuilder() {
		return new SimpleSubteNetworkRouterBuilder();
	}
	
	public PrecomputationBuilder buildSimpleSubteNetwork(Class<Proyecto> proyectoClass) {
		
		Linea l1 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("E1"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E2"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E3"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E4"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E5"))
				.build("L1");
	
		Linea l2 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("E6"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E2"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E7"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E8"))
				.build("L2");
		
		Linea l3 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("E9"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E4"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E10"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E11"))
				.build("L3");
		
		Linea l4 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("E12"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E10"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E13"))
				.agregarEstacion(EstacionSubte.getBuilder().build("E14"))
				.build("L4");
		
		this.agregarLinea(l1)
			.agregarLinea(l2)
			.agregarLinea(l3)
			.agregarLinea(l4);
			
		return super.build("Red simple (acíclica) de subtes", proyectoClass);
	}

}
