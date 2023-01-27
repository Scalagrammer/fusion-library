package scg.fusion.library.security.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.RequiredArgsConstructor;
import scg.fusion.grpc.security.GrantedAuthority;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public enum LibraryRoles implements GrantedAuthority {

    READER("reader"), WRITER("writer"), ADMIN("admin");

    public final String value;

    @JsonCreator
    public static LibraryRoles fromJson(String value) {

        for (LibraryRoles role : values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }

        throw new NoSuchElementException(value);

    }

}
