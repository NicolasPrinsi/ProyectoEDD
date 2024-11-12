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
import ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos.TramoIntraLineaSubte;

public class ViajeTest {
	
	Viaje viajeIntraLinea, viajeConCombinacion;
	Linea lineaA, lineaB, lineaC;
	Estacion[] estacionesSimples; 
	Estacion estacionCombinacionAB, estacionCombinacionBC;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		estacionesSimples = new Estacion[9];
		for(int i=1; i<=8; i++) {
			estacionesSimples[i] = EstacionSubte.getBuilder().build("Estación "+i);
		}
		estacionCombinacionAB = EstacionSubte.getBuilder().build("Combinación Líneas A y B");
		estacionCombinacionBC = EstacionSubte.getBuilder().build("Combinación Líneas B y C");
		
		lineaA = LineaSubte.getBuilder()
				.agregarEstacion(estacionesSimples[1])
				.agregarEstacion(estacionesSimples[2])
				.agregarEstacion(estacionCombinacionAB)
				.agregarEstacion(estacionesSimples[3])
				.build("A");
		
		lineaB = LineaSubte.getBuilder()
				.agregarEstacion(estacionesSimples[4])
				.agregarEstacion(estacionCombinacionAB)
				.agregarEstacion(estacionCombinacionBC)
				.agregarEstacion(estacionesSimples[5])
				.build("B");
		
		lineaC = LineaSubte.getBuilder()
				.agregarEstacion(estacionesSimples[6])
				.agregarEstacion(estacionCombinacionBC)
				.agregarEstacion(estacionesSimples[7])
				.agregarEstacion(estacionesSimples[8])
				.build("C");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrigenParaViajeForwards() {
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[1], lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		assertEquals(estacionesSimples[1],viajeIntraLinea.getOrigen());
	}
	
	@Test
	public void testDestinoParaViajeForwards() {
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[1], lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		assertEquals(estacionesSimples[2],viajeIntraLinea.getDestino());
	}
	
	@Test
	public void testOrigenParaViajeBackwards() {
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionesSimples[1], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[1], lineaA);
		
