package scg.fusion.library.security.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import scg.fusion.grpc.security.Authorization;

import java.util.Set;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class LibraryAuthorization implements Authorization<LibraryRoles, LibraryUser, BearerToken> {

    private final BearerToken credentials;
    private final LibraryUser principal;
    private final Set<LibraryRoles> authorities;

    public boolean hasRole(String value) {

        for (LibraryRoles authority : authorities) {
            if (authority.value.equals(value)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

}
