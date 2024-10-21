package hello.aop.exam.aop;


import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {}, args={}", joinPoint.getSignature(), retry);

        int maxTry = retry.value();

        Exception holder = null;

        for(int i = 0; i < maxTry; i++) {
            try {
                log.info("[retry] count {}", i);
                return joinPoint.proceed();
            } catch(Exception e) {
                holder =  e;
            }
        }

        throw holder;
    }
}
