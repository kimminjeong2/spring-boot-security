package codingon.spring_boot_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// Password 암호화
// Password 객체를 Bean 으로 생성
// 장점
// - 어플리케이션 전체에서 일관된 PasswordEncoder 사용 가능
// - "의존성 주입" 용이
// - 구현체 쉽게 변경 가능
