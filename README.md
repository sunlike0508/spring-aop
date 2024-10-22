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

핵심 기능과 부가 기능을 분리

누군가는 이러한 부가 기능 도입의 문제점들을 해결하기 위해 오랜기간 고민해왔다.

그 결과 부가 기능을 핵심 기능에서 분리하고 한 곳에서 관리하도록 했다. 

그리고 해당 부가 기능을 어디에 적용할지 선 택하는 기능도 만들었다. 이렇게 부가 기능과 부가 기능을 어디에 적용할지 선택하는 기능을 합해서 하나의 모듈로 만들었는데 이것이 바로 애스펙트(aspect)이다. 

애스펙트는 쉽게 이야기해서 부가 기능과, 해당 부가 기능을 어디에 적용할지 정의한 것이다. 예를 들어서 '로그 출력 기능을 모든 컨트롤러에 적용해라' 라는 것이 정의되어 있다.

그렇다 바로 우리가 이전에 알아본 `@Aspect` 바로 그것이다. 

그리고 스프링이 제공하는 어드바이저도 어드바이스(부 가 기능)과 포인트컷(적용 대상)을 가지고 있어서 개념상 하나의 애스펙트이다.

애스펙트는 우리말로 해석하면 관점이라는 뜻인데, 이름 그대로 애플리케이션을 바라보는 관점을 하나하나의 기능에서 횡단 관심사(cross-cutting concerns) 관점으로 달리 보는 것이다.

이렇게 **애스펙트를 사용한 프로그래밍 방식을 관점 지향 프로그래밍 AOP(Aspect-Oriented Programming)** 이라한다.

**참고로 AOP는 OOP를 대체하기 위한 것이 아니라 횡단 관심사를 깔끔하게 처리하기 어려운 OOP의 부족한 부분을 보조하는 목적으로 개발되었다.**

<img width="700" alt="Screenshot 2024-10-15 at 23 17 18" src="https://github.com/user-attachments/assets/95effd00-d1b3-4489-8709-fb174d0e195e">

**AspectJ 프레임워크**

