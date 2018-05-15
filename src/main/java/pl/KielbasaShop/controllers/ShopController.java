package pl.KielbasaShop.controllers;

import org.springframework.core.OrderComparator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.KielbasaShop.model.Order;
import pl.KielbasaShop.services.OrdersService;
import pl.KielbasaShop.services.exceptions.OrderException;
import pl.KielbasaShop.services.exceptions.OutOfStockException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {
    final OrdersService ordersService;

    public ShopController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/orders")
    public List<Order> listOrders(){
        return ordersService.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id){
        Order order = ordersService.find(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> addOrder(@RequestBody Order order, UriComponentsBuilder uriBuilder){
        try{
            ordersService.placeOrder(order);
            URI location = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        catch (OrderException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
