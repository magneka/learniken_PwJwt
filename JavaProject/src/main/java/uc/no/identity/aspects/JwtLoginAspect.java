package uc.no.identity.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import uc.no.identity.jwtTokenService.IJwtTokenService;


@Aspect
@Component
public class JwtLoginAspect {

    final IJwtTokenService jwtTokenUtil;

    JwtLoginAspect(IJwtTokenService jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Pointcut("@annotation(uc.no.identity.aspects.LoginIsRequired)")
    private void loginIsRequired() {
    }

    @Around("loginIsRequired()")
    public Object checkifLoggedIn(ProceedingJoinPoint joinPoint) {
        System.out.println("Around advice JwtAspect >>>>");
        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authTokenHeader = request.getHeader("Authorization");
            String token = authTokenHeader.replace("Bearer ", "");

            if (jwtTokenUtil.parseJwt(token) == null) {
                String redirectUrl = "<head><meta http-equiv=\"Refresh\" content=\"0; URL=http://localhost:8080/login.html\" /></head>";
                return "redirect:" + redirectUrl;
            } else {
                var result = joinPoint.proceed();
                return result;
            }
          

        } catch (Throwable e) {
            System.out.println("Exception occured" + e);
        }
        return null;
    }

}
