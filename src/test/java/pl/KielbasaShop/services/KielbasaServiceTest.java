package pl.KielbasaShop.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.KielbasaShop.model.Kielbasa;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class KielbasaServiceTest {

    @Test
    public void findAllTest(){
        List<Kielbasa> list = new ArrayList<>();
        EntityManager manager = Mockito.mock(EntityManager.class, Mockito.RETURNS_DEEP_STUBS);
        KielbasaService service = new KielbasaService(manager);
        Mockito.when(
                manager.createNamedQuery(any(String.class), eq(Kielbasa.class)).getResultList()).
                thenReturn(list);

        list.add(new Kielbasa());

        Assert.assertEquals(service.findAll(), list);
    }
}
