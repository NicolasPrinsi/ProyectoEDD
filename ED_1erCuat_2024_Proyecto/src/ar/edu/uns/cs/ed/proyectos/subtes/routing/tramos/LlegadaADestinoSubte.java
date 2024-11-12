package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;

public class LlegadaADestinoSubte extends Tramo implements Viaje.LlegadaADestino {
	
	private LlegadaADestinoSubte(Builder b) {
		this.estacionInicial = b.estacionInicial;
		this.estacionFinal = b.estacionFinal;
		this.lineaInicial = b.lineaInicial;
		this.lineaFinal = b.lineaFinal;
	}
	
	@Override
	public String toString() {
		return  "Finalmente, arribar a '"+this.estacionFinal.getNombre()+
				"' (línea: "+this.lineaInicial.getNombre()+").";
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	public static class Builder implements Viaje.LlegadaADestino.Builder {

		private Linea lineaInicial, lineaFinal;
		private Estacion estacionInicial, estacionFinal;
		
		@Override
		public Builder setDestino(
				Estacion destino) {
			this.estacionInicial = destino;
			this.estacionFinal = destino;
			return this;
		}

		@Override
		public Builder setLineaDestino(
				Linea destino) {
			this.lineaInicial = destino;
			this.lineaFinal = destino;
			return this;
		}

		@Override
		public LlegadaADestinoSubte build() {
			if (this.estacionInicial == null) throw new IllegalStateException("Error: el tramo de llegada a destino tiene la estación inicial nula.");
			if (this.estacionFinal == null) throw new IllegalStateException("Error: el tramo de llegada a destino tiene la estación final nula.");
			if (this.lineaInicial == null) throw new IllegalStateException("Error: el tramo de llegada a destino tiene la línea inicial nula.");
			if (this.lineaFinal == null) throw new IllegalStateException("Error: el tramo de llegada a destino tiene la línea final nula.");
			
			return new LlegadaADestinoSubte(this);
		}
	}
}
