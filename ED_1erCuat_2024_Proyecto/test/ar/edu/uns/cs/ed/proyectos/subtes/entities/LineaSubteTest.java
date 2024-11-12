package ar.edu.uns.cs.ed.proyectos.subtes.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineaSubteTest {
	
	Linea lineaA, lineaB, lineaC, lineaU;
	String nombreA, nombreB, nombreC, nombreU;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		nombreA = "Línea A";
		nombreB = "Línea B";
		nombreC = "Línea C";
		nombreU = "Línea Unitaria (Error!)";
		
		
		lineaA = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 1"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 2"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 3"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 4"))
				.build(this.nombreA);
				
		lineaB = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 8"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 7"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 3"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 6"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 5"))
				.build(this.nombreB);
		
		lineaC = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 1"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 9"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 4"))
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación 5"))
				.build(this.nombreC);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNombreLinea() {
		assertEquals(this.nombreA, lineaA.getNombre());
		assertEquals(this.nombreB, lineaB.getNombre());
		assertEquals(this.nombreC, lineaC.getNombre());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNombreNull() {
		lineaA = LineaSubte.getBuilder().build(null);
	}
	
	@Test
	public void testCabecerasLinea() {
		assertTrue(lineaA.getCabeceraInicial().equals(EstacionSubte.getBuilder().build("Estación 1")));
		assertTrue(lineaA.getCabeceraFinal().equals(EstacionSubte.getBuilder().build("Estación 4")));
		
		assertTrue(lineaB.getCabeceraInicial().equals(EstacionSubte.getBuilder().build("Estación 8")));
		assertTrue(lineaB.getCabeceraFinal().equals(EstacionSubte.getBuilder().build("Estación 5")));
		
		assertTrue(lineaC.getCabeceraInicial().equals(EstacionSubte.getBuilder().build("Estación 1")));
		assertTrue(lineaC.getCabeceraFinal().equals(EstacionSubte.getBuilder().build("Estación 5")));
	}
	
	@Test
	public void testViajesHaciaCabeceraFinal() {
		
		Estacion estacionActual = lineaA.getCabeceraInicial();
		assertTrue(estacionActual.getNombre().equals("Estación 1"));
		estacionActual = lineaA.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 2"));
		estacionActual = lineaA.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 3"));
		estacionActual = lineaA.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 4"));
		assertEquals(estacionActual, lineaA.getCabeceraFinal());
		
		
		estacionActual = lineaB.getCabeceraInicial();
		assertTrue(estacionActual.getNombre().equals("Estación 8"));
		estacionActual = lineaB.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 7"));
		estacionActual = lineaB.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 3"));
		estacionActual = lineaB.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 6"));
		estacionActual = lineaB.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 5"));
		assertEquals(estacionActual, lineaB.getCabeceraFinal());
		
		
		estacionActual = lineaC.getCabeceraInicial();
		assertTrue(estacionActual.getNombre().equals("Estación 1"));
		estacionActual = lineaC.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 9"));
		estacionActual = lineaC.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 4"));
		estacionActual = lineaC.viajarHaciaCabeceraFinal(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 5"));
		assertEquals(estacionActual, lineaC.getCabeceraFinal());
	}
	
	
	@Test
	public void testViajesHaciaCabeceraInicial() {

		Estacion estacionActual = lineaA.getCabeceraFinal();
		assertTrue(estacionActual.getNombre().equals("Estación 4"));
		estacionActual = lineaA.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 3"));
		estacionActual = lineaA.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 2"));
		estacionActual = lineaA.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 1"));
		assertEquals(estacionActual, lineaA.getCabeceraInicial());
		
		
		estacionActual = lineaB.getCabeceraFinal();
		assertTrue(estacionActual.getNombre().equals("Estación 5"));
		estacionActual = lineaB.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 6"));
		estacionActual = lineaB.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 3"));
		estacionActual = lineaB.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 7"));
		estacionActual = lineaB.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 8"));
		assertEquals(estacionActual, lineaB.getCabeceraInicial());
		
		
		estacionActual = lineaC.getCabeceraFinal();
		assertTrue(estacionActual.getNombre().equals("Estación 5"));
		estacionActual = lineaC.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 4"));
		estacionActual = lineaC.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 9"));
		estacionActual = lineaC.viajarHaciaCabeceraInicial(estacionActual);
		assertTrue(estacionActual.getNombre().equals("Estación 1"));
		assertEquals(estacionActual, lineaC.getCabeceraInicial());
	}
	
	
	@Test
	public void testViajesMasAllaDeLasCabeceras() {
		assertTrue(lineaA.viajarHaciaCabeceraFinal(lineaA.getCabeceraFinal()) == null);
		assertTrue(lineaB.viajarHaciaCabeceraFinal(lineaB.getCabeceraFinal()) == null);
		assertTrue(lineaC.viajarHaciaCabeceraFinal(lineaC.getCabeceraFinal()) == null);
		
		assertTrue(lineaA.viajarHaciaCabeceraInicial(lineaA.getCabeceraInicial()) == null);
		assertTrue(lineaB.viajarHaciaCabeceraInicial(lineaB.getCabeceraInicial()) == null);
		assertTrue(lineaC.viajarHaciaCabeceraInicial(lineaC.getCabeceraInicial()) == null);
	}
	
	
	@Test
	public void testViajesDesdeEstacionFueraDeLinea() {
		
		assertTrue(lineaA.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
		assertTrue(lineaB.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
		assertTrue(lineaC.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
		
		assertTrue(lineaA.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
		assertTrue(lineaB.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
		assertTrue(lineaC.viajarHaciaCabeceraInicial(EstacionSubte.getBuilder().build("Estación X")) == null);
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testLineaUnitariaInvalida() {
		lineaU = LineaSubte.getBuilder()
				.agregarEstacion(EstacionSubte.getBuilder().build("Estación U"))
				.build(this.nombreU);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLineaSinEstacionesInvalida() {
		lineaU = LineaSubte.getBuilder().build(this.nombreU);
	}
	
	@Test
	public void testEstacionesEnComun() {
		Estacion estacionActualA, estacionActualB, estacionActualC;
		
		estacionActualA = lineaA.getCabeceraInicial();
		estacionActualB = lineaB.getCabeceraInicial();
		while (estacionActualA!=lineaA.getCabeceraFinal()) {
			while (estacionActualB!=lineaB.getCabeceraFinal()) {
				if (estacionActualA.equals(estacionActualB)) {
					assertTrue(estacionActualA.equals(EstacionSubte.getBuilder().build("Estación 3")));
				}
				estacionActualB = lineaB.viajarHaciaCabeceraFinal(estacionActualB);
			}
			estacionActualA = lineaA.viajarHaciaCabeceraFinal(estacionActualA);
		}
		
		estacionActualC = lineaC.getCabeceraInicial();
		estacionActualB = lineaB.getCabeceraInicial();
		while (estacionActualC!=lineaC.getCabeceraFinal()) {
			while (estacionActualB!=lineaB.getCabeceraFinal()) {
				if (estacionActualC.equals(estacionActualB)) {
					assertTrue(estacionActualC.equals(EstacionSubte.getBuilder().build("Estación 5")));
				}
				estacionActualB = lineaB.viajarHaciaCabeceraFinal(estacionActualB);
			}
			estacionActualC = lineaC.viajarHaciaCabeceraFinal(estacionActualC);
		}
			
			
		estacionActualC = lineaC.getCabeceraInicial();
		estacionActualA = lineaA.getCabeceraInicial();
		boolean firstMatch = true;
		while (estacionActualC!=lineaC.getCabeceraFinal()) {
			while (estacionActualA!=lineaA.getCabeceraFinal()) {
				if (estacionActualC.equals(estacionActualA)) {
					if (firstMatch) {
						assertTrue(estacionActualC.equals(EstacionSubte.getBuilder().build("Estación 1")));
						firstMatch = false;
					} else {
						assertTrue(estacionActualC.equals(EstacionSubte.getBuilder().build("Estación 4")));
					}
				}
				estacionActualA = lineaA.viajarHaciaCabeceraFinal(estacionActualA);
			}
			estacionActualC = lineaC.viajarHaciaCabeceraFinal(estacionActualC);
		}
	}	
}