		assertEquals(estacionesSimples[1],viajeIntraLinea.getOrigen());
	}
	
	@Test
	public void testDestinoParaViajeBackwards() {
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionesSimples[1], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[1], lineaA);
		
		assertEquals(estacionesSimples[2],viajeIntraLinea.getDestino());
	}
	
	
	@Test
	public void testBuildViajeIntraLineaForwards() {
		
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[1], lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial() );
		assertEquals(estacionesSimples[1], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionesSimples[3], estacionesSimples[3], lineaA)
				.setDestinoAndBuild(estacionesSimples[3], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[1], lineaA)
				.agregarDireccion(estacionesSimples[3], estacionesSimples[3], lineaA)
				.setDestinoAndBuild(estacionesSimples[3], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[1], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[3], lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[1], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[1], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testBuildViajeIntraLineaBackwards() {
		
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionesSimples[1], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[1], lineaA);
				
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[1], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[3], lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[2], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[3], lineaA)
				.agregarDireccion(estacionesSimples[1], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[1], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[1], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[1], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
		
		
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionesSimples[3], estacionesSimples[1], lineaA)
				.setOrigenAndBuild(estacionesSimples[3], lineaA);
		
		it = viajeIntraLinea.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[3], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[3], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[1], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
	}
	
	
	@Test
	public void testBuildViajeEnCombinacionForwards() {
		
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		viajeConCombinacion = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[3], lineaA)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaC)
				.agregarDireccion(estacionesSimples[7], estacionesSimples[8], lineaC)
				.setDestinoAndBuild(estacionesSimples[7], lineaC);
		
		it = viajeConCombinacion.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[5], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[8], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());		
		
		
		
		viajeConCombinacion = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[7], lineaC)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[6], lineaC)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[4], lineaB)
				.agregarCombinacion(lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[1], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		it = viajeConCombinacion.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[6], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[4], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[1], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
	}
	
	
	@Test
	public void testBuildViajeEnCombinacionBackwards() {
		
		Tramo currentTramo;
		Iterator<Tramo> it;
		
		
		viajeConCombinacion = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[7], lineaC)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[8], lineaC)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[2], lineaA);
				
		it = viajeConCombinacion.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[3], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[5], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[8], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());		
		
		
		
		viajeConCombinacion = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[1], lineaA)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[4], lineaB)
				.agregarCombinacion(lineaC)
				.agregarDireccion(estacionesSimples[7], estacionesSimples[6], lineaC)
				.setOrigenAndBuild(estacionesSimples[7], lineaC);
		
		it = viajeConCombinacion.obtenerDetalleViaje().iterator();
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[7], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof SalidaDeOrigen);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[7], currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[6], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaC, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaC, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionBC, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[4], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaB, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof Combinacion);
		assertEquals(lineaB, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionCombinacionAB, currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof TramoIntraLinea);
		assertEquals(estacionesSimples[1], ((TramoIntraLinea) currentTramo).getCabeceraDireccion());
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertTrue(it.hasNext());
		currentTramo = it.next();
		assertEquals(estacionesSimples[2], currentTramo.getEstacionInicial());
		assertEquals(estacionesSimples[2], currentTramo.getEstacionFinal());
		assertTrue(currentTramo instanceof LlegadaADestino);
		assertEquals(lineaA, currentTramo.getLineaInicial());
		assertEquals(lineaA, currentTramo.getLineaFinal());
		assertFalse(it.hasNext());
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testViajeConMenosDe3TramosForwards() {
		viajeIntraLinea = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[1], lineaA)
				.setDestinoAndBuild(estacionesSimples[1], lineaA);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testViajeConMenosDe3TramosBackwards() {
		viajeIntraLinea = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.setOrigenAndBuild(estacionesSimples[2], lineaA);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testViajeConEstacionesDiscontinuasEnDestinoForwards() {
		viajeConCombinacion = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[3], lineaA)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaC)
				.agregarDireccion(estacionesSimples[7], estacionesSimples[8], lineaC)
				.setDestinoAndBuild(estacionesSimples[8], lineaC);
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void testViajeConEstacionesDiscontinuasEnOrigenBackwards() {
		viajeConCombinacion = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[7], lineaC)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[8], lineaC)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[1], lineaA);		
	}
	
	@Test
	public void testPresentacionDeViajeForwards() {
		viajeConCombinacion = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[3], lineaA)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaC)
				.agregarDireccion(estacionesSimples[7], estacionesSimples[8], lineaC)
				.setDestinoAndBuild(estacionesSimples[7], lineaC);
		
		System.out.println("Viaje 1 - ForwardsBuild");
		for (Tramo t: viajeConCombinacion.obtenerDetalleViaje()) {
			System.out.println(t);
		}
		System.out.println();
		
		viajeConCombinacion = ViajeSubte.getForwardsBuilder()
				.setOrigen(estacionesSimples[7], lineaC)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[6], lineaC)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[4], lineaB)
				.agregarCombinacion(lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[1], lineaA)
				.setDestinoAndBuild(estacionesSimples[2], lineaA);
		
		System.out.println("Viaje 2 - ForwardsBuild");
		for (Tramo t: viajeConCombinacion.obtenerDetalleViaje()) {
			System.out.println(t);
		}
		System.out.println();
	}
	
	@Test
	public void testPresentacionDeViajeBackwards() {
		viajeConCombinacion = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[7], lineaC)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[8], lineaC)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[5], lineaB)
				.agregarCombinacion(lineaA)
				.agregarDireccion(estacionesSimples[2], estacionesSimples[3], lineaA)
				.setOrigenAndBuild(estacionesSimples[2], lineaA);
		
		System.out.println("Viaje 1 - BackwardsBuild");
		for (Tramo t: viajeConCombinacion.obtenerDetalleViaje()) {
			System.out.println(t);
		}
		System.out.println();
		
		
		viajeConCombinacion = ViajeSubte.getBackwardsBuilder()
				.setDestino(estacionesSimples[2], lineaA)
				.agregarDireccion(estacionCombinacionAB, estacionesSimples[1], lineaA)
				.agregarCombinacion(lineaB)
				.agregarDireccion(estacionCombinacionBC, estacionesSimples[4], lineaB)
				.agregarCombinacion(lineaC)
				.agregarDireccion(estacionesSimples[7], estacionesSimples[6], lineaC)
				.setOrigenAndBuild(estacionesSimples[7], lineaC);
		
		System.out.println("Viaje 2 - BackwardsBuild");
		for (Tramo t: viajeConCombinacion.obtenerDetalleViaje()) {
			System.out.println(t);
		}
		System.out.println();
	}
	
}
