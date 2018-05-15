package pl.KielbasaShop.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.KielbasaShop.model.Kielbasa;
import pl.KielbasaShop.model.Order;
import pl.KielbasaShop.services.exceptions.OutOfStockException;
import pl.KielbasaShop.services.exceptions.TooSmallOrderValueException;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {

    @Mock
    EntityManager manager;

    @Mock
    Order order;

    @Test(expected = OutOfStockException.class)
    public void whenOrderedKielbasaNotAvailable_placeOrderThrowsOutOfStockException(){
        Kielbasa kielbasa = new Kielbasa();
        List<Kielbasa> listOfKielbasas = new ArrayList<>();
        OrdersService ordersService = new OrdersService(manager);

        Mockito.when(order.getKielbasas()).thenReturn(listOfKielbasas);
        Mockito.when(manager.find(eq(Kielbasa.class), any(UUID.class))).thenReturn(kielbasa);

        kielbasa.setAmount(0);
        kielbasa.setPrice(22.0);
        listOfKielbasas.add(kielbasa);

        ordersService.placeOrder(order);

        Mockito.verify(manager, Mockito.never()).persist(any(Order.class));
    }

    @Test(expected = TooSmallOrderValueException.class)
    public void whenOrderedKielbasaIsTooCheap_placeOrderThrowsTooSmallOrderValueException(){
        Kielbasa kielbasa = new Kielbasa();
        List<Kielbasa> listOfKielbasas = new ArrayList<>();
        OrdersService ordersService = new OrdersService(manager);

        Mockito.when(order.getKielbasas()).thenReturn(listOfKielbasas);
        Mockito.when(manager.find(eq(Kielbasa.class), any(UUID.class))).thenReturn(kielbasa);

        kielbasa.setAmount(OrdersService.minAmount);
        kielbasa.setPrice(OrdersService.minValue - 1);
        listOfKielbasas.add(kielbasa);

        ordersService.placeOrder(order);

        Mockito.verify(manager, Mockito.never()).persist(any(Order.class));
    }

    @Test
    public void placeOrderTest(){
        Kielbasa kielbasa = new Kielbasa();
        List<Kielbasa> listOfKielbasas = new ArrayList<>();
        OrdersService ordersService = new OrdersService(manager);

        Mockito.when(order.getKielbasas()).thenReturn(listOfKielbasas);
        Mockito.when(manager.find(eq(Kielbasa.class), any(UUID.class))).thenReturn(kielbasa);

        kielbasa.setAmount(OrdersService.minAmount);
        kielbasa.setPrice(OrdersService.minValue + 1);
        listOfKielbasas.add(kielbasa);

        ordersService.placeOrder(order);

        Assert.assertTrue(kielbasa.getAmount() == 0);
        Mockito.verify(manager, Mockito.times(1)).persist(any(Order.class));
    }


}
