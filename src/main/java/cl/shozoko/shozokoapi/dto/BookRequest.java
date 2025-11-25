package cl.shozoko.shozokoapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BookRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Integer price;

    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    public BookRequest() {
    }

    public BookRequest(String title, String author, String category, Integer price, Integer stock) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
