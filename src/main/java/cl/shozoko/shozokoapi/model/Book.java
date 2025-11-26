package cl.shozoko.shozokoapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false)
    private String category;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer price;

    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer stock;

    @Column(name = "cover_uri")
    private String coverUri;

    // ===== Constructores =====
    public Book() {
    }

    public Book(String title, String author, String category, Integer price, Integer stock) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public String getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(String coverUri) {
        this.coverUri = coverUri;
    }


    // no ponemos setId, lo maneja JPA

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
