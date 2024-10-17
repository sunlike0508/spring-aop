package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV4PointCut {

    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처

        return joinPoint.proceed();
    }

    // hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
       try {
           log.info("[transaction start] {}", joinPoint.getSignature());
           Object result = joinPoint.proceed();
           log.info("[transaction commit] {}", joinPoint.getSignature());
           return result;
       } catch (Throwable throwable) {
           log.error("[transaction rollback] {}", joinPoint.getSignature());
           throw throwable;
       } finally {
           log.info("[transaction exit] {}", joinPoint.getSignature());
       }
    }
}
