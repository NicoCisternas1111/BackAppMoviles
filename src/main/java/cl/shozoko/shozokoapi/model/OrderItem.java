package cl.shozoko.shozokoapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Orden a la que pertenece
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    // Libro comprado
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    // Cantidad
    @Column(nullable = false)
    private Integer quantity;

    // Precio que tenía el libro al momento de la compra
    @Column(name = "price_at_purchase", nullable = false)
    private Integer priceAtPurchase;

    public OrderItem() {
    }

    public OrderItem(Book book, Integer quantity, Integer priceAtPurchase) {
        this.book = book;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    // ===== Getters & setters =====

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPriceAtPurchase() {
        return priceAtPurchase;
    }
}
