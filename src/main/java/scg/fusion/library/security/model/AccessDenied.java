package scg.fusion.library.security.model;

public final class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }
}
