package com.invicto.streaming_platform.captcha;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public enum ErrorCode {

    MISSING_SECRET,
    INVALID_SECRET,
    MISSING_RESPONSE,
    INVALID_RESPONSE,
    BAD_REQUEST,
    TIMEOUT_OR_DUPLICATE;

    private static final Map<String, ErrorCode> errorsMap = Map.ofEntries(
            Map.entry("missing-input-secret", MISSING_SECRET),
            Map.entry("invalid-input-secret", INVALID_SECRET),
            Map.entry("missing-input-response", MISSING_RESPONSE),
            Map.entry("invalid-input-response", INVALID_RESPONSE),
            Map.entry("bad-request", BAD_REQUEST),
            Map.entry("timeout-or-duplicate", TIMEOUT_OR_DUPLICATE));

    @JsonCreator
    private static ErrorCode fromValue(String value) {
        return errorsMap.get(value.toLowerCase());
    }
}
