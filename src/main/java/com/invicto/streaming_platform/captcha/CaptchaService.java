package com.invicto.streaming_platform.captcha;

public interface CaptchaService {

    boolean validateResponse(String response);
    String getReCaptchaSite();
    String getReCaptchaSecret();

}
