package scg.fusion.library.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import scg.fusion.grpc.security.AuthInterceptor;
import scg.fusion.library.security.model.BearerToken;
import scg.fusion.library.security.model.LibraryAuthorization;
import scg.fusion.library.security.model.LibraryRoles;
import scg.fusion.library.security.model.LibraryUser;
import scg.fusion.opa.OpaClient;

import java.io.IOException;
import java.util.Set;

import static java.util.Collections.emptySet;

@Slf4j
@RequiredArgsConstructor
public final class AuthnService extends AuthInterceptor {

    private final ObjectMapper mapper = new ObjectMapper();

    private final OpaClient agent;

    @Override
    protected LibraryAuthorization authenticate(String token) throws IOException {

        AuthTestToken testToken = mapper.readValue(token, AuthTestToken.class);

        AuthzResult.Authorization authorization = agent.evalPolicy(testToken, AuthzResult.class).authorization;

        BearerToken credentials = new BearerToken(token);

        LibraryUser principal = new LibraryUser(authorization.getUsername());

        return new LibraryAuthorization(credentials, principal, authorization.getGrantedAuthorities());

    }

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    private static class AuthTestToken {
        private String authId;
    }

    @Data
    private static class AuthzResult {

        private Authorization authorization;

        @Data
        private static class Authorization {
            private Set<LibraryRoles> grantedAuthorities = emptySet();
            private String username;
        }

    }
}
