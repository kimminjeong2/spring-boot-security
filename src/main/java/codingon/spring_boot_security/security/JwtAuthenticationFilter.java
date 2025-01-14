package codingon.spring_boot_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
// Token req에서 꺼내오고 검증
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter: 한 요청당 반드시 한 번만 실행

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // req 에서 token 가져오기
            String token = parseBearerToken(request);
            logger.info("JwtAuthenticationFilter is running ...");

        // token 검사
            if(token != null && !token.equalsIgnoreCase("null")) {
                String userId = tokenProvider.validateAndGetUserId(token);
                logger.info("Au user id: " + userId);

                // 이전에 추출한 userId 로 인증 객체 생성
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));

                // 생성한 인증 객체를 Security context 에 설정
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            }
        } catch (Exception e) {
                logger.error("");
            }

        // 필터 체인을 계속 진행
        filterChain.doFilter(request, response);
    }

    // req.headers 에서 Bearer Token 을 꺼내오는 역할
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
            // Authentication: "Bearer ~~~~";
        }
        return null;
    }
}
