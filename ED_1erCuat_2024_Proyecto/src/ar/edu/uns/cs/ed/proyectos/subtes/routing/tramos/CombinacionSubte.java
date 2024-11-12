package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;

public class CombinacionSubte extends Tramo implements Viaje.Combinacion {
	
	private CombinacionSubte(Builder b) {
		this.estacionInicial = b.estacionInicial;
		this.estacionFinal = b.estacionFinal;
		this.lineaInicial = b.lineaInicial;
		this.lineaFinal = b.lineaFinal;
	}
	
	@Override
	public String toString() {
		return  "En '"+this.estacionFinal.getNombre()+
				"' combinar con línea '"+this.lineaFinal.getNombre()+"'.";
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	public static class Builder implements Viaje.Combinacion.Builder {

		private Linea lineaInicial, lineaFinal;
		private Estacion estacionInicial, estacionFinal;
		
		@Override
		public Builder setEstacionDeCombinacion(
				Estacion estacionDeCombinacion) {
			this.estacionInicial = estacionDeCombinacion;
			this.estacionFinal = estacionDeCombinacion;
			return this;
		}

		@Override
		public Builder setLineaInicial(
				Linea lineaInicial) {
			this.lineaInicial = lineaInicial;
			return this;
		}

		@Override
		public Builder setLineaFinal(
				Linea lineaFinal) {
			this.lineaFinal = lineaFinal;
			return this;
		}

		@Override
		public CombinacionSubte build() {
			if (this.estacionInicial == null) throw new IllegalStateException("Error: el tramo de combinación tiene la estación inicial nula.");
			if (this.estacionFinal == null) throw new IllegalStateException("Error: el tramo de combinación tiene la estación final nula.");
			if (this.lineaInicial == null) throw new IllegalStateException("Error: el tramo de combinación tiene la línea inicial nula.");
			if (this.lineaFinal == null) throw new IllegalStateException("Error: el tramo de combinación tiene la línea final nula.");
			
			return new CombinacionSubte(this);
		}
		
	}
}
