package ar.edu.uns.cs.ed.proyectos.subtes.entities;

public interface Estacion {

	public String getNombre();
	
	public interface Builder {
		
		public Estacion build(String nombre);
		
	}
	
}
