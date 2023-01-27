package scg.fusion.library.security;

import scg.fusion.MethodJoint;
import scg.fusion.annotation.Around;
import scg.fusion.annotation.Privileged;
import scg.fusion.aop.ExecutionJoinPoint;

import scg.fusion.library.security.model.AccessDenied;
import scg.fusion.library.security.model.LibraryAuthorization;
import scg.fusion.library.security.model.Unauthorized;

import javax.annotation.security.RolesAllowed;

import static java.util.Objects.nonNull;
import static scg.fusion.grpc.security.Authorization.lookup;

public interface AuthzSpecLayer {
    @Privileged
    @Around("@execution(javax.annotation.security.RolesAllowed)")
    default Object proceedAuthorized(ExecutionJoinPoint joinPoint) throws Throwable {

        LibraryAuthorization auth = lookup(LibraryAuthorization.class);

        if (nonNull(auth)) {

            MethodJoint joint = joinPoint.dissect();

            for (String value : joint.getAnnotation(RolesAllowed.class).value()) {
                if (auth.hasRole(value)) {
                    return joinPoint.proceed();
                }
            }

            throw new AccessDenied("Not enough authorities");
        }

        throw new Unauthorized();

    }
}
