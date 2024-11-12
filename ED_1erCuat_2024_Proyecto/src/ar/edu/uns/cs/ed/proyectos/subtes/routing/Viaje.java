package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.*;

public interface Viaje {

	public Estacion getOrigen();
	public Estacion getDestino();
	public Iterable<Tramo> obtenerDetalleViaje();
	
	
	public interface ForwardsBuilder {
		public ForwardsMiddleEndBuilder setOrigen(Estacion origen, Linea linea);
	}
	
	public interface ForwardsMiddleEndBuilder {
		
		public ForwardsMiddleEndBuilder agregarDireccion(Estacion destino, Estacion cabeceraDireccion, Linea linea);
		public ForwardsMiddleEndBuilder agregarCombinacion(Linea lineaFinal);
		public Viaje setDestinoAndBuild(Estacion destino, Linea linea);
	}
		
	public interface BackwardsBuilder {
		
		public BackwardsMiddleStartBuilder setDestino(Estacion destino, Linea linea);
	}
	
	public interface BackwardsMiddleStartBuilder {
		public BackwardsMiddleStartBuilder agregarCombinacion(Linea lineaInicial);
		public BackwardsMiddleStartBuilder agregarDireccion(Estacion origen, Estacion cabeceraDireccion, Linea linea);
		public Viaje setOrigenAndBuild(Estacion origen, Linea linea);
	}
	
	public interface Tramo {
		public Linea getLineaInicial();
		public Linea getLineaFinal();
		public Estacion getEstacionInicial();
		public Estacion getEstacionFinal();
	}
	
	public interface SalidaDeOrigen extends Tramo {
		
		public interface Builder {
			public Builder setOrigen(Estacion origen);
			public Builder setLineaOrigen(Linea lineaOrigen);
			public SalidaDeOrigen build();
		}
	}
	
	public interface LlegadaADestino extends Tramo {
		
		public interface Builder {
			public Builder setDestino(Estacion destino);
			public Builder setLineaDestino(Linea destino);
			public LlegadaADestino build();
		}
		
	}
	
	public interface TramoIntraLinea extends Tramo {
		
		public Estacion getCabeceraDireccion();
		
		public interface Builder {
			public Builder setEstacionInicialTramo(Estacion estacionInicial);
			public Builder setEstacionFinalTramo(Estacion estacionFinal);
			public Builder setLinea(Linea linea);
			public Builder setCabeceraDireccion(Estacion cabeceraDireccion);
			public TramoIntraLinea build();
		}
		
	}
	
	public interface Combinacion extends Tramo {
		
		public interface Builder {
			
			public Builder setEstacionDeCombinacion(Estacion estacionDeCombinacion);
			public Builder setLineaInicial(Linea lineaInicial);
			public Builder setLineaFinal(Linea lineaFinal);
			public Combinacion build();
			
		}
		
	}
}
