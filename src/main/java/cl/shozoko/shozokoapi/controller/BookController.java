package cl.shozoko.shozokoapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.shozoko.shozokoapi.dto.BookRequest;
import cl.shozoko.shozokoapi.model.Book;
import cl.shozoko.shozokoapi.repository.BookRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
// @CrossOrigin(origins = "*") // ← si después te da algún tema con CORS desde algún cliente web
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ====== LISTAR TODOS ======
    @GetMapping
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    // ====== OBTENER UNO POR ID ======
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Optional<Book> opt = bookRepository.findById(id);
        return opt.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ====== CREAR LIBRO ======
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getCategory(),
                request.getPrice(),
                request.getStock()
        );
        Book saved = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ====== ACTUALIZAR LIBRO ======
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request
    ) {
        Optional<Book> opt = bookRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book existing = opt.get();
        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setCategory(request.getCategory());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());

        Book saved = bookRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    // ====== ELIMINAR LIBRO ======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