AOP의 대표적인 구현으로 AspectJ 프레임워크(https://www.eclipse.org/aspectj/)가 있다. 

물론 스프링도 AOP를 지원하지만 대부분 AspectJ의 문법을 차용하고, AspectJ가 제공하는 기능의 일부만 제공한다.

AspectJ 프레임워크는 스스로를 다음과 같이 설명한다. 

* 자바 프로그래밍 언어에 대한 완벽한 관점 지향 확장 
* 횡단 관심사의 깔끔한 모듈화 
  * 오류 검사 및 처리
  * 동기화
  * 성능 최적화(캐싱) 
  * 모니터링 및 로깅

## AOP 적용 방식

AOP를 사용하면 핵심 기능과 부가 기능이 코드상 완전히 분리되어서 관리된다.

그렇다면 AOP를 사용할 때 부가 기능 로직은 어떤 방식으로 실제 로직에 추가될 수 있을까?

**크게 3가지 방법이 있다.** 

* 컴파일 시점
* 클래스 로딩 시점
* 런타임 시점(프록시)
  
### 컴파일 시점

<img width="675" alt="Screenshot 2024-10-15 at 23 21 38" src="https://github.com/user-attachments/assets/46419453-cfb1-4b31-be17-e9a2f09ba5c0">

`.java` 소스 코드를 컴파일러를 사용해서 `.class` 를 만드는 시점에 부가 기능 로직을 추가할 수 있다. 

이때는 AspectJ가 제공하는 특별한 컴파일러를 사용해야 한다. 

컴파일 된 `.class` 를 디컴파일 해보면 애스펙트 관련 호출 코드가 들어간다. 

이해하기 쉽게 풀어서 이야기하면 부가 기능 코드가 핵심 기능이 있는 컴파일된 코드 주변에 실제로 붙어 버린다고 생각하면 된다. 

AspectJ 컴파일러는 Aspect를 확인해서 해당 클래스가 적용 대상인지 먼저 확인하고, 적용 대상인 경우에 부가 기능 로직을 적용한다.

참고로 이렇게 원본 로직에 부가 기능 로직이 추가되는 것을 위빙(Weaving)이라 한다.

위빙(Weaving): 옷감을 짜다. 직조하다. 애스펙트와 실제 코드를 연결해서 붙이는 것

**컴파일 시점 - 단점**

컴파일 시점에 부가 기능을 적용하려면 특별한 컴파일러도 필요하고 복잡하다.

### 클래스 로딩 시점

<img width="683" alt="Screenshot 2024-10-15 at 23 23 31" src="https://github.com/user-attachments/assets/f69c585d-db51-48c5-ae7c-71e3111e7653">

자바를 실행하면 자바 언어는 `.class` 파일을 JVM 내부의 클래스 로더에 보관한다. 

이때 중간에서 `.class` 파일을 조작한 다음 JVM에 올릴 수 있다. 

자바 언어는 `.class` 를 JVM에 저장하기 전에 조작할 수 있는 기능을 제공한다. 궁금한 분은 java Instrumentation를 검색해보자. 

참고로 수 많은 모니터링 툴들이 이 방식을 사용한다. 

이 시점에 애스펙트를 적용하는 것을 로드 타임 위빙이라 한다.

**클래스 로딩 시점 - 단점**

로드 타임 위빙은 자바를 실행할 때 특별한 옵션( `java -javaagent` )을 통해 클래스 로더 조작기를 지정해야 하는데, 이 부분이 번거롭고 운영하기 어렵다.

### 런타임 시점

<img width="681" alt="Screenshot 2024-10-15 at 23 25 33" src="https://github.com/user-attachments/assets/c04f6bce-7fe6-4771-a584-8d0a510eef8d">

런타임 시점은 컴파일도 다 끝나고, 클래스 로더에 클래스도 다 올라가서 이미 자바가 실행되고 난 다음을 말한다. 

자바의 메인( `main` ) 메서드가 이미 실행된 다음이다. 

따라서 자바 언어가 제공하는 범위 안에서 부가 기능을 적용해야 한다. 

스프링과 같은 컨테이너의 도움을 받고 프록시와 DI, 빈 포스트 프로세서 같은 개념들을 총 동원해야 한다. 

이렇게 하면 최종적으로 프록시를 통해 스프링 빈에 부가 기능을 적용할 수 있다. 

그렇다. 지금까지 우리가 학습한 것이 바로 프록시 방식의 AOP이다.

프록시를 사용하기 때문에 AOP 기능에 일부 제약이 있다. 

하지만 특별한 컴파일러나, 자바를 실행할 때 복잡한 옵션과 클래스 로더 조작기를 설정하지 않아도 된다. 

스프링만 있으면 얼마든지 AOP를 적용할 수 있다.

**부가 기능이 적용되는 차이를 정리하면 다음과 같다.**

* 컴파일 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다.
* 클래스 로딩 시점: 실제 대상 코드에 애스팩트를 통한 부가 기능 호출 코드가 포함된다. AspectJ를 직접 사용해야 한다.
* 런타임 시점: 실제 대상 코드는 그대로 유지된다. 대신에 프록시를 통해 부가 기능이 적용된다. 따라서 항상 프록시를 통해야 부가 기능을 사용할 수 있다. 스프링 AOP는 이 방식을 사용한다.

**AOP 적용 위치**

* AOP는 지금까지 학습한 메서드 실행 위치 뿐만 아니라 다음과 같은 다양한 위치에 적용할 수 있다.
 * 적용 가능 지점(조인 포인트): 생성자, 필드 값 접근, static 메서드 접근, 메서드 실행.
 * 이렇게 AOP를 적용할 수 있는 지점을 조인 포인트(Join point)라 한다.
 * AspectJ를 사용해서 컴파일 시점과 클래스 로딩 시점에 적용하는 AOP는 바이트코드를 실제 조작하기 때문에 해당 기능을 모든 지점에 다 적용할 수 있다.

* 프록시 방식을 사용하는 스프링 AOP는 메서드 실행 지점에만 AOP를 적용할 수 있다.
 * 프록시는 메서드 오버라이딩 개념으로 동작한다. 따라서 생성자나 static 메서드, 필드 값 접근에는 프록시 개념이 적용될 수 없다.
 * 프록시를 사용하는 **스프링 AOP의 조인 포인트는 메서드 실행으로 제한** 된다.
 * 프록시 방식을 사용하는 스프링 AOP는 스프링 컨테이너가 관리할 수 있는 **스프링 빈에만 AOP를 적용** 할 수 있다.

그니까 기본적인 AOP는 생성자, 필드 값 접근, static, 메서드 실행 모두 접근이 가능하다. 왜? 컴파일, 를래스 로딩 시점에도 AOP가 적용되니까

근데 프록시 AOP 방식은 메서드 실행 지점에서만 적용 가능하다. 

생성자, final은 당연히 프록시가 상속을 받아 사용하기 때문에 사용 불가.

필드 값 접근은 프록시 객체가 실제 객체를 접근할수가 없기 때문에 안된다.

static은 호출 시 클래스 로딩 시점에 메모리에 올라가 있기 때문에 aop가 적용이 불가.

**참고**

스프링은 AspectJ의 문법을 차용하고 프록시 방식의 AOP를 적용한다. AspectJ를 직접 사용하는 것이 아니다.

**중요**

스프링이 제공하는 AOP는 프록시를 사용한다. 

따라서 프록시를 통해 메서드를 실행하는 시점에만 AOP가 적용 된다. 

AspectJ를 사용하면 앞서 설명한 것 처럼 더 복잡하고 더 다양한 기능을 사용할 수 있다.

그렇다면 스프링 AOP 보다는 더 기능이 많은 AspectJ를 직접 사용해서 AOP를 적용하는 것이 더 좋지 않을까?

AspectJ를 사용하려면 공부할 내용도 많고, 자바 관련 설정(특별한 컴파일러, AspectJ 전용 문법, 자바 실행 옵션)도 복잡하다. 

반면에 스프링 AOP는 별도의 추가 자바 설정 없이 스프링만 있으면 편리하게 AOP를 사용할 수 있다. 

실무에서는 스프링이 제공하는 AOP 기능만 사용해도 대부문의 문제를 해결할 수 있다. 

따라서 스프링 AOP가 제공하는 기능을 학습하는 것에 집중하자.

## AOP 용어 정리

<img width="699" alt="Screenshot 2024-10-16 at 00 00 45" src="https://github.com/user-attachments/assets/79cb937b-b152-4150-bb4f-8368251a6df5">

* 조인 포인트(Join point)
  * 어드바이스가 적용될 수 있는 위치, 메소드 실행, 생성자 호출, 필드 값 접근, static 메서드 접근 같은 프로 그램 실행 중 지점
  * 조인 포인트는 추상적인 개념이다. AOP를 적용할 수 있는 모든 지점이라 생각하면 된다.
  * 스프링 AOP는 프록시 방식을 사용하므로 조인 포인트는 항상 메소드 실행 지점으로 제한된다.

* 포인트컷(Pointcut)  
  * 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
  * 주로 AspectJ 표현식을 사용해서 지정
  * 프록시를 사용하는 스프링 AOP는 메서드 실행 지점만 포인트컷으로 선별 가능

* 타켓(Target)
  * 어드바이스를 받는 객체, 포인트컷으로 결정

* 어드바이스(Advice) 부가 기능
  * 특정 조인 포인트에서 Aspect에 의해 취해지는 조치
  * Around(주변), Before(전), After(후)와 같은 다양한 종류의 어드바이스가 있음

* 애스펙트(Aspect)
  * 어드바이스 + 포인트컷을 모듈화 한 것 
  * `@Aspect` 를 생각하면 됨
  * 여러 어드바이스와 포인트 컷이 함께 존재

* 어드바이저(Advisor)
  * 하나의 어드바이스와 하나의 포인트 컷으로 구성 스프링 AOP에서만 사용되는 특별한 용어

* 위빙(Weaving
  * 포인트컷으로 결정한 타켓의 조인 포인트에 어드바이스를 적용하는 것 
  * 위빙을 통해 핵심 기능 코드에 영향을 주지 않고 부가 기능을 추가 할 수 있음 
  * AOP 적용을 위해 애스펙트를 객체에 연결한 상태
    * 컴파일 타임(AspectJ compiler)
    * 로드 타임
    * 런타임, 스프링 AOP는 런타임, 프록시 방식

* AOP 프록시
  * AOP 기능을 구현하기 위해 만든 프록시 객체, 스프링에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시이다.

## 구현 

```java
@Around("execution(* hello.aop.order..*(..))")
public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
    
    return joinPoint.proceed();
}
```

`@Around` 애노테이션의 값인 `execution(* hello.aop.order..*(..))` 는 포인트컷이 된다. 

`@Around` 애노테이션의 메서드인 `doLog` 는 어드바이스( `Advice` )가 된다.

`execution(* hello.aop.order..*(..))` 는 `hello.aop.order` 패키지와 그 하위 패키지( `..` )를 지정하는 AspectJ 포인트컷 표현식이다. 

앞으로는 간단히 포인트컷 표현식이라 하겠다. 참고로 표인트컷 표현식은 뒤에서 자세히 설명하겠다.

이제 `OrderService` , `OrderRepository` 의 모든 메서드는 AOP 적용의 대상이 된다. 

참고로 스프링은 프록시 방식의 AOP를 사용하므로 프록시를 통하는 메서드만 적용 대상이 된다.

**참고**

스프링 AOP는 AspectJ의 문법을 차용하고, 프록시 방식의 AOP를 제공한다. AspectJ를 직접 사용하는 것이 아니다.

스프링 AOP를 사용할 때는 `@Aspect` 애노테이션을 주로 사용하는데, 이 애노테이션도 AspectJ가 제공하는 애노테이션이다.

`@Aspect` 를 포함한 `org.aspectj` 패키지 관련 기능은 `aspectjweaver.jar` 라이브러리가 제공하는 기능이다. 

앞서 `build.gradle` 에 `spring-boot-starter-aop` 를 포함했는데, 이렇게 하면 스프링의 AOP 관련 기능과 함께 `aspectjweaver.jar` 도 함께 사용할 수 있게 의존 관계에 포함된다.

그런데 스프링에서는 AspectJ가 제공하는 애노테이션이나 관련 인터페이스만 사용하는 것이고, 실제 AspectJ 가 제공하는 컴파일, 로드타임 위버 등을 사용하는 것은 아니다. 

스프링은 지금까지 우리가 학습한 것처럼 프록시 방식의 AOP를 사용한다.

`@Aspect` 는 애스펙트라는 표식이지 컴포넌트 스캔이 되는 것은 아니다. 

따라서 `AspectV1` 를 AOP로 사용하려면 스 프링 빈으로 등록해야 한다.

스프링 빈으로 등록하는 방법은 다음과 같다.

1. `@Bean` 을 사용해서 직접 등록
2. `@Component` 컴포넌트 스캔을 사용해서 자동 등록
3. `@Import` 주로 설정 파일을 추가할 때 사용( `@Configuration` )

 
`@Import` 는 주로 설정 파일을 추가할 때 사용하지만, 이 기능으로 스프링 빈도 등록할 수 있다.

## 포인트컷 분리

```java
@Aspect
public class AspectV2 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // 포인트컷 시그니처

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처

        return joinPoint.proceed();
    }
}
```

**@Pointcut**

`@Pointcut` 에 포인트컷 표현식을 사용한다.

메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처(signature)라 한다.

메서드의 반환 타입은 `void` 여야 한다.

코드 내용은 비워둔다.

포인트컷 시그니처는 `allOrder()` 이다. 

이름 그대로 주문과 관련된 모든 기능을 대상으로 하는 포인트컷이다.

`@Around` 어드바이스에서는 포인트컷을 직접 지정해도 되지만, 포인트컷 시그니처를 사용해도 된다. 

여기서는 `@Around("allOrder()")` 를 사용한다.

`private` , `public` 같은 접근 제어자는 내부에서만 사용하면 `private` 을 사용해도 되지만, 다른 애스팩트에서 참고하려면 `public` 을 사용해야 한다.

결과적으로 `AspectV1` 과 같은 기능을 수행한다. 

이렇게 분리하면 하나의 포인트컷 표현식을 여러 어드바이스에서 함께 사용할 수 있다. 

그리고 뒤에 설명하겠지만 다른 클래스에 있는 외부 어드바이스에서도 포인트컷을 함께 사용할 수 있다.

## 어드바이스 추가

이번에는 조금 복잡한 예제를 만들어보자.

앞서 로그를 출력하는 기능에 추가로 트랜잭션을 적용하는 코드도 추가해보자. 

여기서는 진짜 트랜잭션을 실행하는 것은 아니다. 기능이 동작한 것처럼 로그만 남기겠다.

트랜잭션 기능은 보통 다음과 같이 동작한다. 

핵심 로직 실행 직전에 트랜잭션을 시작 핵심 로직 실행 핵심 로직 실행에 문제가 없으면 커밋 핵심 로직 실행에 예외가 발생하면 롤백

```java
@Aspect
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // 포인트컷 시그니처

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처

        return joinPoint.proceed();
    }

    // hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
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
```

* `allOrder()` 포인트컷은 `hello.aop.order` 패키지와 하위 패키지를 대상으로 한다. 

* `allService()` 포인트컷은 타입 이름 패턴이 `*Service` 를 대상으로 하는데 쉽게 이야기해서 `XxxService` 처럼 `Service` 로 끝나는 것을 대상으로 한다. `*Servi*` 과 같은 패턴도 가능하다.

여기서 타입 이름 패턴이라고 한 이유는 클래스, 인터페이스에 모두 적용되기 때문이다.

* `@Around("allOrder() && allService()")`
  * 포인트컷은 이렇게 조합할 수 있다. `&&` (AND), `||` (OR), `!` (NOT) 3가지 조합이 가능하다. 
  * `hello.aop.order` 패키지와 하위 패키지 이면서 타입 이름 패턴이 `*Service` 인 것을 대상으로 한다. 결과적으로 
  `doTransaction()` 어드바이스는 `OrderService` 에만 적용된다.
  * `doLog()` 어드바이스는 `OrderService` , `OrderRepository` 에 모두 적용된다.

**포인트컷이 적용된 AOP 결과는 다음과 같다.**

`orderService` : `doLog()` , `doTransaction()` 어드바이스 적용 

`orderRepository` : `doLog()` 어드바이스 적용

그런데여기에서로그를남기는순서가 [`doLog()` `doTransaction()` ] 순서로작동한다.만약어드바이스가 적용되는 순서를 변경하고 싶으면 어떻게 하면 될까? 

예를 들어서 실행 시간을 측정해야 하는데 트랜잭션과 관련된 시간을 제외하고 측정하고 싶다면 [`doTransaction()` `doLog()`] 이렇게 트랜잭션 이후에 로그를 남겨야 할 것이다.

그 전에 잠깐 포인트컷을 외부로 빼서 사용하는 방법을 먼저 알아보자.

## 포인트컷 참조

```java
@Aspect
public class Pointcuts {

    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {} // 포인트컷 시그니처

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}

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
```
### 어드바이스 순서

어드바이스는 기본적으로 순서를 보장하지 않는다. 

순서를 지정하고 싶으면 `@Aspect` 적용 단위로 `org.springframework.core.annotation.@Order` 애노테이션을 적용해야 한다. 

문제는 이것을 어드바이스 단위가 아니라 클래스 단위로 적용할 수 있다는 점이다. 

그래서 지금처럼 하나의 애스펙트에 여러 어드바이스가 있으면 순서를 보장 받을 수 없다. 

따라서 **애스펙트를 별도의 클래스로 분리**해야 한다.

```java
@Aspect
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처

            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
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
}
```

하나의 애스펙트 안에 있던 어드바이스를 `LogAspect` , `TxAspect` 애스펙트로 각각 분리했다. 

그리고 각 애스펙트에 `@Order` 애노테이션을 통해 실행 순서를 적용했다. 

참고로 숫자가 작을 수록 먼저 실행된다.

## 어드바이스 종류

어드바이스는 앞서 살펴본 `@Around` 외에도 여러가지 종류가 있다.

**어드바이스 종류**

`@Around` : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능

`@Before` : 조인 포인트 실행 이전에 실행

`@AfterReturning` : 조인 포인트가 정상 완료후 실행 

`@AfterThrowing` : 메서드가 예외를 던지는 경우 실행

`@After` : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)

근데 Around만 있어도 된다. 나머지는 아래 코드로 표현되기 때문이다.

```java
@Around("hello.aop.order.aop.Pointcuts.orderAndService()")
public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
        //@Before
        log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature()); 
        
        Object result = joinPoint.proceed();
        
        //@AfterReturning
        log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature()); 
        
        return result;
    } catch (Exception e) {
        //@AfterThrowing
        log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
        throw e;
    } finally {
        //@After
        log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature()); 
    }
}


@Before("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doBefore(JoinPoint joinPoint) {
  log.info("[before] {}", joinPoint.getSignature());
}

@AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
public void doAfterReturning(JoinPoint joinPoint, Object result) {
  log.info("[AfterReturning] {} return={}", joinPoint.getSignature(), result);
}


@AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
  log.info("[AfterThrowing] {} message={}", ex);
}


@After("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doAfter(JoinPoint joinPoint) {
  log.info("[After] {}", joinPoint.getSignature());
}
```

#### 참고 정보 획득

모든 어드바이스는 `org.aspectj.lang.JoinPoint` 를 첫번째 파라미터에 사용할 수 있다. (생략해도 된다.) 

단 `@Around` 는 `ProceedingJoinPoint` 을 사용해야 한다.

참고로 `ProceedingJoinPoint` 는 `org.aspectj.lang.JoinPoint` 의 하위 타입이다.

* **JoinPoint 인터페이스의 주요 기능**

`getArgs()` : 메서드 인수를 반환합니다.

`getThis()` : 프록시 객체를 반환합니다.

`getTarget()` : 대상 객체를 반환합니다.

`getSignature()` : 조언되는 메서드에 대한 설명을 반환합니다. 

`toString()` : 조언되는 방법에 대한 유용한 설명을 인쇄합니다.

**ProceedingJoinPoint 인터페이스의 주요 기능** 

`proceed()` : 다음 어드바이스나 타켓을 호출한다.

추가로 호출시 전달한 매개변수를 파라미터를 통해서도 전달 받을 수도 있는데, 이 부분은 뒤에서 설명한다.

### 어드바이스 종류

#### @Before

조인 포인트 실행 전
```java
 @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
 public void doBefore(JoinPoint joinPoint) {
     log.info("[before] {}", joinPoint.getSignature());
 }
```
`@Around` 와 다르게 작업 흐름을 변경할 수는 없다.

`@Around` 는 `ProceedingJoinPoint.proceed()` 를 호출해야 다음 대상이 호출된다. 

만약 호출하지 않으면 다음 대상이 호출되지 않는다. 

반면에 `@Before` 는 `ProceedingJoinPoint.proceed()` 자체를 사용하지 않는다. 

메서드 종료시 자동으로 다음 타켓이 호출된다. 물론 예외가 발생하면 다음 코드가 호출되지는 않는다.

#### **@AfterReturning**

메서드 실행이 정상적으로 반환될 때 실행

```java
@AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()",
returning = "result")
public void doReturn(JoinPoint joinPoint, Object result) {
   log.info("[return] {} return={}", joinPoint.getSignature(), result);
}
```

`returning` 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.

`returning` 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행한다. (부모 타입을 지정하면 모든 자식 타입은 인정된다.)

`@Around` 와 다르게 반환되는 객체를 변경할 수는 없다. 

반환 객체를 변경하려면 `@Around` 를 사용해야 한다. 참고로 반환 객체를 조작할 수 는 있다.

#### **@AfterThrowing**

메서드 실행이 예외를 던져서 종료될 때 실행

```java
@AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()",
throwing = "ex")
public void doThrowing(JoinPoint joinPoint, Exception ex) {
   log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
}
```

`throwing` 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.

`throwing` 절에 지정된 타입과 맞는 예외를 대상으로 실행한다. (부모 타입을 지정하면 모든 자식 타입은 인정된다.)

#### **@After**

메서드 실행이 종료되면 실행된다. (finally를 생각하면 된다.) 정상 및 예외 반환 조건을 모두 처리한다.

일반적으로 리소스를 해제하는 데 사용한다.

#### **@Around**

* 메서드의 실행의 주변에서 실행된다. 메서드 실행 전후에 작업을 수행한다.
* 가장 강력한 어드바이스
* 조인 포인트 실행 여부 선택 `joinPoint.proceed() 호출 여부 선택` 전달 값 변환: `joinPoint.proceed(args[])`
  * 반환 값 변환
  * 예외 변환
  * 트랜잭션 처럼 `try ~ catch~ finally` 모두 들어가는 구문 처리 가능
* 어드바이스의 첫 번째 파라미터는 `ProceedingJoinPoint` 를 사용해야 한다.
* `proceed()` 를 통해 대상을 실행한다. 
* `proceed()` 를 여러번 실행할 수도 있음(재시도)

<img width="702" alt="Screenshot 2024-10-17 at 23 32 46" src="https://github.com/user-attachments/assets/9190fa99-b8bb-4653-8038-1b157a9ce39e">

**순서**

스프링은 5.2.7 버전부터 동일한 `@Aspect` 안에서 동일한 조인포인트의 우선순위를 정했다.

실행 순서: `@Around` , `@Before` , `@After` , `@AfterReturning` , `@AfterThrowing`

어드바이스가 적용되는 순서는 이렇게 적용되지만, 호출 순서와 리턴 순서는 반대라는 점을 알아두자.

물론 `@Aspect` 안에 동일한 종류의 어드바이스가 2개 있으면 순서가 보장되지 않는다. 

이 경우 앞서 배운 것 처 럼 `@Aspect` 를 분리하고 `@Order` 를 적용하자.

**@Around 외에 다른 어드바이스가 존재하는 이유**

`@Around` 하나만 있어도 모든 기능을 수행할 수 있다. 그런데 다른 어드바이스들이 존재하는 이유는 무엇일까?

다음 코드를 보자. 

```java
@Around("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doBefore(ProceedingJoinPoint joinPoint) {
    log.info("[before] {}", joinPoint.getSignature());
}
```

이 코드의 문제점을 찾을 수 있겠는가? 이 코드는 타켓을 호출하지 않는 문제가 있다.

이 코드를 개발한 의도는 타켓 실행 전에 로그를 출력하는 것이다. 

그런데 `@Around` 는 항상 `joinPoint.proceed()` 를 호출해야 한다. 

만약 실수로 호출하지 않으면 타켓이 호출되지 않는 치명적인 버그가 발생한다.

다음 코드를 보자. 

```java
@Before("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doBefore(JoinPoint joinPoint) {
   log.info("[before] {}", joinPoint.getSignature());
}
```

`@Before` 는 `joinPoint.proceed()` 를 호출하는 고민을 하지 않아도 된다.

`@Around` 가 가장 넓은 기능을 제공하는 것은 맞지만, 실수할 가능성이 있다. 

반면에 `@Before` , `@After` 같은 어드 바이스는 기능은 적지만 실수할 가능성이 낮고, 코드도 단순하다. 

그리고 가장 중요한 점이 있는데, 바로 이 코드를 작성 한 의도가 명확하게 드러난다는 점이다. 

`@Before` 라는 애노테이션을 보는 순간 아~ 이 코드는 타켓 실행 전에 한정해 서 어떤 일을 하는 코드구나 라는 것이 드러난다.

**좋은 설계는 제약이 있는 것이다**

좋은 설계는 제약이 있는 것이다. 

`@Around` 만 있으면 되는데 왜? 이렇게 제약을 두는가? 제약은 실수를 미연에 방지한다. 

일종의 가이드 역할을 한다. 

만약 `@Around` 를 사용했는데, 중간에 다른 개발자가 해당 코드를 수정해서 호출하지 않았다면? 큰 장애가 발생했을 것이다. 

처음부터 `@Before` 를 사용했다면 이런 문제 자체가 발생하지 않는다.

제약 덕분에 역할이 명확해진다. 

다른 개발자도 이 코드를 보고 고민해야 하는 범위가 줄어들고 코드의 의도도 파악하기 쉽다.

# 스프링 AOP - 포인트 컷

## 포인트컷 지시자

지금부터 포인트컷 표현식을 포함한 포인트컷에 대해서 자세히 알아보자.

애스펙트J는 포인트컷을 편리하게 표현하기 위한 특별한 표현식을 제공한다.

예) `@Pointcut("execution(* hello.aop.order..*(..))")`

