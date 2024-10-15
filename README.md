# AOP

## AOP 소개 - 핵심 기능과 부가 기능 핵심 기능과 부가 기능
애플리케이션 로직은 크게 **핵심 기능**과 **부가 기능**으로 나눌 수 있다.

**핵심 기능**은 해당 객체가 제공하는 고유의 기능이다. 예를 들어서 `OrderService` 의 핵심 기능은 주문 로직이다.

**부가 기능**은 핵심 기능을 보조하기 위해 제공되는 기능이다. 예를 들어서 로그 추적 로직, 트랜잭션 기능이 있다. 

이러한 부가 기능은 단독으로 사용되지 않고, 핵심 기능과 함께 사용된다. 

예를 들어서 로그 추적 기능은 어떤 핵 심 기능이 호출되었는지 로그를 남기기 위해 사용한다. 

그러니까 부가 기능은 이름 그대로 핵심 기능을 보조하기 위해 존재한다.

<img width="695" alt="Screenshot 2024-10-15 at 23 10 53" src="https://github.com/user-attachments/assets/cfc5cebf-c527-4ecc-8a2e-b83d87c4a415">

주문 로직을 실행하기 직전에 로그 추적 기능을 사용해야 하면, 핵심 기능인 주문 로직과 부가 기능인 로그 추적 로직이 하나의 객체 안에 섞여 들어가게 된다. 

부가 기능이 필요한 경우 이렇게 둘을 합해서 하나의 로직을 완성한다. 

이제 주문 서비스를 실행하면 핵심 기능인 주문 로직과 부가 기능인 로그 추적 로직이 함께 실행된다.

<img width="705" alt="Screenshot 2024-10-15 at 23 11 13" src="https://github.com/user-attachments/assets/5600979b-ec12-406a-8095-66858b6704cc">

보통 부가 기능은 여러 클래스에 걸쳐서 함께 사용된다. 예를 들어서 모든 애플리케이션 호출을 로깅 해야 하는 요구사항을 생각해보자. 

이러한 부가 기능은 횡단 관심사(cross-cutting concerns)가 된다. 

쉽게 이야기해서 하나의 부가 기능이 여러 곳에 동일하게 사용된다는 뜻이다.

<img width="696" alt="Screenshot 2024-10-15 at 23 11 26" src="https://github.com/user-attachments/assets/efe4da99-aeb1-4cb4-9c74-0471e1586b12">

### 부가 기능 적용 문제

그런데 이런 부가 기능을 여러 곳에 적용하려면 너무 번거롭다. 예를 들어서 부가 기능을 적용해야 하는 클래스가 100개면 100개 모두에 동일한 코드를 추가해야 한다.

부가 기능을 별도의 유틸리티 클래스로 만든다고 해도, 해당 유틸리티 클래스를 호출하는 코드가 결국 필요하다. 

그리고 부가 기능이 구조적으로 단순 호출이 아니라 `try~catch~finally` 같은 구조가 필요하다면 더욱 복잡해진다. (예: 실행 시간 측정)

더 큰 문제는 수정이다. 만약 부가 기능에 수정이 발생하면, 100개의 클래스 모두를 하나씩 찾아가면서 수정해야 한다. 

여기에 추가로 부가 기능이 적용되는 위치를 변경한다면 어떻게 될까? 

예를 들어서 부가 기능을 모든 컨트롤러, 서비스, 리포지토리에 적용했다가, 로그가 너무 많이 남아서 서비스 계층에만 적용한다고 수정해야하면 어떻게 될까?
또 수 많은 코드를 고쳐야 할 것이다.

**부가 기능 적용 문제를 정리하면 다음과 같다.**

부가 기능을 적용할 때 아주 많은 반복이 필요하다.

부가 기능이 여러 곳에 퍼져서 중복 코드를 만들어낸다. 

부가 기능을 변경할 때 중복 때문에 많은 수정이 필요하다. 

부가 기능의 적용 대상을 변경할 때 많은 수정이 필요하다.

소프트웨어 개발에서 변경 지점은 하나가 될 수 있도록 잘 모듈화 되어야 한다. 

그런데 부가 기능처럼 특정 로직을 애플리케이션 전반에 적용하는 문제는 일반적인 OOP 방식으로는 해결이 어렵다.

## AOP 소개 - 애스펙트






















