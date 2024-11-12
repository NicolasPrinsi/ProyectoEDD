package ar.edu.uns.cs.ed.proyectos.subtes.routing;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.EstacionSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.LineaSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.Combinacion;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.LlegadaADestino;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.SalidaDeOrigen;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.Tramo;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.Viaje.TramoIntraLinea;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.proyecto.Proyecto_LU135402_LU118903;
import ar.edu.uns.cs.ed.proyectos.subtes.routing.util.Par;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.exceptions.InvalidKeyException;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.proyectos.subtes.tdas.tdamapeo.MapeoConHash;

public class RouterSubteTest {
	
	Router routerToTest, routerWithConnection, routerSimple, routerCABA;
	Linea dummyLine1, dummyLine2;
	String nombreRouter, nombreLinea1, nombreLinea2;
	Class proyectoClass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		/* TODO Proyecto_Tarea_1 Verificar el correcto nombre de la clase
		 * del proyecto (si se utilizó Refactor > Rename en eclipse, ya
		 * debería haberse ajustado automáticamente). 
		 */
		proyectoClass = Proyecto_LU135402_LU118903.class;
		
		
		nombreLinea1 = "Linea Dummy 1";
		nombreLinea2 = "Linea Dummy 2";
		nombreRouter = "Router de 1 dummy line";
		
		dummyLine1 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 1"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 2"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 3"))
				.build(nombreLinea1);
		
		dummyLine2 = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 4"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 2"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estacion 5"))
				.build(nombreLinea2);
				
