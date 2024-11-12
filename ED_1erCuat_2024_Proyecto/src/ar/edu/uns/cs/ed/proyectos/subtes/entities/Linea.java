package ar.edu.uns.cs.ed.proyectos.subtes.entities;

public interface Linea {

	public String getNombre();
	public Estacion getCabeceraInicial();
	public Estacion getCabeceraFinal();
	public Estacion viajarHaciaCabeceraInicial(Estacion origen);
	public Estacion viajarHaciaCabeceraFinal(Estacion origen);
	
	public interface Builder {
		
		public Builder agregarEstacion(Estacion estacion);
		public Linea build(String nombre);

	}
}
