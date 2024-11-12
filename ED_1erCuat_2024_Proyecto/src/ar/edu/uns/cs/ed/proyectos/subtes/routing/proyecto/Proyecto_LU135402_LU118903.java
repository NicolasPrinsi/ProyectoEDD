package ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto;

import java.util.Iterator;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion; 
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Router;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.PrecomputationBuilder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.util.Par;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.InvalidKeyException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.DiccionarioHashAbierto;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.Entry;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.MapeoConHash;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.ViajeSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.ViajeSubte.ForwardsBuilder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.ViajeSubte.ForwardsMiddleEndBuilder;

/* TODO Proyecto_Tarea_1: Renombrar la clase ProyectoEjemplo siguiendo 
 * la siguiente convención: Proyecto_LU1_LU2
 * Usar Refactor > Rename en eclipse (de manera de que cambie también 
 * el nombre del archivo y las referencias existentes)
 */
public class Proyecto_LU135402_LU118903 implements Proyecto{
	
	// TODO Proyecto_Tarea_2: Declarar acá las estructuras de datos/TDAs necesarias
	Map<Estacion, PositionList<Linea>> LineasDeEstacion = new MapeoConHash<Estacion, PositionList<Linea>>();
	Dictionary<Linea, Par<Linea, Estacion>> combinaciones = new DiccionarioHashAbierto<Linea, Par<Linea, Estacion>>();
	@Override
	public String getComision() {
		// TODO Proyecto_Tarea_1: Ajustar los datos de la comisión
		String alumno1 = "Tonin Agustin Ezequiel";
		String lu1 = "135402";
		String alumno2 ="Prinsi Nicolas Ezequiel";
		String lu2="118903";
		return alumno1+" (LU: "+lu1+") "+alumno2+" (LU: "+lu2+")";
	}
	
	@Override
	public void precomputarLineasDeEstaciones(PrecomputationBuilder pb) {		
		/* TODO Proyecto_Tarea_3: Computar la(s) línea(s) a las que 
		 * pertenece cada estación y cargar la estructura elegida
		 */
		
		for(Linea l: pb.getAllLineas()) {
			Estacion pos = l.getCabeceraInicial();
			while(pos!=l.getCabeceraFinal()) {
				try {
					if(LineasDeEstacion.get(pos)==null) {
						LineasDeEstacion.put(pos, new ListaDoblementeEnlazada<Linea>());
					}
					LineasDeEstacion.get(pos).addLast(l);
					pos= l.viajarHaciaCabeceraFinal(pos);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				}
			}
			if(pos==l.getCabeceraFinal()) {
				try {
					if(LineasDeEstacion.get(pos)==null) {
						LineasDeEstacion.put(pos, new ListaDoblementeEnlazada<Linea>());
					}
					LineasDeEstacion.get(pos).addLast(l);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
			}
		}
		}
		
	}
	
