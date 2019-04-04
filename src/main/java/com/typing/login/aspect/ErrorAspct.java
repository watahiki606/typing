package com.typing.login.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspct {

    @AfterThrowing(value = "execution(* *..*..*(..))"
            + " && (bean(*Controller) || bean(*Service) || bean(*Repository))", throwing = "ex")
    public void throwingNull(DataAccessException ex) {
        System.out.println("================================================");
        System.out.println("[err]" + ex);
        System.out.println("================================================");
    }
}
