package scg.fusion.library.container.model.protocol;

import scg.fusion.annotation.Around;
import scg.fusion.aop.ExecutionJoinPoint;
import scg.fusion.library.security.model.AccessDenied;
import scg.fusion.library.container.model.NotFound;
import scg.fusion.library.security.model.Unauthorized;

import static scg.fusion.library.container.model.protocol.ResultCode.*;
import static scg.fusion.library.container.model.protocol.ResultCode.INTERNAL_ERROR;

public interface ContractAdvice {
    @Around("@execution(scg.fusion.library.container.annotation.Read) && @target(scg.fusion.library.container.annotation.Api)")
    @Around("@execution(scg.fusion.library.container.annotation.Write) && @target(scg.fusion.library.container.annotation.Api)")
    default Object aroundService(ExecutionJoinPoint entryPoint) {
        try {
            return entryPoint.proceed();
        } catch (NotFound cause) {
            return ContainerResult.code(NOT_FOUND, "Book not found");
        } catch (Unauthorized cause) {
            return ContainerResult.code(UNAUTHORIZED, "Required authorization");
        } catch (AccessDenied cause) {
            return ContainerResult.code(ACCESS_DENIED, "Not enough authorities");
        } catch (Throwable cause) {
            return ContainerResult.failure(cause, INTERNAL_ERROR, "An error has occurred during request processing");
        }
    }
}
