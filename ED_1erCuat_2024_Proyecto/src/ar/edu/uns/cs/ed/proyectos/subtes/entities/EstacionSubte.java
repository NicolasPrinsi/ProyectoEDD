package ar.edu.uns.cs.ed.proyectos.subtes.entities;

public class EstacionSubte implements Estacion {

	private String nombre;
	
	private EstacionSubte(EstacionSubte.Builder b) {
		this.nombre = b.nombre;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	public static Builder getBuilder() {
		return new Builder();
	}
	
	
	@Override
	public int hashCode() {
		return this.nombre.hashCode();
	}

	@Override
	public boolean equals(Object obj) { 
		return (obj instanceof Estacion) && this.nombre.equals(((Estacion)obj).getNombre());
	}

	@Override
	public String toString() {
		return "Estación: " + this.nombre;
	}

	public static class Builder implements Estacion.Builder {
		
		private String nombre;

		@Override
		public Estacion build(String nombre) {
			if (nombre == null) throw new IllegalArgumentException("Error: El nombre de una estación no puede ser null.");
			this.nombre = nombre;
			return new EstacionSubte(this);
		}
		
	}

}