포인트컷 표현식은 AspectJ pointcut expression 즉 애스펙트J가 제공하는 포인트컷 표현식을 줄여서 말하는 것이다.

### 포인트컷 지시자

* 포인트컷 표현식은 `execution` 같은 포인트컷 지시자(Pointcut Designator)로 시작한다. 줄여서 PCD라 한다.

* 포인트컷 지시자의 종류
  * `execution` : 메소드 실행 조인 포인트를 매칭한다. 스프링 AOP에서 가장 많이 사용하고, 기능도 복잡하다.
  * `within` : 특정 타입 내의 조인 포인트를 매칭한다.
  * `args` : 인자가 주어진 타입의 인스턴스인 조인 포인트
  * `this` : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
  * `target` : Target 객체(스프링 AOP 프록시가 가리키는 실제 대상)를 대상으로 하는 조인 포인트
  * `@target` : 실행 객체의 클래스에 주어진 타입의 애노테이션이 있는 조인 포인트 
  * `@within` : 주어진 애노테이션이 있는 타입 내 조인 포인트
  * `@annotation` : 메서드가 주어진 애노테이션을 가지고 있는 조인 포인트를 매칭
  * `@args` : 전달된 실제 인수의 런타임 타입이 주어진 타입의 애노테이션을 갖는 조인 포인트 
  * `bean` : 스프링 전용 포인트컷 지시자, 빈의 이름으로 포인트컷을 지정한다.

