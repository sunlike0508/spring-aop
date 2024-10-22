package hello.aop.proxyvs;


import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class= {}", memberService.getClass());
        log.info("memberServiceImpl class= {}", memberServiceImpl.getClass());

        memberServiceImpl.hello("hello");
    }
}
