package com.example.bisneslogic.dto.checkout;

public class StripeResponse {
    private String sessionId;

    public StripeResponse(String sessionId) {
        this.sessionId = sessionId;
    }
}