포인트컷 지시자가 무엇을 뜻하는지, 사실 글로만 읽어보면 이해하기 쉽지 않다. 

예제를 통해서 하나씩 이해해보자. `execution` 은 가장 많이 사용하고, 나머지는 자주 사용하지 않는다. 

따라서 `execution` 을 중점적으로 이해하자.

## Execution

**execution 문법** 
```
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)

execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?) 
```

메소드 실행 조인 포인트를 매칭한다. 

?는 생략할 수 있다.

`*` 같은 패턴을 지정할 수 있다.

**매칭 조건**

접근제어자?: `public`

반환타입: `String`

선언타입?: `hello.aop.member.MemberServiceImpl` 

메서드이름: `hello`

파라미터: `(String)`

예외?: 생략

**가장 많이 생략한 포인트컷** 

```java
@Test
void allMatch() {
    pointcut.setExpression("execution(* *(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}
```

가장 많이 생략한 포인트컷이다.

**매칭 조건**

* 접근제어자?: 생략
* 반환타입: `*`
* 선언타입?: 생략 
* 메서드이름: `*` 
* 파라미터: `(..)` 
* 예외?: 없음
* `*` 은 아무 값이 들어와도 된다는 뜻이다.
* 파라미터에서 `..` 은 파라미터의 타입과 파라미터 수가 상관없다는 뜻이다. ( `0..*` ) 파라미터는 뒤에 자세히 정리하겠다.
  

**hello.aop.member.*(1).*(2)**
* (1): 타입
* (2): 메서드 이름

**패키지에서 `.` , `..` 의 차이를 이해해야 한다.**
* `.` : 정확하게 해당 위치의 패키지
* `..` : 해당 위치의 패키지와 그 하위 패키지도 포함

**타입 매칭 - 부모 타입 허용** 

```java
@Test
void typeExactMatch() {
  pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
  assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}
@Test
void typeMatchSuperType() {
  pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
  assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}
```

`typeExactMatch()` 는 타입 정보가 정확하게 일치하기 때문에 매칭된다.

`typeMatchSuperType()` 을 주의해서 보아야 한다.

`execution` 에서는 `MemberService` 처럼 부모 타입을 선언해도 그 자식 타입은 매칭된다. 

다형성에서 `부모타입 = 자식타입` 이 할당 가능하다는 점을 떠올려보면 된다.

**타입 매칭 - 부모 타입에 있는 메서드만 허용** 

```java
@Test
void typeExactSuperMatch2() throws NoSuchMethodException {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

    assertThat(pointcut.matches(internalMethod, MemberService.class)).isTrue();
}

@Test
void typeExactSuperMatch3() throws NoSuchMethodException {
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

    assertThat(pointcut.matches(internalMethod, MemberService.class)).isFalse();
}
```

`typeMatchInternal()` 의 경우 `MemberServiceImpl` 를 표현식에 선언했기 때문에 그 안에 있는 `internal(String)` 메서드도 매칭 대상이 된다.

`typeMatchNoSuperTypeMethodFalse()` 를 주의해서 보아야 한다.

이 경우 표현식에 부모 타입인 `MemberService` 를 선언했다. 

그런데 자식 타입인 `MemberServiceImpl` 의 `internal(String)` 메서드를 매칭하려 한다. 

이 경우 매칭에 실패한다. 

`MemberService` 에는 `internal(String)` 메서드가 없다!

