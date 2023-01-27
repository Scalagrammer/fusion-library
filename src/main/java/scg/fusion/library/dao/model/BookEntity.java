package scg.fusion.library.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import scg.fusion.library.transport.grpc.Library;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

import static scg.fusion.library.dao.model.BookEntity.QUERY;

@Data
@Entity
@NoArgsConstructor
@Table(name = "books")
@NamedQuery(name = QUERY, query = "select book from BookEntity book where book.id.title = :title and book.id.author = :author")
public class BookEntity {

    public static final String QUERY = "BookEntity.select";

    @EmbeddedId
    private BookId id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Genre genre;

    public Library.Book toBook() {
        return Library.Book.newBuilder()
                .setTitle(id.getTitle())
                .setAuthor(id.getAuthor())
                .setGenreValue(genre.ordinal())
                .build();
    }

    public static BookEntity fromBook(Library.Book book) {

        BookId bookId = new BookId();

        bookId.setTitle(book.getTitle());
        bookId.setAuthor(book.getAuthor());

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId(bookId);
        bookEntity.setGenre(Genre.valueOf((book.getGenre()).name()));

        return bookEntity;
    }

}
