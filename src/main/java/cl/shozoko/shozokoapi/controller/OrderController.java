package cl.shozoko.shozokoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.shozoko.shozokoapi.dto.OrderDTOs.OrderRequest;
import cl.shozoko.shozokoapi.dto.OrderDTOs.OrderResponse;
import cl.shozoko.shozokoapi.model.Order;
import cl.shozoko.shozokoapi.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest req) {
        Order order = orderService.createOrder(req);
        OrderResponse resp = new OrderResponse(order.getId(), order.getUser().getId());
        return ResponseEntity.ok(resp);
    }
}
