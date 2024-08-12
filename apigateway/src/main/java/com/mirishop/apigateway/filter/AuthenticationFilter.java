package com.mirishop.apigateway.filter;

import com.mirishop.apigateway.exception.ErrorCode;
import com.mirishop.apigateway.exception.JwtTokenException;
import com.mirishop.apigateway.util.UtilJwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final UtilJwtTokenProvider jwtTokenProvider;
    private final RouteValidator routeValidator;

    public AuthenticationFilter(UtilJwtTokenProvider jwtTokenProvider, RouteValidator routeValidator) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.routeValidator = routeValidator;
    }

    /**
     * token으로 부터 number를 가져와 header에 X-MEMBER-NUMBER로 추가하는 메소드
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("헤더가 토큰을 가지고 있는지 검증");
            if (routeValidator.isSecured().test(exchange.getRequest())) {
                ServerHttpRequest request = exchange.getRequest();

                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("토큰값을 가지고 있지 않음");
                    throw new JwtTokenException(ErrorCode.TOKEN_NOT_FOUND);
                }

                String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                String token = extractTokenByHeader(authorizationHeader); // Remove "Bearer " prefix

                if (!jwtTokenProvider.validateToken(token)) {
                    log.error("올바른 토큰을 가지고 있지 않음");
                    throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
                }

                Long extractMemberNumber = jwtTokenProvider.getMemberNumber(token);
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-MEMBER-NUMBER", extractMemberNumber.toString())
                        .build();
                log.info("토큰 발급후 헤더에 정상 memberNumber추가");

                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration 추후 추가
    }

    /**
     * 토큰 앞부분 "bearer " 를 제거하는 메소드
     */
    private String extractTokenByHeader(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }
}
