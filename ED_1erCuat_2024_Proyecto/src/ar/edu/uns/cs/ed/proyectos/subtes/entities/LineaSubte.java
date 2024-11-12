package ar.edu.uns.cs.ed.proyectos.subtes.entities;

import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.BoundaryViolationException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.EmptyListException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.InvalidPositionException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.Position;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.PositionList;

public class LineaSubte implements Linea {

	private String nombre;
	private PositionList<Estacion> listaEstaciones;
	
	
	private LineaSubte(Builder b) {
		this.nombre = b.nombre;
		this.listaEstaciones = b.listaEstaciones;
	}
	
	
	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public Estacion getCabeceraInicial() {
		Estacion cabeceraInicial = null;
		try {
			cabeceraInicial = listaEstaciones.first().element();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return cabeceraInicial;
	}

	@Override
	public Estacion getCabeceraFinal() {
		Estacion cabeceraFinal = null;
		try {
			cabeceraFinal = listaEstaciones.last().element();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return cabeceraFinal;
	}

	
	@Override
	public Estacion viajarHaciaCabeceraInicial(Estacion origen) {
		try {
			Position<Estacion> currentPos = this.listaEstaciones.last();
			
			while (currentPos != this.listaEstaciones.first()) {
				if (currentPos.element().equals(origen)) {
					return this.listaEstaciones.prev(currentPos).element();
				}
				currentPos = this.listaEstaciones.prev(currentPos);
			}
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public Estacion viajarHaciaCabeceraFinal(Estacion origen) {
		try {
			Position<Estacion> currentPos = this.listaEstaciones.first();
			
			while (currentPos != this.listaEstaciones.last()) {
				if (currentPos.element().equals(origen)) {
					return this.listaEstaciones.next(currentPos).element();
				}
				currentPos = this.listaEstaciones.next(currentPos);
			}
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	
	public static class Builder implements Linea.Builder {
		
		private String nombre;
		private PositionList<Estacion> listaEstaciones = new ListaDoblementeEnlazada<Estacion>();

		@Override
		public Linea.Builder agregarEstacion(Estacion estacion) {
			listaEstaciones.addLast(estacion);
			return this;
		}
		
		@Override
		public Linea build(String nombre) {
			if (nombre == null) throw new IllegalArgumentException("Error: El nombre de una línea no puede ser null.");
			if (listaEstaciones.size()<2) throw new IllegalStateException("Error: La línea debe tener al menos dos estaciones.");
			this.nombre = nombre;
			return new LineaSubte(this);
		}
	}
}
