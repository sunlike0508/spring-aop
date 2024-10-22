package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV2Test {

    @Autowired
    private CallServiceV2 callServiceV2;

    @Test
    void external() {
        callServiceV2.external();
    }

    @Test
    void internal() {
        callServiceV2.internal();
    }
}