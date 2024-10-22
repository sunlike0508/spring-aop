package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV3Test {

    @Autowired
    private CallServiceV3 callServiceV3;

    @Test
    void external() {
        callServiceV3.external();
    }
}