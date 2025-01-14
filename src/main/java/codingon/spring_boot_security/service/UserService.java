package codingon.spring_boot_security.service;

import codingon.spring_boot_security.entity.UserEntity;
import codingon.spring_boot_security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    // 회원가입 (사용자 생성)
    public UserEntity create(UserEntity userEntity) {
        // 유효성 검사 1) userEntity 혹은 email 이 null 인 경우 예외 던짐
        if(userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        String email = userEntity.getEmail();

        // 유효성 검사 2) 이메일이 이미 존재하는 경우 예외 던짐
        if(userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(userEntity);
    }
}

// 로그인
// 패스워드가 암호화 되어 있어 그냥 비교 X
public UserEntity getByCredentials(String email, string password, PasswordEncoder encoder) {
    UserEntity originalUser = userRepository.findByEmail(email);

    if(originalUser !== null && encoder.matches(password, originalUser.getPassword())) {
        return originalUser;
    }
    return null;
}