부모 타입을 표현식에 선언한 경우 부모 타입에서 선언한 메서드가 자식 타입에 있어야 매칭에 성공한다. 

그래서 부모 타입에 있는 `hello(String)` 메서드는 매칭에 성공하지만, 부모 타입에 없는 `internal(String)` 는 매칭에 실 패한다.

**파라미터 매칭**

```java
@Test
void argsMatch() {
    pointcut.setExpression("execution(* *(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}

@Test
void argsMatch2() {
    pointcut.setExpression("execution(* *(String))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}

@Test
void argsMatch3() {
    pointcut.setExpression("execution(* *())");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
}

@Test
void argsMatch4() {
    pointcut.setExpression("execution(* *(*))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}

@Test
void argsMatch5() {
    pointcut.setExpression("execution(* *(String, ..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
}

@Test
void argsMatch6() {
    pointcut.setExpression("execution(* *(String, *))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
}
```

**execution 파라미터 매칭 규칙은 다음과 같다.**

`(String)` : 정확하게 String 타입 파라미터

`()` : 파라미터가 없어야 한다.

`(*)` : 정확히 하나의 파라미터, 단 모든 타입을 허용한다.

`(*, *)` : 정확히 두 개의 파라미터, 단 모든 타입을 허용한다.

`(..)` : 숫자와 무관하게 모든 파라미터, 모든 타입을 허용한다. 참고로 파라미터가 없어도 된다. `0..*` 로 이해하면 된다.

`(String, ..)` : String 타입으로 시작해야 한다. 숫자와 무관하게 모든 파라미터, 모든 타입을 허용한다.

예) `(String)` , `(String, Xxx)` , `(String, Xxx, Xxx)` 허용

## within

```java
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
```

`within` 지시자는 특정 타입 내의 조인 포인트들로 매칭을 제한한다. 

쉽게 이야기해서 해당 타입이 매칭되면 그 안의 메서드(조인 포인트)들이 자동으로 매칭된다.

문법은 단순한데 `execution` 에서 타입 부분만 사용한다고 보면 된다.

**주의**

```java
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
```

그런데 `within` 사용시 주의해야 할 점이 있다. 

표현식에 부모 타입을 지정하면 안된다는 점이다. 

정확하게 타입이 맞 아야 한다. 이 부분에서 `execution` 과 차이가 난다.

## args

`args` : 인자가 주어진 타입의 인스턴스인 조인 포인트로 매칭 

기본 문법은 `execution` 의 `args` 부분과 같다.

**execution과 args의 차이점**

`execution` 은 파라미터 타입이 정확하게 매칭되어야 한다. 

`execution` 은 클래스에 선언된 정보를 기반으로 판단한다.

`args` 는 부모 타입을 허용한다. 

`args` 는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.

`args` 지시자는 단독으로 사용되기 보다는 뒤에서 설명할 파라미터 바인딩에서 주로 사용된다.

## @target, @within

**정의**

`@target` : 실행 객체의 클래스에 주어진 타입의 애노테이션이 있는 조인 포인트 `@within` : 주어진 애노테이션이 있는 타입 내 조인 포인트

**설명**

`@target` , `@within` 은 다음과 같이 타입에 있는 애노테이션으로 AOP 적용 여부를 판단한다.

`@target(hello.aop.member.annotation.ClassAop)` `@within(hello.aop.member.annotation.ClassAop)`

```java
@ClassAop
class Target{}
```

**@target vs @within**

`@target` 은 인스턴스의 모든 메서드를 조인 포인트로 적용한다. 

`@within` 은 해당 타입 내에 있는 메서드만 조인 포인트로 적용한다.

쉽게 이야기해서 `@target` 은 부모 클래스의 메서드까지 어드바이스를 다 적용하고, `@within` 은 자기 자신의 클래스 에 정의된 메서드에만 어드바이스를 적용한다.

<img width="682" alt="Screenshot 2024-10-20 at 15 30 34" src="https://github.com/user-attachments/assets/2ea204da-e4bc-442b-b281-00a6bf7f10ec">

**주의**

다음 포인트컷 지시자는 단독으로 사용하면 안된다. `args, @args, @target`

스프링 컨테이너가 프록시를 생성하는 시점은 스프링 컨테이너가 만들어지는 애플리케이션 로딩 시점이다. 

이때 final 같은 빈들은 프록시를 생성하지 않는다.

포인트컷 지시자(`args` , `@args` , `@target`)는 애플리케이션이 로딩 -> 실행 후 실제 객체 인스턴스가 생성이 실행될 때 프록시가 있어야 어드바이스 적용 여부를 확인할 수 있다.

그런데 스프링은 포인트컷 지시자(`args` , `@args` , `@target`)가 있으면 모든 빈에 AOP를 적용하려고 시도한다. (왜냐하면 범위를 제한하지 않았으니까)

그러나 final이 붙은 빈에는 프록시가 없기 때문에 오류가 발생한다. 

(프록시의 특징, final이 붙은 클래스나 메소드에는 프록시를 생성하지 않는다. 프록시는 기본적으로 상속을 통해서 생성되는데 final이 붙으면 상속이 불가능하므로)

그래서 스프링에서는 애플리케이션 로딩 시점에 단독으로 사용되는 포인트컷 지시자에 에러를 발생시킨다.

따라서 이러한 표현식은 최대한 프록시 적용 대상을 축소하는 표현식과 함께 사용해야 한다.

## @annotation, @args @annotation


###@annotation

**정의**

`@annotation` : 메서드가 주어진 애노테이션을 가지고 있는 조인 포인트를 매칭

### @args

**정의**
`@args` : 전달된 실제 인수의 런타임 타입이 주어진 타입의 애노테이션을 갖는 조인 포인트

**설명**

전달된 인수의 런타임 타입에 `@Check` 애노테이션이 있는 경우에 매칭한다.

`@args(test.Check)`

## bean

**정의**

`bean` : 스프링 전용 포인트컷 지시자, 빈의 이름으로 지정한다.

**설명**

스프링 빈의 이름으로 AOP 적용 여부를 지정한다. 

이것은 스프링에서만 사용할 수 있는 특별한 지시자이다. 

`bean(orderService) || bean(*Repository)`

`*` 과 같은 패턴을 사용할 수 있다.

## 매개변수 전달

다음은 포인트컷 표현식을 사용해서 어드바이스에 매개변수를 전달할 수 있다. 

**this, target, args,@target, @within, @annotation, @args**

```java
@Before("allMember() && args(arg,..)")
public void logArgs3(String arg) {
    log.info("[logArgs3] arg={}", arg);
}
```
포인트컷의 이름과 매개변수의 이름을 맞추어야 한다. `arg` 로 맞추었다.

추가로 타입이 메서드에 지정한 타입으로 제한된다. 

여기서는 메서드의 타입이 `String` 으로 되어 있기 때문에 다음과 같이 정의되는 것으로 이해하면 된다.

`args(arg,..)` `args(String,..)`

## this, target

**정의**

`this` : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트

`target` : Target 객체(스프링 AOP 프록시가 가리키는 실제 대상)를 대상으로 하는 조인 포인트

**설명**

`this` , `target` 은 다음과 같이 적용 타입 하나를 정확하게 지정해야 한다.

```java
this(hello.aop.member.MemberService)
target(hello.aop.member.MemberService) 
```

`*` 같은 패턴을 사용할 수 없다. 부모 타입을 허용한다.

**this vs target**

단순히 타입 하나를 정하면 되는데, `this` 와 `target` 은 어떤 차이가 있을까?

스프링에서 AOP를 적용하면 실제 `target` 객체 대신에 프록시 객체가 스프링 빈으로 등록된다. 

`this` 는 스프링 빈으로 등록되어 있는 **프록시 객체**를 대상으로 포인트컷을 매칭한다. 

`target` 은 실제 **target 객체**를 대상으로 포인트컷을 매칭한다.

**프록시 생성 방식에 따른 차이**

스프링은 프록시를 생성할 때 JDK 동적 프록시와 CGLIB를 선택할 수 있다. 

둘의 프록시를 생성하는 방식이 다르기 때 문에 차이가 발생한다.

JDK 동적 프록시: 인터페이스가 필수이고, 인터페이스를 구현한 프록시 객체를 생성한다.

CGLIB: 인터페이스가 있어도 구체 클래스를 상속 받아서 프록시 객체를 생성한다.

### **JDK 동적 프록시**

<img width="684" alt="Screenshot 2024-10-20 at 16 50 58" src="https://github.com/user-attachments/assets/453f0eaa-d78a-47bb-9e79-887f37128c22">

