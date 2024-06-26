package com.isst.nutriapp.grupo18.apirest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
	
class ApirestApplicationTests {

	@Test
	void contextLoads() {
	}

}

public class ReunionServiceTest {

    @Mock
    private ReunionRepository reunionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ReunionService reunionService;

    @Test
    public void testUsuarioExiste() {
        when(usuarioRepository.existsByNombre("usuario1")).thenReturn(true);
        when(usuarioRepository.existsByNombre("usuario2")).thenReturn(false);

        assertTrue(reunionService.usuarioExiste("usuario1"));
        assertFalse(reunionService.usuarioExiste("usuario2"));
    }

    @Test
    public void testNuevaReunion() {
        // Preparar datos de prueba
        String titulo = "Reunión de prueba";
        String link = "https://example.com";
        Integer dia = 5;
        Integer mes = 5;
        String hora = "10:00";
        String usuario = "usuario1";

        // Simular comportamiento del repositorio
        Reunion reunionGuardada = new Reunion();
        reunionGuardada.setId(1); // Simulamos la asignación de ID por la base de datos
        when(reunionRepository.save(any(Reunion.class))).thenReturn(reunionGuardada);

        // Llamar al método a probar
        reunionService.nuevaReunion(titulo, link, dia, mes, hora, usuario);

        // Verificar que se haya llamado al método save del repositorio con los parámetros correctos
        verify(reunionRepository).save(argThat(reunion ->
                reunion.getTitulo().equals(titulo) &&
                reunion.getLink().equals(link) &&
                reunion.getDia().equals(dia) &&
                reunion.getMes().equals(mes) &&
                reunion.getHora().equals(hora) &&
                reunion.getUsuario().equals(usuario)
        ));
    }

    @Test
    public void testGetReunionesDia() {
        // Preparar datos de prueba
        Integer dia = 5;
        Integer mes = 5;
        Reunion reunion1 = new Reunion();
        reunion1.setDia(dia);
        reunion1.setMes(mes);
        Reunion reunion2 = new Reunion();
        reunion2.setDia(dia);
        reunion2.setMes(mes);
        List<Reunion> reuniones = new ArrayList<>();
        reuniones.add(reunion1);
        reuniones.add(reunion2);

        // Simular comportamiento del repositorio
        when(reunionRepository.findAll(any(Example.class))).thenReturn(reuniones);

        // Llamar al método a probar
        List<Reunion> reunionesEncontradas = reunionService.getReunionesDia(mes, dia);

        // Verificar que se devuelva la lista correcta de reuniones
        assertEquals(2, reunionesEncontradas.size());
    }

    @Test
    public void testGetReunion() {
        // Preparar datos de prueba
        Integer dia = 5;
        Integer mes = 5;
        Reunion reunion = new Reunion();
        reunion.setDia(dia);
        reunion.setMes(mes);

        // Simular comportamiento del repositorio
        when(reunionRepository.exists(any(Example.class))).thenReturn(true);

        // Llamar al método a probar
        RespJSON resp = reunionService.getReunion(mes, dia);

        // Verificar que se haya encontrado la reunión
        assertTrue(resp.getResp());
    }

    @Test
    public void testGetDias() {
        // Preparar datos de prueba
        List<Reunion> reuniones = new ArrayList<>();
        reuniones.add(new Reunion());
        reuniones.add(new Reunion());

        // Simular comportamiento del repositorio
        when(reunionRepository.findAll()).thenReturn(reuniones);

        // Llamar al método a probar
        List<Reunion> reunionesObtenidas = reunionService.getDias();

        // Verificar que se devuelva la lista correcta de reuniones
        assertEquals(2, reunionesObtenidas.size());
    }
}
public class EspecialistaServiceTest {

    @Mock
    private EspecialistaRepository especialistaRepository;

    @InjectMocks
    private EspecialistaService especialistaService;

    @Test
    public void testGetEspecialistas() {
        // Preparar datos de prueba
        List<Especialista> especialistas = new ArrayList<>();
        especialistas.add(new Especialista());
        especialistas.add(new Especialista());

        // Simular comportamiento del repositorio
        when(especialistaRepository.findAll()).thenReturn(especialistas);

        // Llamar al método a probar
        List<Especialista> especialistasObtenidos = especialistaService.getEspecialistas();

        // Verificar que se devuelva la lista correcta de especialistas
        assertEquals(2, especialistasObtenidos.size());
    }

    @Test
    public void testGetEspecialistaUsuario() {
        // Preparar datos de prueba
        String usuario = "usuario1";
        Especialista especialista = new Especialista();
        especialista.setUsuario(usuario);

        // Simular comportamiento del repositorio
        when(especialistaRepository.findByUsuario(usuario)).thenReturn(especialista);

        // Llamar al método a probar
        Especialista especialistaEncontrado = especialistaService.getEspecialistaUsuario(usuario);

        // Verificar que se haya encontrado el especialista correcto
        assertNotNull(especialistaEncontrado);
        assertEquals(usuario, especialistaEncontrado.getUsuario());
    }

    @Test
    public void testSetValoracion() {
        // Preparar datos de prueba
        String nombre = "Especialista1";
        Integer valoracion = 4;
        Especialista especialista = new Especialista();
        especialista.setNombre(nombre);

        // Simular comportamiento del repositorio
        when(especialistaRepository.findByNombre(nombre)).thenReturn(especialista);
        when(especialistaRepository.save(any(Especialista.class))).thenReturn(especialista);

        // Llamar al método a probar
        especialistaService.setValoracion(nombre, valoracion);

        // Verificar que se haya establecido la valoración correctamente
        assertEquals(valoracion, especialista.getValoracion());
    }

