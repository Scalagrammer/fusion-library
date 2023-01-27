package scg.fusion.library;

import scg.fusion.Application;
import scg.fusion.ComponentDiscovery;
import scg.fusion.ComponentFactory;
import scg.fusion.XmlClasspathComponentDiscoveryService;
import scg.fusion.annotation.AwaitShutdown;
import scg.fusion.library.container.BookContainer;
import scg.fusion.library.container.model.protocol.BookContainerApi;
import scg.fusion.library.container.model.protocol.ContractAdvice;
import scg.fusion.library.dao.BookRepository;
import scg.fusion.library.security.AuthnService;
import scg.fusion.library.security.AuthzSpecLayer;
import scg.fusion.library.security.model.BearerToken;
import scg.fusion.library.security.model.LibraryAuthorization;

import scg.fusion.library.security.model.LibraryUser;
import scg.fusion.library.transport.grpc.BookService;

import scg.fusion.library.transport.grpc.Library.Book;
import scg.fusion.library.transport.grpc.Library.Genre;

import static java.util.Collections.singleton;
import static scg.fusion.library.security.model.LibraryRoles.WRITER;
import static scg.fusion.library.transport.grpc.Library.Genre.DRAMA;

@AwaitShutdown
public class ContainerApp extends Application implements XmlClasspathComponentDiscoveryService {
    @Override
    public void discoverAlso(ComponentDiscovery discovery) {
        discovery.found(AuthnService.class, AuthzSpecLayer.class);
        discovery.found(BookService.class, BookContainerApi.class);
        discovery.found(BookContainer.class, BookRepository.class, ContractAdvice.class);
    }

    @Override
    public void startup(ComponentFactory components) {

        Book book = newBook("Les Misérables", "Victor Hugo", DRAMA);

        new LibraryAuthorization(new BearerToken("<empty>"), new LibraryUser("fusion"), singleton(WRITER)).attach();

        System.out.println(components.get(BookContainerApi.class).write(book));

    }

    private static Book newBook(String title, String author, Genre genre) {
        return Book.newBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .build();
    }
}