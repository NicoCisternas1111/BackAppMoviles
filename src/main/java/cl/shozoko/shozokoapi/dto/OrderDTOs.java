package cl.shozoko.shozokoapi.dto;

import java.util.List;

public class OrderDTOs {

    public static class OrderItemRequest {
        public Long bookId;
        public Integer quantity;
    }

    public static class OrderRequest {
        public Long userId;
        public List<OrderItemRequest> items;
    }

    // Respuesta básica
    public static class OrderResponse {
        public Long id;
        public Long userId;

        public OrderResponse(Long id, Long userId) {
            this.id = id;
            this.userId = userId;
        }
    }
}
