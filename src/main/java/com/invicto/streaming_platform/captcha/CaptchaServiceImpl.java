package com.invicto.streaming_platform.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.regex.Pattern;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final HttpServletRequest request;

    private final CaptchaSettings captchaSettings;

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    @Autowired
    public CaptchaServiceImpl(HttpServletRequest request, CaptchaSettings captchaSettings) {
        this.request = request;
        this.captchaSettings = captchaSettings;
    }

    @Override
    public void processResponse(String response) {
        if(!responseSanityCheck(response)) {
            throw new ReCaptchaInvalidException("Response contains invalid characters");
        }

        RestTemplate restTemplate = new RestTemplate();
        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                getReCaptchaSecret(), response, getClientIP()));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if(!googleResponse.isSuccess()) {
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    @Override
    public String getReCaptchaSite() {
        return captchaSettings.getSite();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
