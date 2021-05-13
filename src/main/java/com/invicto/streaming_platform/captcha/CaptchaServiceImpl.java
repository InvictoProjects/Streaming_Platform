package com.invicto.streaming_platform.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    private final CaptchaSettings captchaSettings;

    @Autowired
    public CaptchaServiceImpl(CaptchaSettings captchaSettings) {
        this.captchaSettings = captchaSettings;
    }

    @Override
    public boolean validateResponse(String response) {
        RestTemplate restTemplate = new RestTemplate();
        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                getReCaptchaSecret(), response));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
        if (googleResponse == null) {
            return false;
        }
        return googleResponse.isSuccess();
    }

    @Override
    public String getReCaptchaSite() {
        return captchaSettings.getSite();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

}