    @Test
    public void testCreateEspecialista() {
        // Preparar datos de prueba
        String nombre = "Especialista Nuevo";
        String usuario = "nuevoUsuario";
        Integer movil = 123456789;
        String info = "Especialista en algo nuevo";
        Integer precio = 50;
        NuevoEspecialistaJSON especialistaJSON = new NuevoEspecialistaJSON(nombre, usuario, movil, info, precio);

        // Llamar al método a probar
        especialistaService.createEspecialista(nombre, usuario, movil, info, precio);

        // Verificar que se haya guardado el especialista correctamente
        verify(especialistaRepository).save(argThat(especialista ->
                especialista.getNombre().equals(nombre) &&
                especialista.getUsuario().equals(usuario) &&
                especialista.getMovil().equals(movil) &&
                especialista.getInfo().equals(info) &&
                especialista.getPrecio().equals(precio)
        ));
    }
}
public class PlatoServiceTest {

    @Mock
    private PlatoRepository platoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DiaRepository diaRepository;

    @InjectMocks
    private PlatoService platoService;

    @Test
    public void testUsuarioExiste() {
        when(usuarioRepository.existsByNombre("usuario1")).thenReturn(true);
        when(usuarioRepository.existsByNombre("usuario2")).thenReturn(false);

        assertTrue(platoService.usuarioExiste("usuario1"));
        assertFalse(platoService.usuarioExiste("usuario2"));
    }

    // Test para agregar un plato
    @Test
    public void testAgregarPlato() {
        // Definir datos de prueba
        String usuario = "usuario1";
        String nombre = "Arroz con pollo";
        String preparacion = "Cocinar arroz, pollo y vegetales";
        String ingredientes = "pollo, arroz, vegetales";
        String descripcion = "500g, 300g, 200g";
        String calorias = "500 kcal";
        Integer caloriasTotal = 500;
        String diaMes = "5_5";

        // Simular comportamiento del repositorio
        when(platoRepository.exists(any(Example.class))).thenReturn(false);

        // Llamar al método a probar
        platoService.agregarPlato(usuario, nombre, preparacion, ingredientes, descripcion, calorias, caloriasTotal, diaMes);

        // Verificar que se haya guardado el plato correctamente
        verify(platoRepository).save(any(Plato.class));
    }

    // Test para modificar un plato
    @Test
    public void testModificarPlato() {
        // Definir datos de prueba
        String usuario = "usuario1";
        String nombre = "Arroz con pollo";
        String preparacion = "Cocinar arroz, pollo y vegetales";
        String ingredientes = "pollo, arroz, vegetales";
        String descripcion = "500g, 300g, 200g";
        String calorias = "500 kcal";
        Integer caloriasTotal = 500;
        String diaMes = "5_5";

        // Simular comportamiento del repositorio
        List<Plato> platos = new ArrayList<>();
        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setIngredientes(ingredientes);
        plato.setCalorias(calorias);
        plato.setCantidades(descripcion);
        plato.setCaloriasTot(caloriasTotal);
        platos.add(plato);
        when(platoRepository.findByDias_Id(any(Integer.class))).thenReturn(platos);

        // Llamar al método a probar
        platoService.modificarPlato(usuario, nombre, preparacion, ingredientes, descripcion, calorias, caloriasTotal, diaMes);

        // Verificar que se haya modificado el plato correctamente
        verify(platoRepository).save(any(Plato.class));
    }
}
public class DiaServiceTest {

    @Mock
    private DiaRepository diaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DiaService diaService;

    @Test
    public void testUsuarioExiste() {
        when(usuarioRepository.existsByNombre("usuario1")).thenReturn(true);
        when(usuarioRepository.existsByNombre("usuario2")).thenReturn(false);

        assertTrue(diaService.usuarioExiste("usuario1"));
        assertFalse(diaService.usuarioExiste("usuario2"));
    }

    @Test
    public void testCreateCalendario() {
        // Definir usuario existente
        when(diaRepository.existsByNombreusuario("usuario1")).thenReturn(true);

        // Llamar al método a probar
        diaService.createCalendario("usuario1");

        // Verificar que no se haya guardado ningún día (ya que el usuario existe)
        verify(diaRepository, never()).save(any(Dia.class));
    }

    @Test
    public void testGetPlatosDia() {
        // Definir datos de prueba
        String nombre = "usuario1";
        Integer mes = 1;
        Integer dia = 1;

        // Simular comportamiento del repositorio
        Dia diaEntity = new Dia();
        diaEntity.setNombreusuario(nombre);
        diaEntity.setDia(dia);
        diaEntity.setMes(mes);
        Plato plato = new Plato();
        plato.setNombre("Arroz con pollo");
        List<Plato> platos = new ArrayList<>();
        platos.add(plato);
        diaEntity.setPlatos(platos);
        when(diaRepository.findOne(any(Example.class))).thenReturn(Optional.of(diaEntity));

        // Llamar al método a probar
        ArrayList<PlatosJSON> platosDia = diaService.getPlatosDia(nombre, mes, dia);

        // Verificar que se obtengan los platos correctamente
        assertEquals(1, platosDia.size());
        assertEquals("Arroz con pollo", platosDia.get(0).getNombre());
    }

    @Test
    public void testGetDias() {
        // Definir usuario existente
        when(diaRepository.findByNombreusuario("usuario1")).thenReturn(new ArrayList<>());

        // Llamar al método a probar
        ArrayList<DiaJSON> dias = diaService.getDias("usuario1");

        // Verificar que no se hayan encontrado días (ya que no hay platos asociados)
        assertEquals(0, dias.size());
    }

}
