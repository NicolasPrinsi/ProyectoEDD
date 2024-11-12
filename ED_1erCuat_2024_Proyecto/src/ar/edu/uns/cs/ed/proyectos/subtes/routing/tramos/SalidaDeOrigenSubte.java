package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;

public class SalidaDeOrigenSubte extends Tramo implements Viaje.SalidaDeOrigen {
	
	private SalidaDeOrigenSubte(Builder b) {
		this.estacionInicial = b.estacionInicial;
		this.estacionFinal = b.estacionFinal;
		this.lineaInicial = b.lineaInicial;
		this.lineaFinal = b.lineaFinal;
	}
	
	@Override
	public String toString() {
		return  "Salir de '"+this.estacionInicial.getNombre()+
				"' (línea: "+this.lineaInicial.getNombre()+").";
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	public static class Builder implements Viaje.SalidaDeOrigen.Builder {
		
		private Linea lineaInicial, lineaFinal;
		private Estacion estacionInicial, estacionFinal;

		@Override
		public Builder setOrigen(
				Estacion origen) {
			this.estacionInicial = origen;
			this.estacionFinal = origen;
			return this;
		}

		@Override
		public Builder setLineaOrigen(
				Linea lineaOrigen) {
			this.lineaInicial = lineaOrigen;
			this.lineaFinal = lineaOrigen;
			return this;
		}

		@Override
		public SalidaDeOrigenSubte build() {
			if (this.estacionInicial == null) throw new IllegalStateException("Error: el tramo de salida de origen tiene la estación inicial nula.");
			if (this.estacionFinal == null) throw new IllegalStateException("Error: el tramo de salida de origen tiene la estación final nula.");
			if (this.lineaInicial == null) throw new IllegalStateException("Error: el tramo de salida de origen tiene la línea inicial nula.");
			if (this.lineaFinal == null) throw new IllegalStateException("Error: el tramo de salida de origen tiene la línea final nula.");
			
			return new SalidaDeOrigenSubte(this);
		}
	}
}