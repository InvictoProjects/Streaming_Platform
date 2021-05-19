package com.invicto.streaming_platform.captcha;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CaptchaAspect {

    private final HttpServletRequest request;
    private final CaptchaService captchaService;

    @Autowired
    public CaptchaAspect(HttpServletRequest request, CaptchaService captchaService) {
        this.request = request;
        this.captchaService = captchaService;
    }

    @Around("@annotation(RequiresCaptcha)")
    public Object validateCaptcha(ProceedingJoinPoint joinPoint) throws Throwable {
        String response = request.getParameter("g-recaptcha-response");
        boolean isValidCaptcha = captchaService.validateResponse(response);
        if (!isValidCaptcha) {
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }
        return joinPoint.proceed();
    }

}
