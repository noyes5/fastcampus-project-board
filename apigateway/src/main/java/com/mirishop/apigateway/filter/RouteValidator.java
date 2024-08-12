package com.mirishop.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    /**
     * 엔드포인트 중 회원검증이 필요하지 않은 엔드포인트 목록
     */
    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/v1/members/register",
            "/api/v1/members/email-authentication",
            "/api/v1/members/email-verification",
            "/api/v1/members/upload/image",
            "/api/v1/login",
            "/api/v1/refreshToken"
    );

    public static List<String> getOpenApiEndpoints() {
        return new ArrayList<>(OPEN_API_ENDPOINTS);
    }

    public Predicate<ServerHttpRequest> isSecured() {
        return request -> getOpenApiEndpoints()
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
