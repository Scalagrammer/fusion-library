package scg.fusion.library.dao;

import lombok.RequiredArgsConstructor;
import scg.fusion.jpa.annotations.Transactional;
import scg.fusion.library.dao.model.BookEntity;
import scg.fusion.library.transport.grpc.Library;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;

import static scg.fusion.library.dao.model.BookEntity.QUERY;

@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager entityManager;

    @Transactional
    @RolesAllowed({"reader", "admin"})
    public Library.Book get(Library.BookRequest request) {
        return entityManager.createNamedQuery(QUERY, BookEntity.class)
                .setParameter("title", request.getTitle())
                .setParameter("author", request.getAuthor())
                .getSingleResult()
                .toBook();
    }

    @Transactional
    @RolesAllowed({"writer", "admin"})
    public void add(Library.Book book) {
        entityManager.persist(BookEntity.fromBook(book));
    }

}
