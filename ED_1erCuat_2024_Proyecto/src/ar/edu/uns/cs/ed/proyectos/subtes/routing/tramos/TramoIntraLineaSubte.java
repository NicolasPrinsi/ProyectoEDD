package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;


public class TramoIntraLineaSubte extends Tramo implements Viaje.TramoIntraLinea {
	
	private Estacion cabeceraDireccion;
	
	private TramoIntraLineaSubte(Builder b) {
		this.estacionInicial = b.estacionInicial;
		this.estacionFinal = b.estacionFinal;
		this.lineaInicial = b.lineaInicial;
		this.lineaFinal = b.lineaFinal;
		this.cabeceraDireccion = b.cabeceraDireccion;
	}
	
	@Override
	public String toString() {
		return  "Viajar de '"+this.estacionInicial.getNombre()+
				"' a '"+this.estacionFinal.getNombre()+"' (línea: "+
				this.lineaInicial.getNombre()+") en dirección a '"+
				this.cabeceraDireccion.getNombre()+"'.";
	}
	
	@Override
	public Estacion getCabeceraDireccion() {
		return this.cabeceraDireccion;
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	public static class Builder implements Viaje.TramoIntraLinea.Builder {

		private Linea lineaInicial, lineaFinal;
		private Estacion estacionInicial, estacionFinal, cabeceraDireccion;
		
		@Override
		public Builder setEstacionInicialTramo(
				Estacion estacionInicial) {
			this.estacionInicial = estacionInicial;
			return this;
		}
		
		@Override
		public Builder setEstacionFinalTramo(
				Estacion estacionFinal) {
			this.estacionFinal = estacionFinal;
			return this;
		}

		@Override
		public Builder setLinea(
				Linea linea) {
			this.lineaInicial = linea;
			this.lineaFinal = linea;
			return this;
		}

		@Override
		public Builder setCabeceraDireccion(
				Estacion cabeceraDireccion) {
			this.cabeceraDireccion = cabeceraDireccion;
			return this;
		}

		@Override
		public TramoIntraLineaSubte build() {
			if (this.estacionInicial == null) throw new IllegalStateException("Error: el tramo de intra-línea tiene la estación inicial nula.");
			if (this.estacionFinal == null) throw new IllegalStateException("Error: el tramo de intra-línea tiene la estación final nula.");
			if (this.estacionInicial.equals(this.estacionFinal)) throw new IllegalStateException("Error: el tramo de intra-línea tiene la misma estación inicial y final.");
			if (this.lineaInicial == null) throw new IllegalStateException("Error: el tramo de intra-línea tiene la línea inicial nula.");
			if (this.lineaFinal == null) throw new IllegalStateException("Error: el tramo de intra-línea tiene la línea final nula.");
			if (this.cabeceraDireccion == null) throw new IllegalStateException("Error: el intra-línea de origen tiene la cabecera de dirección nula.");
			
			return new TramoIntraLineaSubte(this);
		}
	}
	
}
