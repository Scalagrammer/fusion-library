package scg.fusion.library.container;

import lombok.RequiredArgsConstructor;
import scg.fusion.library.dao.BookRepository;
import scg.fusion.library.container.annotation.Api;
import scg.fusion.library.container.annotation.Read;
import scg.fusion.library.container.annotation.Write;
import scg.fusion.library.container.model.protocol.ContainerResult;

import scg.fusion.library.transport.grpc.Library.Book;
import scg.fusion.library.transport.grpc.Library.BookRequest;
import scg.fusion.library.transport.grpc.Library.Unit;

import static scg.fusion.library.container.model.protocol.ContainerResult.success;
import static scg.fusion.library.container.model.protocol.ResultCode.SUCCESS;
import static scg.fusion.library.transport.grpc.Library.Unit.getDefaultInstance;

@Api
@RequiredArgsConstructor
public class BookContainer {

    private final BookRepository service;

    @Write
    public ContainerResult<Unit> add(Book request) {
        service.add(request);
        return success(SUCCESS, getDefaultInstance());
    }

    @Read
    public ContainerResult<Book> get(BookRequest request) {
        return success(SUCCESS, service.get(request));
    }

}
