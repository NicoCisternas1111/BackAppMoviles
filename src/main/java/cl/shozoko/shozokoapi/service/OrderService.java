package cl.shozoko.shozokoapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cl.shozoko.shozokoapi.dto.OrderDTOs.OrderItemRequest;
import cl.shozoko.shozokoapi.dto.OrderDTOs.OrderRequest;
import cl.shozoko.shozokoapi.model.Book;
import cl.shozoko.shozokoapi.model.Order;
import cl.shozoko.shozokoapi.model.OrderItem;
import cl.shozoko.shozokoapi.model.User;
import cl.shozoko.shozokoapi.repository.BookRepository;
import cl.shozoko.shozokoapi.repository.OrderRepository;
import cl.shozoko.shozokoapi.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest req) {
        if (req.items == null || req.items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden no tiene ítems");
        }

        User user = userRepository.findById(req.userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        // 1) Verificar stock
        for (OrderItemRequest itemReq : req.items) {
            Book book = bookRepository.findById(itemReq.bookId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Libro no encontrado: " + itemReq.bookId));

            if (itemReq.quantity == null || itemReq.quantity <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cantidad inválida para el libro " + itemReq.bookId);
            }

            if (book.getStock() < itemReq.quantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el libro " + book.getTitle());
            }
        }

        // 2) Crear orden, descontar stock y guardar
        Order order = new Order(user);

        for (OrderItemRequest itemReq : req.items) {
            Book book = bookRepository.findById(itemReq.bookId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Libro no encontrado: " + itemReq.bookId));

            // Descuento de stock
            int nuevoStock = book.getStock() - itemReq.quantity;
            book.setStock(nuevoStock);
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem(
                    book,
                    itemReq.quantity,
                    book.getPrice()
            );

            order.addItem(orderItem);
        }

        return orderRepository.save(order);
    }
}