먼저 JDK 동적 프록시를 적용했을 때 `this` , `target` 을 알아보자.

**MemberService 인터페이스 지정** 

`this(hello.aop.member.MemberService)`

proxy 객체를 보고 판단한다. 

`this` 는 부모 타입을 허용하기 때문에 AOP가 적용된다. 

`target(hello.aop.member.MemberService)`

target 객체를 보고 판단한다. 

`target` 은 부모 타입을 허용하기 때문에 AOP가 적용된다.

**MemberServiceImpl 구체 클래스 지정**

`this(hello.aop.member.MemberServiceImpl)` : proxy 객체를 보고 판단한다. 

JDK 동적 프록시로 만들어진 proxy 객체는 `MemberService` 인터페이스를 기반으로 구현된 새로운 클래스다. 

따라서 `MemberServiceImpl` 를 전혀 알지 못하므로 **AOP 적용 대상이 아니다.**

`target(hello.aop.member.MemberServiceImpl)` : target 객체를 보고 판단한다. 

target 객체가 `MemberServiceImpl` 타입이므로 AOP 적용 대상이다.

### **CGLIB 프록시**

<img width="678" alt="Screenshot 2024-10-20 at 16 53 02" src="https://github.com/user-attachments/assets/6d88be44-8d24-4afe-873b-b5bb06fa50f2">

**MemberService 인터페이스 지정**

`this(hello.aop.member.MemberService)` : proxy 객체를 보고 판단한다. 

`this` 는 부모 타입을 허용하기 때문에 AOP가 적용된다.

`target(hello.aop.member.MemberService)` : target 객체를 보고 판단한다. 

`target` 은 부모 타입을 허용하기 때문에 AOP가 적용된다.

**MemberServiceImpl 구체 클래스 지정**

`this(hello.aop.member.MemberServiceImpl)` : proxy 객체를 보고 판단한다. 

CGLIB로 만들어진proxy 객체는 `MemberServiceImpl` 를 상속 받아서 만들었기 때문에 AOP가 적용된다. 

`this` 가 부모 타입을 허용하기 때문에 포인트컷의 대상이 된다.

`target(hello.aop.member.MemberServiceImpl)` : target 객체를 보고 판단한다. 

target 객체가 `MemberServiceImpl` 타입이므로 AOP 적용 대상이다.

**정리**

프록시를 대상으로 하는 `this` 의 경우 구체 클래스를 지정하면 프록시 생성 전략에 따라서 다른 결과가 나올 수 있다는 점을 알아두자.

# 실무 주의사항

## 내부 호출

스프링은 프록시 방식의 AOP를 사용한다.

따라서 AOP를 적용하려면 항상 프록시를 통해서 대상 객체(Target)을 호출해야 한다.

이렇게 해야 프록시에서 먼저 어드바이스를 호출하고, 이후에 대상 객체를 호출한다.

만약 프록시를 거치지 않고 대상 객체를 직접 호출하게 되면 AOP가 적용되지 않고, 어드바이스도 호출되지 않는다.

AOP를 적용하면 스프링은 대상 객체 대신에 프록시를 스프링 빈으로 등록한다. 

따라서 스프링은 의존관계 주입시에 항상 프록시 객체를 주입한다. 

프록시 객체가 주입되기 때문에 대상 객체를 직접 호출하는 문제는 일반적으로 발생하지 않는다. 

하지만 대상 객체의 내부에서 메서드 호출이 발생하면 프록시를 거치지 않고 대상 객체를 직접 호출하는 문제가 발생한다. 

실무에서 반드시 한번은 만나서 고생하는 문제이기 때문에 꼭 이해하고 넘어가자.

예제를 통해서 내부 호출이 발생할 때 어떤 문제가 발생하는지 알아보자. 

먼저 내부 호출이 발생하는 예제를 만들어보자.


```java
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external method");
        internal();
    }


    public void internal() {
        log.info("call internal method");
    }
}

@Aspect
public class CallLogAspect {

    @Before("execution(* hello.aop.internalcall..*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("aop={}", joinPoint.getSignature());
    }
}


@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV0Test {

    @Autowired
    private CallServiceV0 callServiceV0;
  
    @Test
    void external() {
        callServiceV0.external();
    }
    
    @Test
    void internal() {
        callServiceV0.internal();
    }
}
```
**실행 결과 - external()** 

```
h.aop.internalcall.aop.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV0.external()
hello.aop.internalcall.CallServiceV0     : call external method
hello.aop.internalcall.CallServiceV0     : call internal method
```

<img width="698" alt="Screenshot 2024-10-21 at 21 56 52" src="https://github.com/user-attachments/assets/074c2cb8-5116-4c5f-b655-d2dd564e870d">


실행 결과를 보면 `callServiceV0.external()` 을 실행할 때는 프록시를 호출한다. 

따라서 `CallLogAspect` 어 드바이스가 호출된 것을 확인할 수 있다.

그리고 AOP Proxy는 `target.external()` 을 호출한다.

그런데 여기서 문제는 `callServiceV0.external()` 안에서 `internal()` 을 호출할 때 발생한다. 

이때는 `CallLogAspect` 어드바이스가 호출되지 않는다.

자바 언어에서 메서드 앞에 별도의 참조가 없으면 `this` 라는 뜻으로 자기 자신의 인스턴스를 가리킨다.

결과적으로 자기 자신의 내부 메서드를 호출하는 `this.internal()` 이 되는데, 여기서 `this` 는 실제 대상 객체 (target)의 인스턴스를 뜻한다. 

결과적으로 이러한 내부 호출은 프록시를 거치지 않는다.

따라서 어드바이스도 적용할 수 없다.

이번에는 외부에서 `internal()` 을 호출하는 테스트를 실행해보자.

**실행 결과 - internal()**

```
 CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV0.internal()
 CallServiceV0     : call internal
```

<img width="688" alt="Screenshot 2024-10-21 at 22 07 14" src="https://github.com/user-attachments/assets/d5b4e8a5-5fd3-46ad-85eb-2b1269a72910">

외부에서 호출하는 경우 프록시를 거치기 때문에 `internal()` 도 `CallLogAspect` 어드바이스가 적용된 것을 확인 할 수 있다.

**프록시 방식의 AOP 한계**

스프링은 프록시 방식의 AOP를 사용한다. 

프록시 방식의 AOP는 메서드 내부 호출에 프록시를 적용할 수 없다. 

지금부터 이 문제를 해결하는 방법을 하나씩 알아보자.

**참고**

실제 코드에 AOP를 직접 적용하는 AspectJ를 사용하면 이런 문제가 발생하지 않는다.

프록시를 통하는 것이 아 니라 해당 코드에 직접 AOP 적용 코드가 붙어 있기 때문에 내부 호출과 무관하게 AOP를 적용할 수 있다. 

하지만 로드 타임 위빙 등을 사용해야 하는데, 설정이 복잡하고 JVM 옵션을 주어야 하는 부담이 있다. 

그리고 지금부터 설명할 프록시 방식의 AOP에서 내부 호출에 대응할 수 있는 대안들도 있다.

이런 이유로 AspectJ를 직접 사용하는 방법은 실무에서는 거의 사용하지 않는다.

## 프록시와 내부 호출 - 대안1 자기 자신 주입

내부 호출을 해결하는 가장 간단한 방법은 자기 자신을 의존관계 주입 받는 것이다.

```java
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external method");
        callServiceV1.internal();
    }


    public void internal() {
        log.info("call internal method");
    }
}
```

`callServiceV1` 를 수정자를 통해서 주입 받는 것을 확인할 수 있다. 

스프링에서 AOP가 적용된 대상을 의존관계 주 입 받으면 주입 받은 대상은 실제 자신이 아니라 프록시 객체이다.

`external()` 을 호출하면 `callServiceV1.internal()` 를 호출하게 된다. 

주입받은 `callServiceV1` 은 프록 시이다. 따라서 프록시를 통해서 AOP를 적용할 수 있다.

참고로 이 경우 생성자 주입시 오류가 발생한다. 본인을 생성하면서 주입해야 하기 때문에 순환 사이클이 만들어진다. 

반면에 수정자 주입은 스프링이 생성된 이후에 주입할 수 있기 때문에 오류가 발생하지 않는다.


**실행 결과** 

```
CallLogAspect : aop=void hello.aop.internalcall.CallServiceV1.external()
CallServiceV2 : call external
CallLogAspect : aop=void hello.aop.internalcall.CallServiceV1.internal()
CallServiceV2 : call internal
```

