package codingon.spring_boot_security.security;

import codingon.spring_boot_security.entity.UserEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// 사용자 정보를 받아서 JWT 를 생성하는 클래스
public class TokenProvider {
    private static final String SECRET_KEY = "codingon-springboot-security";

    // JWT 토큰 생성
    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        // 기한 : 지금으로부터 1일

        // 생성
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(String.valueOf(userEntity.getId()))
                .setIssuer("issuer") // iss : 토큰 발급자
                .setIssuedAt(new Date()) // iat : 토큰 발급된 시간
                .setExpiration(expiryDate) // exp: 토큰 만료 시간
                .compact(); // 토큰 생성
    }

    // 토큰 디코딩 및 파싱하고 토큰 위조 여부 확인 -> 사용자의 아이디를 리턴
    public String validateAndGetUserId(String token) {
        // parseClaimsJws(): Base 64로 디코딩/파싱
        Claims claims = Jwts.parser() // 파서 생성
                .setSigningKey(SECRET_KEY) // 서명 검증을 위해 비밀키 입력
                .parseClaimsJws(token) // 토큰을 파싱하고 서명 검증
                .getBody(); // 검증된 토큰의 본문을 가져옴

        return claims.getSubject();
    }
}
