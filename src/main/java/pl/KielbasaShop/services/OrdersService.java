package pl.KielbasaShop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.KielbasaShop.model.Kielbasa;
import pl.KielbasaShop.model.Order;
import pl.KielbasaShop.services.exceptions.OrderException;
import pl.KielbasaShop.services.exceptions.OutOfStockException;
import pl.KielbasaShop.services.exceptions.TooSmallOrderValueException;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class OrdersService extends EntityService<Order> {
    public static final double minValue = 20.0;
    public static final int minAmount = 1;

    public OrdersService(EntityManager manager){
        super(manager, Order.class, Order::getId);
    }

    public List<Order> findAll(){
        return manager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Transactional
    public void placeOrder(Order order){
        double sum = 0;

        if(order.getKielbasas().size() < minAmount)
            throw new OrderException();

        for(Kielbasa k : order.getKielbasas()){
            Kielbasa kielbasa = manager.find(Kielbasa.class, k.getId());
            if(kielbasa == null) continue;
            if(kielbasa.getAmount() < 1){
                throw new OutOfStockException();
            }
            sum += kielbasa.getPrice();
        }

        if(sum < minValue)
            throw new TooSmallOrderValueException();

        for(Kielbasa k : order.getKielbasas()){
            Kielbasa kielbasa = manager.find(Kielbasa.class, k.getId());
            kielbasa.setAmount(kielbasa.getAmount()-1);
        }

        save(order);
    }
}
