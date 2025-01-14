package codingon.spring_boot_security.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames="email")})
// uniqueConstraints
// 이메일 필드에 unique 제약 조건
// - unique : 해당 테이블에 컬럼이 중복된 값을 허용하지 않음, pk 와 다른점은 null 들어갈 수 있음
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
