package com.invicto.streaming_platform.captcha;

public interface CaptchaService {

    void processResponse(String response);
    String getReCaptchaSite();
    String getReCaptchaSecret();

}