	@Override
	public Iterable<Linea> getLineas(Estacion estacion) {
		
		/* TODO Proyecto_Tarea_3: A partir de la precomptación de Lineas 
		 * de Estaciones, implementar este método para que devuelva las 
		 * líneas a las que pertenece la estación dada
		 */
		Iterable<Linea> toreturn = new ListaDoblementeEnlazada<Linea>();
		
		try {
			toreturn = LineasDeEstacion.get(estacion);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return toreturn;
	}
	
	@Override
	public void precomputarCombinaciones(PrecomputationBuilder pb) {
		/* TODO Proyecto_Tarea_4: Computar las combinaciones entre 
		 * líneas y cargar la estructura elegida
		 */
		for(Linea linea: pb.getAllLineas()) {
			Estacion pos = linea.getCabeceraInicial();
			while(pos!=linea.getCabeceraFinal()) {
				try {
					if(LineasDeEstacion.get(pos)!=null&&LineasDeEstacion.get(pos).size()>1) {
						for(Linea c: LineasDeEstacion.get(pos)) {
							if(c!=linea) {
								combinaciones.insert(linea, new Par(c, pos));
							}
						}
					}
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				}
				pos = linea.viajarHaciaCabeceraFinal(pos);
			}
			if(pos==linea.getCabeceraFinal()) {
				try {
					if(LineasDeEstacion.get(pos)!=null&&LineasDeEstacion.get(pos).size()>1) {
						for(Linea c: LineasDeEstacion.get(pos)) {
							if(c!=linea) {
								combinaciones.insert(linea, new Par(c, pos));
							}
						}
					}
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public Iterable<Par<Linea,Estacion>> getCombinaciones(Linea linea) {
		/* TODO Proyecto_Tarea_4: A partir de la precomptación de Combinaciones, 
		 * implementar este método para que devuelva un iterable con las 
		 * combinaciones (i.e. pares de línea a combinar y estación de combinación) 
		 * de la línea dada
		 */
		PositionList<Par<Linea, Estacion>> toreturn = new ListaDoblementeEnlazada<Par<Linea, Estacion>>();
			try {
				for(Entry<Linea,Par<Linea,Estacion>> ent: combinaciones.findAll(linea)) {
					toreturn.addLast(ent.getValue());
				}
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		return toreturn;
	}
	
	@Override
	public Viaje viajar(Estacion origen, Estacion destino, Router router) {
		
		/* TODO Proyecto_Tarea_5: Resolver la tarea principal del proyecto: 
		 * el cálculo y creación de viajes entre las estaciones dadas. 
		 * Utilizando las estructuras de precomputación, y las estructuras 
		 * auxiliares que considere necesarias construya un Viaje que vaya
		 * de origen a destino utilizando la red de subtes cargada.
		 */
		Viaje toreturn = null;	
		Iterator<Linea> lorigen = router.getLineas(origen).iterator();
		Iterator<Linea> ldestino = router.getLineas(destino).iterator();
		
		Linea slinea=null;
		while(lorigen.hasNext()&&slinea==null) {
			Linea lo = lorigen.next();
			while(ldestino.hasNext()&&slinea==null){
				Linea ld = ldestino.next();
				if(lo.equals(ld))
					slinea=lo;
			}
		}
		if(slinea!=null) {
			ForwardsMiddleEndBuilder forward = ViajeSubte.getForwardsBuilder().setOrigen(origen, slinea);
			toreturn = ViajeSubte.getForwardsBuilder()
													.setOrigen(origen, slinea)
													.agregarDireccion(destino, this.pathfinder(origen, destino, slinea), slinea)
													.setDestinoAndBuild(destino, slinea);	
		}
	
		if(slinea==null) {
			Linea lo=null;
			Linea ld=null;
			Estacion c=null;
			Iterator<Linea> it = router.getLineas(origen).iterator();
			while(it.hasNext()&&ld==null) {
				Linea lact = it.next();
				Iterator<Par<Linea,Estacion>> co = router.getCombinaciones(lact).iterator();
				while(ld==null&&co.hasNext()) {
					Par<Linea, Estacion> combi = co.next();
					Estacion pos = combi.getFirst().getCabeceraInicial();
					while(pos!=null) {
						if(pos.equals(destino)) {
							lo=lact;
							ld=combi.getFirst();
							c=combi.getSecond();
						}
						pos=combi.getFirst().viajarHaciaCabeceraFinal(pos);
					}
				}
			}
			
			if(lo!=null&&ld!=null&&c!=null) {
				toreturn = ViajeSubte.getForwardsBuilder()
														.setOrigen(origen, lo)
														.agregarDireccion(c, this.pathfinder(origen, c, lo), lo)
														.agregarCombinacion(ld)
														.agregarDireccion(destino, this.pathfinder(c, destino, ld), ld)
														.setDestinoAndBuild(destino, ld);
				
			} else if(lo==null||ld==null||c==null){
				//3er caso
				Linea lo2=null;
				Linea lm=null;
				Linea ld2=null;
				Estacion c1=null;
				Estacion c2=null;			
				
				Iterator<Linea> lineaso = router.getLineas(origen).iterator();
				while(lineaso.hasNext()&&ld2==null) {
					Linea lori = lineaso.next();
					Iterator<Linea> lineasd = router.getLineas(destino).iterator();
					while(lineasd.hasNext()&&ld2==null) {
						Linea ldest=lineasd.next();
						Iterator<Par<Linea, Estacion>> combiorigen = router.getCombinaciones(lori).iterator();
						while(combiorigen.hasNext()&&ld2==null) {
							Par<Linea, Estacion> combia =combiorigen.next();
							Iterator<Par<Linea,Estacion>> combidestino = router.getCombinaciones(ldest).iterator();
							while(combidestino.hasNext()&&ld2==null) {
								Par<Linea, Estacion> combib = combidestino.next();
								if(combia.getFirst().equals(combib.getFirst())) {
									lo2=lori;
									c1=combia.getSecond();
									lm=combia.getFirst();
									c2=combib.getSecond();
									ld2=ldest;
								}
							}
						}
					}
				}
				if(lo2!=null&&lm!=null&&ld2!=null&&c1!=null&&c2!=null) {
					toreturn = ViajeSubte.getForwardsBuilder()
															.setOrigen(origen, lo2)
															.agregarDireccion(c1, this.pathfinder(origen, c1, lo2), lo2)
															.agregarCombinacion(lm)
															.agregarDireccion(c2, this.pathfinder(c1, c2, lm), lm).agregarCombinacion(ld2)
															.agregarDireccion(destino, this.pathfinder(c2, destino, ld2), ld2).setDestinoAndBuild(destino, ld2);	
				}
			//3er caso
			}
		}
		return toreturn;
	}

	//Encuentra la direccion
	private Estacion pathfinder(Estacion partida, Estacion llegada, Linea l) {
		Estacion toreturn =null;
		Estacion pos = l.getCabeceraInicial();
		while(!pos.equals(partida)&&!pos.equals(llegada)&&pos!=null) {
			pos=l.viajarHaciaCabeceraFinal(pos);
		}
		if(pos.equals(partida)) {
			toreturn = l.getCabeceraFinal();
		} else if (pos.equals(llegada)) {
			toreturn = l.getCabeceraInicial();
		}
		return toreturn;
	}
}
