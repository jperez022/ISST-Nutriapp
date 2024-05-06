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
