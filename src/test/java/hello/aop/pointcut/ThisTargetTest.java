package hello.aop.pointcut;


import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * true : CGLIB // default
 * false : JDK
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object thisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("thisInterface={}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모타입 허용
        @Around("target(hello.aop.member.MemberService)")
        public Object targetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("targetInterface={}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("thisImplement={}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("targetImplement={}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
