package ar.edu.uns.cs.ed.proyectos.subtes.main;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.CABARouterBuilder;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Router;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto_LU135402_LU118903;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.InvalidKeyException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.DiccionarioHashAbierto;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdadiccionario.Entry;


public class Main {

	/* TODO Proyecto_Tarea_1 Verificar el correcto nombre de la clase
	 * del proyecto (si se utilizó Refactor > Rename en eclipse, ya
	 * debería haberse ajustado automáticamente). 
	 */
	private static Class proyectoClass = Proyecto_LU135402_LU118903.class;
	
	// Se inicializa el router de CABA inyectando el Proyecto
	private static Router routerCABA = CABARouterBuilder.getBuilder()
			.buildCABA(proyectoClass)
			.precomputarLineasDeEstaciones()
			.precomputarCombinaciones()
			.build();
	
	// Usamos un diccionario Hash para autocompletar las estaciones (todavía no vieron TRIEs =P)
	private static Dictionary<String,Estacion> dictDeAutocompletado = new DiccionarioHashAbierto<String,Estacion>();
	
	
	public static void main(String[] args) {

		mostrarBienvenida();
		cargarDiccionarioDeAutocompletado();

		try {
			while (true) {
				Estacion origen=leerEstacionConAutocompletado("Estación de origen");
				Estacion destino=leerEstacionConAutocompletado("Estación de destino");
				Viaje v;
				try {	
					v=routerCABA.viajar(origen, destino);
					routerCABA.validarViaje(v);
					mostrarViajeResultado(origen, destino, v);
				}
				catch (IllegalArgumentException | IllegalStateException e) {
					System.err.println("Error: hubo un error al calcular el viaje: "+ e.getMessage());
				}
			}
				
		} catch (ExitCLIAppException e) {
			System.out.println("Saliendo de la aplicación CLI ante la acción del usuario...");
		}
	}
	
	private static void mostrarBienvenida() {
		
		Proyecto proyecto;
		try {
			proyecto = (Proyecto) proyectoClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("Error: Problema al cargar el proyecto: "+proyectoClass+".");
		}
		
		System.out.println("Proyecto: Router de viajes en Subte (C.A.B.A.) - Estructuras de Datos");
		System.out.println("Departamento de Ciencias e Ingeniería de la Computación - UNS - 1er Cuat. de 2024");
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Comisión: "+((proyecto!=null)?proyecto.getComision():"[Error al obtener la comisión]"));
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println();
	}
	
	
	private static void cargarDiccionarioDeAutocompletado() {
		for (Estacion estacion: routerCABA.getAllEstaciones()) {
			String s = estacion.getNombre();
			for (int i=1; i<=s.length(); i++) {
				try {
					dictDeAutocompletado.insert(s.substring(0, i).toLowerCase(), estacion);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				}
			}			
			if (estacion.getNombre().split("[ |-]").length > 1) {
				boolean skipFirstSplit = true;
				for(String sp: estacion.getNombre().split("[ |-]")) {
					if (skipFirstSplit) {
						skipFirstSplit = false;
					} else {
						for (int i=1; i<=sp.length(); i++) {
							try {
								dictDeAutocompletado.insert(sp.substring(0, i).toLowerCase(), estacion);
							} catch (InvalidKeyException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	private static Estacion leerEstacionConAutocompletado(String prompt) {
		
		System.out.println(prompt+" (Autocompletar disponible - SPACE p/salir): ");
		
		// Se inicia la lectura por CLI
		try {
			//StringBuilder sb = new StringBuilder();
			Scanner input = new Scanner(System.in);
			input.useDelimiter("\\n");
			while (input.hasNext()) {
				String s = input.nextLine();
				if (Pattern.compile("[a-zA-Z0-9.áéíóúÁÉÍÓÚüÜñÑ]+").matcher(String.valueOf(s)).matches()) {
					// Se procesa un prefijo de autocompletado
					Iterator<Entry<String,Estacion>> it = dictDeAutocompletado.findAll(s.toLowerCase()).iterator();
					Entry<String,Estacion> entry=null;
					
					// Buscamos la coincidencia exacta
					while (it.hasNext()) {
						entry = it.next();
						String estName = entry.getValue().getNombre();
						if (estName.toLowerCase().equals(s.toLowerCase())) {
							// Se prioriza la primer coincidencia exacta por sobre otras de prefijo
							// Esto salva el caso de 'Callao' vs 'Callao - Maestro A. Bravo'... 
							return entry.getValue();
						}
					}
					
					// Se procesa un prefijo de autocompletado
					it = dictDeAutocompletado.findAll(s.toLowerCase()).iterator();
					entry=null;
					
					int i = 0;
					int charCount = 0;
					while (it.hasNext()) {
						entry = it.next();
						String estName = entry.getValue().getNombre();
						i++;
						if (i>=2) {
							// Hay más de 2 coincidencias: inserto el separador
							if (charCount+estName.length()>80) {
								System.out.println();
								charCount=0;
							} else {
								System.out.print("', '");
								charCount+=3;
							}
						} else {
							System.out.print("'");
						}
						//Muestro la estación match
						System.out.print(estName);
						charCount+=estName.length()+2;
					}
					if (i>0) System.out.println("'");
					
					if (i==1) {
						// Hay exactamente una coincidencia: devuelvo esa estación
						System.out.println();
						return entry.getValue();
					}					
				}
				else if (Pattern.compile("[ ].*").matcher(String.valueOf(s)).matches()) {
					throw new ExitCLIAppException();
				}
				else { 
					System.out.println(prompt+" (TAB / '`' Autocompletar - SPACE p/salir): ");
				}
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void mostrarViajeResultado(Estacion origen, Estacion destino, Viaje v) {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(">>> Viaje resultado para ir de '"+origen.getNombre()+"' a '"+destino.getNombre()+"'");
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(v);
		System.out.println("---------------------------------------------------------------------------------");
	}
}
