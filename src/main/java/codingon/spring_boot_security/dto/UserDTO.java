package codingon.spring_boot_security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class UserDTO {
    private String token; // jwt token 저장 공간
    private String email;
    private String username;
    private String password;
    private Long id;
}
