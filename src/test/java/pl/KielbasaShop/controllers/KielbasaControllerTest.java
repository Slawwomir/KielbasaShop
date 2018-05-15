package pl.KielbasaShop.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.KielbasaShop.model.Kielbasa;
import pl.KielbasaShop.services.KielbasaService;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class KielbasaControllerTest {

    Kielbasa kielbasa;
    KielbasaController kielbasaController;

    @Mock
    KielbasaService service;

    @Before
    public void initValues(){
        kielbasa = new Kielbasa();
        kielbasaController = new KielbasaController(service);
    }

    @Test
    public void addKielbasaTest(){
        kielbasa.setPrice(22.0);
        kielbasa.setAmount(2);
        kielbasa.setTitle("Testowa");
        Mockito.when(service.find(any(UUID.class))).thenReturn(null);
        assertEquals(kielbasaController.addKielbasa(kielbasa,
                UriComponentsBuilder.newInstance().path("localhost:1527")),
                ResponseEntity.created(UriComponentsBuilder.newInstance().
                        path("localhost:1527/kielbasas/{id}").buildAndExpand(kielbasa.getId()).toUri()).build());
        Mockito.verify(service, Mockito.times(1)).save(kielbasa);
    }

    @Test
    public void getKielbasaTest(){
        Mockito.when(service.find(any(UUID.class))).thenReturn(kielbasa);
        assertEquals(ResponseEntity.ok(kielbasa), kielbasaController.getKielbasa(kielbasa.getId()));
    }

    @Test
    public void updateKielbasaWhenKielbasaExistsTest(){
        Mockito.when(service.find(any(UUID.class))).thenReturn(kielbasa);
        assertEquals(ResponseEntity.ok().build(), kielbasaController.updateKielbasa(kielbasa));
        Mockito.verify(service, Mockito.times(1)).save(any(Kielbasa.class));
    }

    @Test
    public void updateKielbasaWhenKielbasaNotExistTest(){
        Mockito.when(service.find(any(UUID.class))).thenReturn(null);
        assertEquals(ResponseEntity.notFound().build(), kielbasaController.updateKielbasa(kielbasa));
        Mockito.verify(service, Mockito.never()).save(any(Kielbasa.class));
    }
}
