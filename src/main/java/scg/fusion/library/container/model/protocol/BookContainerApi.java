package scg.fusion.library.container.model.protocol;

import lombok.RequiredArgsConstructor;
import scg.fusion.AutowiringHook;
import scg.fusion.ComponentFactory;
import scg.fusion.Joint;
import scg.fusion.annotation.Initialize;
import scg.fusion.library.container.annotation.Read;
import scg.fusion.library.container.annotation.Write;
import scg.fusion.library.transport.grpc.Library;

import java.util.Map;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public final class BookContainerApi {

    private final ComponentFactory components;

    private AutowiringHook readHook;
    private AutowiringHook writeHook;

    @Initialize
    private void bind() {

        Map<Joint, AutowiringHook> readBy = components.by("@execution(%s)", Read.class);

        for (Joint joint : readBy.keySet()) {
            readHook = readBy.get(joint);
            break;
        }

        Map<Joint, AutowiringHook> writeBy = components.by("@execution(%s)", Write.class);

        for (Joint joint : writeBy.keySet()) {
            writeHook = writeBy.get(joint);
            break;
        }

        if (isNull(readHook) | isNull(writeHook)) {
            throw new ExceptionInInitializerError();
        }
    }

    public ContainerResult<Library.BookResponse> read(Library.BookRequest request) {

        ContainerResult<Library.Book> result = readHook.autowireWith(components.swap(request));

        return result.map(book -> Library.BookResponse.newBuilder().setBook(book).build());

    }

    public ContainerResult<Library.Unit> write(Library.Book request) {
        return writeHook.autowireWith(components.swap(request));
    }

}