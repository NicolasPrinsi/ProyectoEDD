package ar.edu.uns.cs.ed.proyectos.subtes.routing.tramos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.uns.cs.ed.proyectos.subtes.entities.Estacion;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.EstacionSubte;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.Linea;
import ar.edu.uns.cs.ed.proyectos.subtes.entities.LineaSubte;

public class TramoTest {

	Linea lineaA, lineaB;
	Estacion estacion1, estacion2, estacion3, estacion4;
	
	SalidaDeOrigenSubte tramoSalidaDeOrigen;
	LlegadaADestinoSubte tramoLLegadaADestino;
	TramoIntraLineaSubte tramoIntraLinea;
	CombinacionSubte tramoCombinacion;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		estacion1 = EstacionSubte.getBuilder().build("Estacion 1");
		estacion2 = EstacionSubte.getBuilder().build("Estacion 2");
		estacion3 = EstacionSubte.getBuilder().build("Estacion 3");
		estacion4 = EstacionSubte.getBuilder().build("Estacion 4");
		
		lineaA = LineaSubte.getBuilder()
				.agregarEstacion(estacion1)
				.agregarEstacion(estacion2)
				.build("Linea A");
		
		lineaB = LineaSubte.getBuilder()
				.agregarEstacion(estacion2)
				.agregarEstacion(estacion3)
				.agregarEstacion(estacion4)
				.build("Linea B");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSalidaDeOrigen() {
		this.tramoSalidaDeOrigen = SalidaDeOrigenSubte.getBuilder()
				.setOrigen(estacion1)
				.setLineaOrigen(lineaA)
				.build();
		
		assertEquals(estacion1, this.tramoSalidaDeOrigen.getEstacionInicial());
		assertEquals(estacion1, this.tramoSalidaDeOrigen.getEstacionFinal());
		assertEquals(lineaA, this.tramoSalidaDeOrigen.getLineaInicial());
		assertEquals(lineaA, this.tramoSalidaDeOrigen.getLineaFinal());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSalidaDeOrigenSinEstacionOrigen() {
		this.tramoSalidaDeOrigen = SalidaDeOrigenSubte.getBuilder()
				.setLineaOrigen(lineaA)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSalidaDeOrigenSinLineaOrigen() {
		this.tramoSalidaDeOrigen = SalidaDeOrigenSubte.getBuilder()
				.setOrigen(estacion1)
				.build();
	}
	
	@Test
	public void testLlegadaADestino() {
		this.tramoLLegadaADestino = LlegadaADestinoSubte.getBuilder()
				.setDestino(estacion2)
				.setLineaDestino(lineaA)
				.build();
		
		assertEquals(estacion2, this.tramoLLegadaADestino.getEstacionInicial());
		assertEquals(estacion2, this.tramoLLegadaADestino.getEstacionFinal());
		assertEquals(lineaA, this.tramoLLegadaADestino.getLineaInicial());
		assertEquals(lineaA, this.tramoLLegadaADestino.getLineaFinal());

	}
	
	@Test(expected = IllegalStateException.class)
	public void testLlegadaADestinoSinEstacionDestino() {
		this.tramoLLegadaADestino = LlegadaADestinoSubte.getBuilder()
				.setLineaDestino(lineaA)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLlegadaADestinoSinLineaDestino() {
		this.tramoLLegadaADestino = LlegadaADestinoSubte.getBuilder()
				.setDestino(estacion2)
				.build();
	}
	
	@Test
	public void testTramoIntraLinea() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionInicialTramo(estacion1)
				.setEstacionFinalTramo(estacion2)
				.setLinea(lineaA)
				.setCabeceraDireccion(estacion3)
				.build();
		
		assertEquals(estacion1, this.tramoIntraLinea.getEstacionInicial());
		assertEquals(estacion2, this.tramoIntraLinea.getEstacionFinal());
		assertEquals(lineaA, this.tramoIntraLinea.getLineaInicial());
		assertEquals(lineaA, this.tramoIntraLinea.getLineaFinal());
		assertEquals(estacion3, this.tramoIntraLinea.getCabeceraDireccion());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoIntraLineaSinEstacionInicial() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionFinalTramo(estacion2)
				.setLinea(lineaA)
				.setCabeceraDireccion(estacion3)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoIntraLineaSinEstacionFinal() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionInicialTramo(estacion1)
				.setLinea(lineaA)
				.setCabeceraDireccion(estacion3)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoIntraLineaConMismaEstacionInicialYFinal() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionInicialTramo(estacion1)
				.setEstacionFinalTramo(estacion1)
				.setLinea(lineaA)
				.setCabeceraDireccion(estacion3)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoIntraLineaSinLinea() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionInicialTramo(estacion1)
				.setEstacionFinalTramo(estacion2)
				.setCabeceraDireccion(estacion3)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoIntraLineaSinCabeceraDireccion() {
		this.tramoIntraLinea = TramoIntraLineaSubte.getBuilder()
				.setEstacionInicialTramo(estacion1)
				.setEstacionFinalTramo(estacion2)
				.setLinea(lineaA)
				.build();
	}
	
	@Test
	public void testCombinacion() {
		this.tramoCombinacion = CombinacionSubte.getBuilder()
				.setEstacionDeCombinacion(estacion4)
				.setLineaInicial(lineaA)
				.setLineaFinal(lineaB)
				.build();
		
		assertEquals(estacion4, this.tramoCombinacion.getEstacionInicial());
		assertEquals(estacion4, this.tramoCombinacion.getEstacionFinal());
		assertEquals(lineaA, this.tramoCombinacion.getLineaInicial());
		assertEquals(lineaB, this.tramoCombinacion.getLineaFinal());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoCombinacionSinEstacionDeCombinacion() {
		this.tramoCombinacion = CombinacionSubte.getBuilder()
				.setLineaInicial(lineaA)
				.setLineaFinal(lineaB)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoCombinacionSinLineaInicial() {
		this.tramoCombinacion = CombinacionSubte.getBuilder()
				.setEstacionDeCombinacion(estacion4)
				.setLineaFinal(lineaB)
				.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTramoCombinacionSinLineaFinal() {
		this.tramoCombinacion = CombinacionSubte.getBuilder()
				.setEstacionDeCombinacion(estacion4)
				.setLineaInicial(lineaA)
				.build();
	}

}
