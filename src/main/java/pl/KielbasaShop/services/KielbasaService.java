package pl.KielbasaShop.services;

import org.springframework.stereotype.Service;
import pl.KielbasaShop.model.Kielbasa;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class KielbasaService extends EntityService<Kielbasa> {

    public KielbasaService(EntityManager manager){
        super(manager, Kielbasa.class, Kielbasa::getId);
    }

    public List<Kielbasa> findAll() {
        return manager.createNamedQuery(Kielbasa.FIND_ALL, Kielbasa.class).getResultList();
    }
}
