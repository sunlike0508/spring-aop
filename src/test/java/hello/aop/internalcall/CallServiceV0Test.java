package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV0Test {

    @Autowired
    private CallServiceV0 callServiceV0;

    @Autowired
    private CallServiceV1 callServiceV1;

    @Test
    void external() {
        //callServiceV0.external();
        callServiceV1.external();
    }

    @Test
    void internal() {
        callServiceV0.internal();
    }
}