실행 결과를 보면 이제는 `internal()` 을 호출할 때 자기 자신의 인스턴스를 호출하는 것이 아니라 프록시 인스턴스를 통해서 호출하는 것을 확인할 수 있다. 당연히 AOP도 잘 적용된다.

**주의**

스프링 부트 2.6부터는 순환 참조를 기본적으로 금지하도록 정책이 변경되었다. 

따라서 이번 예제를 스프링 부트 2.6 이상의 버전에서 실행하면 다음과 같은 오류 메시지가 나오면서 정상 실행되지 않는다.

`Error creating bean with name 'callServiceV1': Requested bean is currently in creation: Is there an unresolvable circular reference?`

이 문제를 해결하려면 `application.properties` 에 다음을 추가해야 한다. 

`spring.main.allow-circular-references=true`

## 프록시와 내부 호출 - 대안2 지연 조회

앞서 생성자 주입이 실패하는 이유는 자기 자신을 생성하면서 주입해야 하기 때문이다. 

이 경우 수정자 주입을 사용하거나 지금부터 설명하는 지연 조회를 사용하면 된다.

스프링 빈을 지연해서 조회하면 되는데, `ObjectProvider(Provider)` , `ApplicationContext` 를 사용하면 된다.

```
@Component
public class CallServiceV2 {

    //private final ApplicationContext applicationContext;
    
    //public CallServiceV2(ApplicationContext applicationContext) {
    //    this.applicationContext = applicationContext;
    //}

    
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;


    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }

    public void external() {
        log.info("call external method");
        CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal method");
    }
}
```

ObjectProvider` 는 기본편에서 학습한 내용이다. `ApplicationContext` 는 너무 많은 기능을 제공한다.

`ObjectProvider` 는 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라 실제 객체를 사용하 는 시점으로 지연할 수 있다.

`callServiceProvider.getObject()` 를 호출하는 시점에 스프링 컨테이너에서 빈을 조회한다.

여기서는 자기 자신을 주입 받는 것이 아니기 때문에 순환 사이클이 발생하지 않는다.

## 프록시와 내부 호출 - 대안3 구조 변경

앞선 방법들은 자기 자신을 주입하거나 또는 `Provider` 를 사용해야 하는 것 처럼 조금 어색한 모습을 만들었다.

가장 나은 대안은 내부 호출이 발생하지 않도록 구조를 변경하는 것이다. 실제 이 방법을 가장 권장한다.

```java
@Component
public class CallServiceV3 {
    
    private final InternalService internalService;

    public CallServiceV3(InternalService internalService) {
        this.internalService = internalService;
    }

    public void external() {
        log.info("call external method");
        internalService.internal();
    }
}

@Service
public class InternalService {

  public void internal() {
    log.info("call internal method");
  }
}
```

<img width="689" alt="Screenshot 2024-10-22 at 22 19 24" src="https://github.com/user-attachments/assets/154c69bb-1c12-4fbb-bd98-c2627be36237">

내부호출 자체가 사라지고, `callService` `internalService` 를 호출하는 구조로 변경되었다.덕분에 자연스럽게 AOP가 적용된다.

여기서 구조를 변경한다는 것은 이렇게 단순하게 분리하는 것 뿐만 아니라 다양한 방법들이 있을 수 있다.

예를 들어서 다음과 같이 클라이언트에서 둘다 호출하는 것이다. 

`클라이언트` `external()`

`클라이언트` `internal()`

물론 이 경우 `external()` 에서 `internal()` 을 내부 호출하지 않도록 코드를 변경해야 한다. 

그리고 클라이언트가 `external()` , `internal()` 을 모두 호출하도록 구조를 변경하면 된다. (물론 가능한 경우에 한해서)

**참고**

AOP는 주로 트랜잭션 적용이나 주요 컴포넌트의 로그 출력 기능에 사용된다. 

쉽게 이야기해서 인터페이스에 메서드가 나올 정도의 규모에 AOP를 적용하는 것이 적당하다. 

더 풀어서 이야기하면 AOP는 `public` 메서드에만 적용한다. 

`private` 메서드처럼 작은 단위에는 AOP를 적용하지 않는다.

AOP 적용을 위해 `private` 메서드를 외부 클래스로 변경하고 `public` 으로 변경하는 일은 거의 없다.

그러나 위 예제와 같이 `public` 메서드에서 `public` 메서드를 내부 호출하는 경우에는 문제가 발생한다. 

실무에서 꼭 한번은 만나는 문제이기에 이번 강의에서 다루었다.

AOP가 잘 적용되지 않으면 내부 호출을 의심해보자.


## 프록시 기술과 한계 - 타입 캐스팅

JDK 동적 프록시와 CGLIB를 사용해서 AOP 프록시를 만드는 방법에는 각각 장단점이 있다. 

JDK 동적 프록시는 인터페이스가 필수이고, 인터페이스를 기반으로 프록시를 생성한다. 

CGLIB는 구체 클래스를 기반으로 프록시를 생성한다.

물론 인터페이스가 없고 구체 클래스만 있는 경우에는 CGLIB를 사용해야 한다. 

그런데 인터페이스가 있는 경우에는 JDK 동적 프록시나 CGLIB 둘중에 하나를 선택할 수 있다.

스프링이 프록시를 만들때 제공하는 `ProxyFactory` 에 `proxyTargetClass` 옵션에 따라 둘중 하나를 선택해서 프록시를 만들 수 있다.

`proxyTargetClass=false` JDK 동적 프록시를 사용해서 인터페이스 기반 프록시 생성

`proxyTargetClass=true` CGLIB를 사용해서 구체 클래스 기반 프록시 생성

참고로 옵션과 무관하게 인터페이스가 없으면 JDK 동적 프록시를 적용할 수 없으므로 CGLIB를 사용한다.

**JDK 동적 프록시 한계**

인터페이스 기반으로 프록시를 생성하는 JDK 동적 프록시는 구체 클래스로 타입 캐스팅이 불가능한 한계가 있다. 

어떤 한계인지 코드를 통해서 알아보자.

```java
void jdkProxy() {
    MemberServiceImpl target =  new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    MemberServiceImpl castingMemberService = (MemberServiceImpl) proxyFactory.getProxy();
}
```

<img width="693" alt="Screenshot 2024-10-22 at 22 31 09" src="https://github.com/user-attachments/assets/3f16b788-78ab-41cb-803b-e13525575d14">

**jdkProxy() 테스트**

여기서는 `MemberServiceImpl` 타입을 기반으로 JDK 동적 프록시를 생성했다. `MemberServiceImpl` 타입은 `MemberService` 인터페이스를 구현한다. 

따라서 JDK 동적 프록시는 `MemberService` 인터페이스를 기반으로 프록시를 생성한다. 

이 프록시를 `JDK Proxy` 라고 하자. 

여기서 `memberServiceProxy` 가 바로 `JDK Proxy` 이다.

<img width="679" alt="Screenshot 2024-10-22 at 22 31 15" src="https://github.com/user-attachments/assets/3ccfdd68-ee9c-4a25-929a-78f071888b1e">

그런데 여기에서 JDK Proxy를 대상 클래스인 `MemberServiceImpl` 타입으로 캐스팅 하려고 하니 예외가 발생한다.

왜냐하면 JDK 동적 프록시는 인터페이스를 기반으로 프록시를 생성하기 때문이다. 

JDK Proxy는 `MemberService` 인터페이스를 기반으로 생성된 프록시이다. 

따라서 JDK Proxy는 `MemberService` 로 캐스팅은 가능하지만 `MemberServiceImpl` 이 어떤 것인지 전혀 알지 못한다.

따라서 `MemberServiceImpl` 타입으로는 캐스팅이 불가능하다. 

캐스팅을 시도하면 `ClassCastException.class` 예외가 발생한다.

**CGLIB 프록시**

```java
void jdkProxy2() {
    MemberServiceImpl target =  new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); // JDK 동적 프록시

    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    MemberServiceImpl castingMemberService = (MemberServiceImpl) proxyFactory.getProxy();
}
```

<img width="696" alt="Screenshot 2024-10-22 at 22 34 19" src="https://github.com/user-attachments/assets/bbbd68b0-bfd7-4d3e-b64a-4af91611ae64">

**cglibProxy() 테스트**

`MemberServiceImpl` 타입을 기반으로 CGLIB 프록시를 생성했다.

`MemberServiceImpl` 타입은 `MemberService` 인터페이스를 구현했다. 

CGLIB는 구체 클래스를 기반으로 프록시를 생성한다. 따라서 CGLIB는 `MemberServiceImpl` 구체 클래스를 기반으로 프록시를 생성한다. 

이 프록시를 CGLIB Proxy라고 하자. 여기서 `memberServiceProxy` 가 바로 CGLIB Proxy이다.

<img width="697" alt="Screenshot 2024-10-22 at 22 35 48" src="https://github.com/user-attachments/assets/70996a72-1c2b-4719-a282-21e82ce6b6e2">

여기에서 CGLIB Proxy를 대상 클래스인 `MemberServiceImpl` 타입으로 캐스팅하면 성공한다.

왜냐하면 CGLIB는 구체 클래스를 기반으로 프록시를 생성하기 때문이다. 

CGLIB Proxy는 `MemberServiceImpl` 구체 클래스를 기반으로 생성된 프록시이다. 

따라서 CGLIB Proxy는 `MemberServiceImpl` 은 물론이고, `MemberServiceImpl` 이 구현한 인터페이스인 `MemberService` 로도 캐스팅 할 수 있다.

**정리**
JDK 동적 프록시는 대상 객체인 `MemberServiceImpl` 로 캐스팅 할 수 없다. 

CGLIB 프록시는 대상 객체인 `MemberServiceImpl` 로 캐스팅 할 수 있다.

그런데 프록시를 캐스팅 할 일이 많지 않을 것 같은데 왜 이 이야기를 하는 것일까? 진짜 문제는 의존관계 주입시에 발생한다.


## 록시 기술과 한계 - 의존관계 주입

JDK 동적 프록시를 사용하면서 의존관계 주입을 할 때 어떤 문제가 발생하는지 코드로 알아보자.

```java
@Aspect
public class ProxyDIAspect {

