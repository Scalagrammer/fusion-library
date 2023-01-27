package scg.fusion.library.security.model;

import lombok.Value;
import scg.fusion.grpc.security.Principal;

@Value
public class LibraryUser implements Principal {
    String name;
}
