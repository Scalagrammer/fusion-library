package scg.fusion.library.security.model;

import lombok.Value;
import scg.fusion.grpc.security.Credentials;

@Value
public class BearerToken implements Credentials {
    String value;
}
