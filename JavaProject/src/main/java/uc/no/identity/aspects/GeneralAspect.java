package uc.no.identity.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GeneralAspect {

    @Before(value = "execution(* uc.no.identity.controllers..*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before GeneralAspect >>>>");
    }

    @After(value = "execution(* uc.no.identity.controllers..*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("After GeneralAspect >>>>");
    }

    @AfterReturning(value = "execution(* uc.no.identity.controllers..*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        System.out.println("After returning GeneralAspect >>>>");
    }

    @Around(value = "execution(* uc.no.identity.controllers..*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        System.out.println("Around advice GeneralAspect >>>>");
        try {
            var result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            System.out.println("Exception occured" + e);
        }
        return null;
    }
    
}
