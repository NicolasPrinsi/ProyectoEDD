package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import java.util.Iterator;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.Tramo;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.LlegadaADestinoSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.SalidaDeOrigenSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.EmptyListException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.PositionList;

public class ViajeSubte implements Viaje {

	PositionList<Tramo> trip;
	
	private ViajeSubte(ForwardsMiddleEndBuilder fb) {
		this.trip = fb.trip;
	}
	
	private ViajeSubte(BackwardsMiddleStartBuilder bb) {
		this.trip = bb.trip;
	}
	
	private static void checkConsistenciaViaje(PositionList<Tramo> trip) {
		
		if (trip.size()<3) {
			throw new IllegalStateException("Error: Todo viaje debe tener un tramo de origen, al menos un tramo intermedio (dirección o combinación) y un tramo de destino.");
		}
		
		Iterator<Tramo> it = trip.iterator();
		Tramo currentTramo;
		Estacion lastEstacion;
		Linea lastLinea;
		
		if (it.hasNext() && ((currentTramo = it.next()) instanceof SalidaDeOrigenSubte)) {
			lastEstacion = currentTramo.getEstacionFinal();
			lastLinea = currentTramo.getLineaFinal();
		} else {
			throw new IllegalStateException("Error: No se encontró el tramo de salida de origen en el viaje.");
		}
		
		while (it.hasNext()) {
			currentTramo = it.next();
			if (currentTramo.getEstacionInicial()==lastEstacion) {
				lastEstacion = currentTramo.getEstacionFinal();
			} else {
				throw new IllegalStateException("Error: Inconsistencias en la sucesión de estaciones en el viaje.");
			}
			
			if (currentTramo.getLineaInicial()==lastLinea) {
				lastLinea = currentTramo.getLineaFinal();
			} else {
				throw new IllegalStateException("Error: Inconsistencias en la sucesión de líneas en el viaje.");
			}
			
			if (currentTramo instanceof LlegadaADestinoSubte && it.hasNext()) {
				//Este debe ser el último tramo y único en su tipo. Si no lo es, error.
				throw new IllegalStateException("Error: Se encontró el tramo de llegada a destino en el medio del viaje.");
			}
		}
		
		
	}
	
	
	@Override
	public Estacion getOrigen() {
		Estacion estacionOrigen=null;
		try {
			estacionOrigen = this.trip.first().element().getEstacionInicial();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
			
		return estacionOrigen;
	}

	@Override
	public Estacion getDestino() {
		Estacion estacionOrigen=null;
		try {
			estacionOrigen = this.trip.last().element().getEstacionFinal();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
			
		return estacionOrigen;
	}

	@Override
	public Iterable<Viaje.Tramo> obtenerDetalleViaje() {
		return this.trip;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Tramo t: this.obtenerDetalleViaje()) {
			sb.append(t+"\n");
			//System.out.println(t);
		}
		return sb.append("\n").toString();
	}

	public static ForwardsBuilder getForwardsBuilder() {
		return new ForwardsBuilder();
	}
	
	public static BackwardsBuilder getBackwardsBuilder() {
		return new BackwardsBuilder();
	}
	
	
	public static class ForwardsBuilder implements Viaje.ForwardsBuilder {
		
		private PositionList<Viaje.Tramo> trip;

		@Override
		public ForwardsMiddleEndBuilder setOrigen(Estacion origen, Linea linea) {
			
			this.trip = new ListaDoblementeEnlazada<Viaje.Tramo>();
			Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.SalidaDeOrigenSubte.getBuilder()
							.setOrigen(origen)
							.setLineaOrigen(linea)
							.build();
			this.trip.addLast(t);
			return new ForwardsMiddleEndBuilder(this);
		}
	}
	
	public static class ForwardsMiddleEndBuilder implements Viaje.ForwardsMiddleEndBuilder {

		private PositionList<Viaje.Tramo> trip;
		
		private ForwardsMiddleEndBuilder(ForwardsBuilder fb) {
			this.trip = fb.trip;
		}
		
		@Override
		public Viaje.ForwardsMiddleEndBuilder agregarDireccion(
				Estacion destino, Estacion cabeceraDireccion, Linea linea) {
			try {
				Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.TramoIntraLineaSubte.getBuilder()
								.setEstacionInicialTramo(trip.last().element().getEstacionFinal())
								.setEstacionFinalTramo(destino)
								.setCabeceraDireccion(cabeceraDireccion)
								.setLinea(linea)
								.build();
				this.trip.addLast(t);
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public Viaje.ForwardsMiddleEndBuilder agregarCombinacion(Linea lineaFinal) {
			try {
				Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.CombinacionSubte.getBuilder()
						.setEstacionDeCombinacion(trip.last().element().getEstacionFinal())
						.setLineaInicial(trip.last().element().getLineaFinal())
						.setLineaFinal(lineaFinal)
						.build();
				this.trip.addLast(t);
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public Viaje setDestinoAndBuild(Estacion destino, Linea linea) {

			Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.LlegadaADestinoSubte.getBuilder()
					.setDestino(destino)
					.setLineaDestino(linea)
					.build();
			this.trip.addLast(t);

			ViajeSubte.checkConsistenciaViaje(this.trip);
			return new ViajeSubte(this);
		}
	}
	
	
	
	public static class BackwardsBuilder implements Viaje.BackwardsBuilder {

		private PositionList<Viaje.Tramo> trip;
		
		@Override
		public BackwardsMiddleStartBuilder setDestino(Estacion destino, Linea linea) {
			
			this.trip = new ListaDoblementeEnlazada<Viaje.Tramo>();
			Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.LlegadaADestinoSubte.getBuilder()
					.setDestino(destino)
					.setLineaDestino(linea)
					.build();
			this.trip.addFirst(t);
			
			return new BackwardsMiddleStartBuilder(this);
		}
		
	}
	
	public static class BackwardsMiddleStartBuilder implements Viaje.BackwardsMiddleStartBuilder {

		private PositionList<Viaje.Tramo> trip;
		
		private BackwardsMiddleStartBuilder(BackwardsBuilder fb) {
			this.trip = fb.trip;
		}
		
		@Override
		public Viaje.BackwardsMiddleStartBuilder agregarCombinacion(Linea lineaInicial) {
			try {
				Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.CombinacionSubte.getBuilder()
						.setEstacionDeCombinacion(trip.first().element().getEstacionInicial())
						.setLineaInicial(lineaInicial)
						.setLineaFinal(trip.first().element().getLineaInicial())
						.build();
				this.trip.addFirst(t);
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public Viaje.BackwardsMiddleStartBuilder agregarDireccion(
				Estacion origen, Estacion cabeceraDireccion, Linea linea) {
			try {
				Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.TramoIntraLineaSubte.getBuilder()
								.setEstacionInicialTramo(origen)
								.setEstacionFinalTramo(trip.first().element().getEstacionInicial())
								.setCabeceraDireccion(cabeceraDireccion)
								.setLinea(linea)
								.build();
				this.trip.addFirst(t);
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public Viaje setOrigenAndBuild(Estacion origen, Linea linea) {
			
			Viaje.Tramo t = ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.SalidaDeOrigenSubte.getBuilder()
					.setOrigen(origen)
					.setLineaOrigen(linea)
					.build();
			this.trip.addFirst(t);
			
			ViajeSubte.checkConsistenciaViaje(this.trip);
			return new ViajeSubte(this);
		}
	}
}
