package ar.edu.uns.cs.ed.proyectos.subtes.routing;


import ar.edu.uns.cs.ed.proyectos.subtes.entities.EstacionSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.LineaSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.Builder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.RouterSubte.PrecomputationBuilder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto;

public class CABARouterBuilder extends RouterSubte.Builder {
	
	public static CABARouterBuilder getBuilder() {
		return new CABARouterBuilder();
	}

	public PrecomputationBuilder buildCABA(Class<Proyecto> proyectoClass) {
		
		Linea a = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Plaza de Mayo - Casa Rosada"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Catedral | Perú | Bolívar"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Piedras"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Lima | Av. de Mayo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Sáenz Peña"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Congreso Pdte. Raúl R. Alfonsín"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pasco"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Alberti"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Plaza Miserere | Once - 30 de Diciembre"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Loria"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Castro Barros"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Río de Janeiro"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Acoyte"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Primera Junta"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Puan"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Carabobo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("San José de Flores"))
				.agregarEstacion(EstacionSubte.getBuilder().build("San Pedrito"))
				.build("A");
		
		Linea b = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Leandro N. Alem | Correo Central"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Florida"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Carlos Pelegrini | Diagonal Norte | 9 de Julio"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Uruguay"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Callao - Maestro A. Bravo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pasteur - AMIA"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pueyrredón | Corrientes"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Carlos Gardel"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Medrano - Almagro"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Angel Gallardo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Malabia - O. Pugliese"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Dorrego"))
				.agregarEstacion(EstacionSubte.getBuilder().build("F. Lacroze"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Tronador - Villa Ortúzar"))
				.agregarEstacion(EstacionSubte.getBuilder().build("De los Incas - Parque Chas"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Echeverría"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Juan Manuel De Rosas - Villa Urquiza"))
				.build("B");
		
		Linea c = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Retiro"))
				.agregarEstacion(EstacionSubte.getBuilder().build("General San Martín"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Lavalle"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Carlos Pelegrini | Diagonal Norte | 9 de Julio"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Lima | Av. de Mayo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Moreno"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Independencia"))
				.agregarEstacion(EstacionSubte.getBuilder().build("San Juan"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Constitución"))
				.build("C");
		
		Linea d = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Catedral | Perú | Bolívar"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Carlos Pelegrini | Diagonal Norte | 9 de Julio"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Tribunales - Teatro Colón"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Callao"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Facultad de Medicina"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pueyrredón | Santa Fe - Carlos Jáuregui"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Agüero"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Bulnes"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Scalabrini Ortiz"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Plaza Italia"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Palermo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Mtro. Carranza - Miguel Abuelo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Olleros"))
				.agregarEstacion(EstacionSubte.getBuilder().build("José Hernández"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Juramento"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Congreso de Tucumán"))
				.build("D");
		
		Linea e = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Retiro"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Catalinas"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Leandro N. Alem | Correo Central"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Catedral | Perú | Bolívar"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Belgrano"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Independencia"))
				.agregarEstacion(EstacionSubte.getBuilder().build("San José"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Entre Ríos - Rodolfo Walsh"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pichincha"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Jujuy | Humberto 1o"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Gral. Urquiza"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Boedo"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Av. La Plata"))
				.agregarEstacion(EstacionSubte.getBuilder().build("José María Moreno"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Emilio Mitre"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Medalla Milagrosa"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Varela"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Plaza de los Virreyes - Eva Perón"))
				.build("E");
		
		Linea h = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Facultad de Derecho - Julieta Lanteri"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Las Heras"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pueyrredón | Santa Fe - Carlos Jáuregui"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Córdoba"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Pueyrredón | Corrientes"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Plaza Miserere | Once - 30 de Diciembre"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Venezuela"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Jujuy | Humberto 1o"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Inclán - Mezquita Al Ahmad"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Caseros"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Parque Patricios"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Hospitales"))
				.build("H");
		
		this.agregarLinea(a)
			.agregarLinea(b)
			.agregarLinea(c)
			.agregarLinea(d)
			.agregarLinea(e)
			.agregarLinea(h);

		return super.build("Subtes de CABA", proyectoClass);
	}
}