		routerToTest = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.build(nombreRouter, proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
		
		routerWithConnection = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.agregarLinea(dummyLine2)
				.build(nombreRouter, proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
		
		routerSimple = SimpleSubteNetworkRouterBuilder.getBuilder()
				.buildSimpleSubteNetwork(proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
		
		routerCABA = CABARouterBuilder.getBuilder()
				.buildCABA(proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNombreRouter() {
		assertEquals(this.nombreRouter, routerToTest.getNombre());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNombreNull() {
		routerToTest = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.build(null, proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testProyectoNull() {
		routerToTest = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.build(nombreRouter, null)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLineasInsuficientes() {
		routerToTest = RouterSubte.getBuilder()
				.build(nombreRouter, proyectoClass)
				.precomputarLineasDeEstaciones()
				.precomputarCombinaciones()
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNoSePrecomputaLineasDeEstaciones() {
		routerToTest = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.build(nombreRouter, proyectoClass)
				.precomputarCombinaciones()
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNoSePrecomputaCombinaciones() {
		routerToTest = RouterSubte.getBuilder()
				.agregarLinea(dummyLine1)
				.build(nombreRouter, proyectoClass)
				.precomputarLineasDeEstaciones()
				.build();
	}
	
	
	@Test
	public void testGetAllEstaciones() {
		Map<Estacion,Integer> m = new MapeoConHash<Estacion,Integer>();
		try {
			for (Estacion estacion: routerWithConnection.getAllEstaciones()) {
					assertTrue(m.get(estacion)==null); 
					m.put(estacion, 1);
			}
			assertEquals(5, m.size());
			assertEquals((Integer) 1, m.remove(EstacionSubte.getBuilder().build("Estacion 1")));
			assertEquals((Integer) 1, m.remove(EstacionSubte.getBuilder().build("Estacion 2")));
			assertEquals((Integer) 1, m.remove(EstacionSubte.getBuilder().build("Estacion 3")));
			assertEquals((Integer) 1, m.remove(EstacionSubte.getBuilder().build("Estacion 4")));
			assertEquals((Integer) 1, m.remove(EstacionSubte.getBuilder().build("Estacion 5")));
			assertEquals(0, m.size());
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			fail("Unexpected InvalidKeyException");
		}
	}
	
	
	@Test
	public void testGetAllLineas() {
		Iterator<Linea> itLine;
		
		itLine = routerToTest.getAllLineas().iterator();
		assertTrue(itLine.hasNext());
		assertEquals(dummyLine1, itLine.next());
		assertFalse(itLine.hasNext());
		
		itLine = routerWithConnection.getAllLineas().iterator();
		assertTrue(itLine.hasNext());
		Linea l = itLine.next();
		assertTrue(itLine.hasNext());
		if (l.equals(dummyLine1)) {
			assertEquals(dummyLine2, itLine.next());
		} else if (l.equals(dummyLine2)) {
			assertEquals(dummyLine2, itLine.next());
		} else {
			fail("No se encontraron las líneas esperadas para la Estacion 2");
		}
		assertFalse(itLine.hasNext());
	}
	
	
	@Test
	public void testGetLineas() {
		Iterator<Linea> itLine;
		
		itLine = routerWithConnection.getLineas(EstacionSubte.getBuilder().build("Estacion 1")).iterator();
		assertTrue(itLine.hasNext());
		assertEquals(dummyLine1, itLine.next());
		assertFalse(itLine.hasNext());
		
		itLine = routerWithConnection.getLineas(EstacionSubte.getBuilder().build("Estacion 2")).iterator();
		assertTrue(itLine.hasNext());
		Linea l = itLine.next();
		assertTrue(itLine.hasNext());
		if (l.equals(dummyLine1)) {
			assertEquals(dummyLine2, itLine.next());
		} else if (l.equals(dummyLine2)) {
			assertEquals(dummyLine2, itLine.next());
		} else {
			fail("No se encontraron las líneas esperadas para la Estacion 2");
		}
		assertFalse(itLine.hasNext());
		
		itLine = routerWithConnection.getLineas(EstacionSubte.getBuilder().build("Estacion 3")).iterator();
		assertTrue(itLine.hasNext());
		assertEquals(dummyLine1, itLine.next());
		assertFalse(itLine.hasNext());
		
		itLine = routerWithConnection.getLineas(EstacionSubte.getBuilder().build("Estacion 4")).iterator();
		assertTrue(itLine.hasNext());
		assertEquals(dummyLine2, itLine.next());
		assertFalse(itLine.hasNext());
		
		itLine = routerWithConnection.getLineas(EstacionSubte.getBuilder().build("Estacion 5")).iterator();
		assertTrue(itLine.hasNext());
		assertEquals(dummyLine2, itLine.next());
		assertFalse(itLine.hasNext());
	}
	
	@Test
	public void testGetCombinaciones() {
		Iterator<Par<Linea,Estacion>> itComb;
		
		itComb = routerWithConnection.getCombinaciones(dummyLine1).iterator();
		assertTrue(itComb.hasNext());
		assertEquals(new Par<Linea,Estacion>(dummyLine2,EstacionSubte.getBuilder().build("Estacion 2")), itComb.next());
		assertFalse(itComb.hasNext());
		
		itComb = routerWithConnection.getCombinaciones(dummyLine2).iterator();
		assertTrue(itComb.hasNext());
		assertEquals(new Par<Linea,Estacion>(dummyLine1,EstacionSubte.getBuilder().build("Estacion 2")), itComb.next());
		assertFalse(itComb.hasNext());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testViajarMismoOrigenYDestinoInSimpleSubteNetwork() {
		routerSimple.viajar(EstacionSubte.getBuilder().build("E3"),
							EstacionSubte.getBuilder().build("E3"));
	}
	
	@Test
	public void testViajarAtoBIntraLinea() {
		Viaje v = routerWithConnection.viajar(
						EstacionSubte.getBuilder().build("Estacion 1"),
						EstacionSubte.getBuilder().build("Estacion 2"));
	
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		it = v.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionInicial() );
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(EstacionSubte.getBuilder().build("Estacion 3"), ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		routerSimple.validarViaje(v);
	}
	
	
	@Test
	public void testViajarAtoBIntraLineaInvertido() {
		Viaje v = routerWithConnection.viajar(
						EstacionSubte.getBuilder().build("Estacion 2"),
						EstacionSubte.getBuilder().build("Estacion 1"));
	
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		it = v.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionInicial() );
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		routerSimple.validarViaje(v);
	}
	
	
	@Test
	public void testViajarAtoBConCombinaciones() {
		Viaje v = routerWithConnection.viajar(
						EstacionSubte.getBuilder().build("Estacion 1"),
						EstacionSubte.getBuilder().build("Estacion 4"));
	
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		it = v.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionInicial() );
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 1"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(EstacionSubte.getBuilder().build("Estacion 3"), ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine1, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(dummyLine1, currentTramo.getLineaInicial());
		assertEquals(dummyLine2, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 2"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 4"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(EstacionSubte.getBuilder().build("Estacion 4"), ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(dummyLine2, currentTramo.getLineaInicial());
		assertEquals(dummyLine2, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(EstacionSubte.getBuilder().build("Estacion 4"), currentTramo.getEstacionInicial());
		assertEquals(EstacionSubte.getBuilder().build("Estacion 4"), currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(dummyLine2, currentTramo.getLineaInicial());
		assertEquals(dummyLine2, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		routerSimple.validarViaje(v);
	}
	
	@Test
	public void testViajarAtoBIntraLineaInSimpleSubteNetwork() {
		Viaje v = routerSimple.viajar(
						EstacionSubte.getBuilder().build("E3"),
						EstacionSubte.getBuilder().build("E4"));
		
		routerSimple.validarViaje(v);
	}
	
	@Test
	public void testViajarAtoBConCombinacionesInSimpleSubteNetwork() {
		Viaje v = routerSimple.viajar(
					EstacionSubte.getBuilder().build("E1"),
					EstacionSubte.getBuilder().build("E13"));

		routerSimple.validarViaje(v);
	}
	
	@Test
	public void testViajarAtoBIntraLineaInCABA() {
		Viaje v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Carlos Pelegrini | Diagonal Norte | 9 de Julio"),
				EstacionSubte.getBuilder().build("F. Lacroze"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 1 CABA Intra Línea B (hacia cabecera final): de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);
		
		
		v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Independencia"),
				EstacionSubte.getBuilder().build("Lavalle"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 2 CABA Intra Línea C (hacia cabecera inicial): de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);
	}
	
	@Test
	public void testViajarAtoBConCombinacionesInCABA() {
				
		Viaje v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Av. La Plata"),
				EstacionSubte.getBuilder().build("Palermo"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 1 CABA con combinación: de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);
		
		
		v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Venezuela"),
				EstacionSubte.getBuilder().build("Moreno"));
		
		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 2 CABA con múltiples rutas: de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);

		
		v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Retiro"),
				EstacionSubte.getBuilder().build("Independencia"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 3 CABA con múltiples rutas: de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);
		
		
		v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("Olleros"),
				EstacionSubte.getBuilder().build("Malabia - O. Pugliese"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 4 CABA con múltiples rutas: de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);
	}
	
	@Test
	public void testViajarAtoBConCombinacionEnDestinoInCABA() {
		Viaje v = routerCABA.viajar(
				EstacionSubte.getBuilder().build("F. Lacroze"),
				EstacionSubte.getBuilder().build("Retiro"));

		routerSimple.validarViaje(v);
		
		System.out.println("Viaje 1 CABA con potencial combinacion en destino: de '"+v.getOrigen()+"' a '"+v.getDestino()+"'.");
		System.out.println(v);

	}
}
