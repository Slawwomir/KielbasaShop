package pl.KielbasaShop.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.KielbasaShop.model.Order;
import pl.KielbasaShop.services.OrdersService;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ShopControllerTest {

    Order order;
    ShopController shopController;

    @Mock
    OrdersService service;

    @Before
    public void initValues(){
        order = new Order();
        shopController = new ShopController(service);
    }

    @Test
    public void addOrderTest(){
        assertEquals(shopController.addOrder(order,
                UriComponentsBuilder.newInstance().path("localhost:1527")),
                ResponseEntity.created(UriComponentsBuilder.newInstance().
                        path("localhost:1527/orders/{id}").buildAndExpand(order.getId()).toUri()).build());
        Mockito.verify(service, Mockito.times(1)).placeOrder(order);
    }

    @Test
    public void getOrderTest(){
        Mockito.when(service.find(any(UUID.class))).thenReturn(order);
        assertEquals(ResponseEntity.ok(order), shopController.getOrder(order.getId()));
    }
}
