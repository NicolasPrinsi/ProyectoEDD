package ar.edu.uns.cs.ed.proyectos.subtes.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EstacionSubteTest {
	
	Estacion estacionToTest1, estacionToTest2, estacionToTest3;
	String nombre1, nombre2;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		nombre1 = "Estacion N1";
		nombre2 = "Estacion N2";
		estacionToTest1 = EstacionSubte.getBuilder().build(nombre1);
		estacionToTest2 = EstacionSubte.getBuilder().build(nombre2);
		estacionToTest3 = EstacionSubte.getBuilder().build(nombre1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNombreEstacion() {
		assertEquals(nombre1, estacionToTest1.getNombre());
		assertEquals(nombre2, estacionToTest2.getNombre());
		assertEquals(nombre1, estacionToTest3.getNombre());
	}
	
	@Test
	public void testIgualdadEstacionPorNombre() {
		assertTrue("Las estaciones con igual nombre deberían ser iguales ("+estacionToTest1.getNombre()+" != "+estacionToTest3.getNombre()+").",
				estacionToTest1.equals(estacionToTest3));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNombreNull() {
		estacionToTest1 = EstacionSubte.getBuilder().build(null);
	}
}
