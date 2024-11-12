package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;

public abstract class Tramo implements Viaje.Tramo {
	
	protected Linea lineaInicial, lineaFinal;
	protected Estacion estacionInicial, estacionFinal;
	
	
	@Override
	public Linea getLineaInicial() {
		return this.lineaInicial;
	}

	@Override
	public Linea getLineaFinal() {
		return this.lineaFinal;
	}

	@Override
	public Estacion getEstacionInicial() {
		return this.estacionInicial;
	}

	@Override
	public Estacion getEstacionFinal() {
		return this.estacionFinal;
	}
}