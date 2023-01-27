package scg.fusion.library.transport.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import scg.fusion.library.container.model.protocol.BookContainerApi;
import scg.fusion.library.container.model.protocol.ContainerResult;

@RequiredArgsConstructor
public class BookService extends BookServiceGrpc.BookServiceImplBase {

    private final BookContainerApi api;

    @Override
    public void get(Library.BookRequest request, StreamObserver<Library.BookResponse> responseObserver) {
        convertServiceResult(api.read(request), responseObserver);
    }

    @Override
    public void add(Library.Book request, StreamObserver<Library.Unit> responseObserver) {
        convertServiceResult(api.write(request), responseObserver);
    }

    private static <R> void convertServiceResult(ContainerResult<R> result, StreamObserver<R> responseObserver) {
        switch (result.getResultCode()) {
            case SUCCESS:
                responseObserver.onNext(result.getResult());
                responseObserver.onCompleted();
                break;
            case UNAUTHORIZED:
                responseObserver.onError(Status.UNAUTHENTICATED.asException());
                break;
            case ACCESS_DENIED:
                responseObserver.onError(Status.PERMISSION_DENIED.asException());
                break;
            case NOT_FOUND:
                responseObserver.onError(Status.NOT_FOUND.asException());
                break;
            case INTERNAL_ERROR:
                responseObserver.onError(Status.INTERNAL.asException());
                break;
            default:
                responseObserver.onError(Status.UNKNOWN.asException());
                break;
        }
    }

}
