package scg.fusion.library.container.model.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Function;

@Getter
@ToString
@RequiredArgsConstructor
public final class ContainerResult<R> {

    private final ResultCode resultCode;

    private final R result;

    private final Throwable cause;

    private final String description;

    public boolean hasResult() {
        return Objects.nonNull(result);
    }

    public boolean hasException() {
        return Objects.nonNull(cause);
    }

    public <T> ContainerResult<T> map(Function<R, T> f) {
        if (hasResult()) {
            return new ContainerResult<>(resultCode, f.apply(result), cause, description);
        } else {
            return (ContainerResult<T>) this;
        }
    }

    public static <R> ContainerResult<R> success(ResultCode code, R value) {
        return new ContainerResult<>(code, value, null, null);
    }

    public static <R> ContainerResult<R> failure(Throwable cause, ResultCode code) {
        return new ContainerResult<>(code, null, cause, null);
    }

    public static <R> ContainerResult<R> failure(Throwable cause, ResultCode code, String description) {
        return new ContainerResult<>(code, null, cause, description);
    }

    public static <R> ContainerResult<R> code(ResultCode code, String description) {
        return new ContainerResult<>(code, null, null, description);
    }

    public static <R> ContainerResult<R> code(ResultCode code) {
        return new ContainerResult<>(code, null, null, null);
    }

}
