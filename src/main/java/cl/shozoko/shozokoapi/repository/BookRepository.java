package cl.shozoko.shozokoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.shozoko.shozokoapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
