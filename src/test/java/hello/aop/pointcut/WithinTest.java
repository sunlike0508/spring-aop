package hello.aop.pointcut;

import java.lang.reflect.Method;
import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void withinStar() {
        pointcut.setExpression("within(hello.aop.member.*Service*)");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(hello.aop..*)");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage2() {
        pointcut.setExpression("within(hello.aop.member.MemberService)");

        assertThat(pointcut.matches(helloMethod, MemberService.class)).isFalse();
    }

    @Test
    void withinSubPackage3() {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..)))");

        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }
}