    @Before("execution(* hello.aop..*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("before method invoked {}", joinPoint.getSignature());
    }
}

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
```
```shell
Bean named 'memberServiceImpl' is expected to be of type 'hello.aop.member.MemberServiceImpl' but was actually of type 'jdk.proxy3.$Proxy56'org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'hello.aop.proxyvs.ProxyDITest': Unsatisfied dependency expressed through field 'memberServiceImpl': Bean named 'memberServiceImpl' is expected to be of type 'hello.aop.member.MemberServiceImpl' but was actually of type 'jdk.proxy3.$Proxy56'
```

### JDK 동적 프록시를 구체 클래스 타입에 주입


`@Autowired MemberService memberService` : 이 부분은 문제가 없다. 

JDK Proxy는 `MemberService` 인터페이스를 기반으로 만들어진다. 

따라서 해당 타입으로 캐스팅 할 수 있다. 

`MemberService = JDK Proxy` 가 성립한다.

`@Autowired MemberServiceImpl memberServiceImpl` : 문제는 여기다. 

JDK Proxy는 `MemberService` 인터페이스를 기반으로 만들어진다. 

따라서 `MemberServiceImpl` 타입이 뭔지 전혀 모른다. 그래서 해당 타입에 주입할 수 없다.

`MemberServiceImpl = JDK Proxy` 가 성립하지 않는다.

### CGLIB 프록시를 구체 클래스 타입에 주입

`@Autowired MemberService memberService` : CGLIB Proxy는 `MemberServiceImpl` 구체 클래스 를 기반으로 만들어진다. 

`MemberServiceImpl` 은 `MemberService` 인터페이스를 구현했기 때문에 해당 타 입으로 캐스팅 할 수 있다.

`MemberService = CGLIB Proxy` 가 성립한다.

`@Autowired MemberServiceImpl memberServiceImpl` :CGLIBProxy는 `MemberServiceImpl ` 구체 클래스를 기반으로 만들어진다.

따라서 해당 타입으로 캐스팅할 수 있다.

`MemberServiceImpl = CGLIB Proxy` 가 성립한다.

**정리**

JDK 동적 프록시는 대상 객체인 `MemberServiceImpl` 타입에 의존관계를 주입할 수 없다. 

CGLIB 프록시는 대상 객체인 `MemberServiceImpl` 타입에 의존관계 주입을 할 수 있다.

지금까지 JDK 동적 프록시가 가지는 한계점을 알아보았다. 

실제로 개발할 때는 인터페이스가 있으면 인터페이스를 기반으로 의존관계 주입을 받는 것이 맞다.

DI의 장점이 무엇인가? DI 받는 클라이언트 코드의 변경 없이 구현 클래스를 변경할 수 있는 것이다. 

이렇게 하려면 인 터페이스를 기반으로 의존관계를 주입 받아야 한다. 

`MemberServiceImpl` 타입으로 의존관계 주입을 받는 것처럼 구현 클래스에 의존관계를 주입하면 향후 구현 클래스를 변경할 때 의존관계 주입을 받는 클라이언트의 코드도 함께 변경해야 한다.

따라서 올바르게 잘 설계된 애플리케이션이라면 이런 문제가 자주 발생하지는 않는다.

그럼에도 불구하고 테스트, 또는 여러가지 이유로 AOP 프록시가 적용된 구체 클래스를 직접 의존관계 주입 받아야 하는 경우가 있을 수 있다. 

이때는 CGLIB를 통해 구체 클래스 기반으로 AOP 프록시를 적용하면 된다.

여기까지 듣고보면 CGLIB를 사용하는 것이 좋아보인다. 

CGLIB를 사용하면 사실 이런 고민 자체를 하지 않아도 된다. 다음 시간에는 CGLIB의 단점을 알아보자.

## 프록시 기술과 한계 - CGLIB

스프링에서 CGLIB는 구체 클래스를 상속 받아서 AOP 프록시를 생성할 때 사용한다.

CGLIB는 구체 클래스를 상속 받기 때문에 다음과 같은 문제가 있다.

**CGLIB 구체 클래스 기반 프록시 문제점** 

대상 클래스에 기본 생성자 필수 생성자 2번 호출 문제

final 키워드 클래스, 메서드 사용 불가

**대상 클래스에 기본 생성자 필수**

CGLIB는 구체 클래스를 상속 받는다. 

자바 언어에서 상속을 받으면 자식 클래스의 생성자를 호출할 때 자식 클래스의 생성자에서 부모 클래스의 생성자도 호출해야 한다. 

(이 부분이 생략되어 있다면 자식 클래스의 생성자 첫줄에 부모 클래스의 기본 생성자를 호출하는 `super()` 가 자동으로 들어간다.) 이 부분은 자바 문법 규약이다.

CGLIB를 사용할 때 CGLIB가 만드는 프록시의 생성자는 우리가 호출하는 것이 아니다. 

CGLIB 프록시는 대상 클래스 를 상속 받고, 생성자에서 대상 클래스의 기본 생성자를 호출한다. 

따라서 대상 클래스에 기본 생성자를 만들어야 한다. 

(기본 생성자는 파라미터가 하나도 없는 생성자를 뜻한다. 생성자가 하나도 없으면 자동으로 만들어진다.)

**생성자 2번 호출 문제**

CGLIB는 구체 클래스를 상속 받는다. 

자바 언어에서 상속을 받으면 자식 클래스의 생성자를 호출할 때 부모 클래스의 생성자도 호출해야 한다. 그런데 왜 2번일까?

1. 실제 target의 객체를 생성할 때
2. 프록시 객체를 생성할때 부모 클래스의 생성자 호출

<img width="696" alt="Screenshot 2024-10-22 at 22 54 11" src="https://github.com/user-attachments/assets/babb944f-d13a-408e-b69d-426f15e95240">

**final 키워드 클래스, 메서드 사용 불가**

final 키워드가 클래스에 있으면 상속이 불가능하고, 메서드에 있으면 오버라이딩이 불가능하다. 

CGLIB는 상속을 기반으로 하기 때문에 두 경우 프록시가 생성되지 않거나 정상 동작하지 않는다.

프레임워크 같은 개발이 아니라 일반적인 웹 애플리케이션을 개발할 때는 `final` 키워드를 잘 사용하지 않는다. 

따라서 이 부분이 특별히 문제가 되지는 않는다.

**정리**

JDK 동적 프록시는 대상 클래스 타입으로 주입할 때 문제가 있고, CGLIB는 대상 클래스에 기본 생성자 필수, 생성자 2번 호출 문제가 있다.

그렇다면 스프링은 어떤 방법을 권장할까?


















