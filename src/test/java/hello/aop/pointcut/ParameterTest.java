package hello.aop.pointcut;


import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberServce ={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {

        }


        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg = joinPoint.getArgs()[0];
            log.info("sig ={}, arg ={}", joinPoint.getSignature(), arg);

            return joinPoint.proceed();
        }


        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {

            log.info("sig ={}, arg ={}", joinPoint.getSignature(), arg);

            return joinPoint.proceed();
        }


        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) throws Throwable {
            log.info("arg ={}", arg);
        }


        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinpoint, MemberService obj) throws Throwable {
            log.info("this ={} , arg ={}", joinpoint.getSignature(), obj.getClass());
        }


        @Before("allMember() && target(obj)")
        public void targetArgs2(JoinPoint joinpoint, MemberService obj) throws Throwable {
            log.info("target ={} , arg ={}", joinpoint.getSignature(), obj.getClass());
        }


        @Before("allMember() && target(annotation)")
        public void annotation(JoinPoint joinpoint, ClassAop annotation) throws Throwable {
            log.info("target ={} , annotation ={}", joinpoint.getSignature(), annotation);
        }


        @Before("allMember() && within(annotation)")
        public void atWithin(JoinPoint joinpoint, ClassAop annotation) throws Throwable {
            log.info("within ={} , arg ={}", joinpoint.getSignature(), annotation);
        }


        @Before("allMember() && @annotation(annotation)")
        public void annotationv(JoinPoint joinpoint, MethodAop annotation) throws Throwable {
            log.info("annotation ={} , arg ={}", joinpoint.getSignature(), annotation.value());
        }
    }
}
