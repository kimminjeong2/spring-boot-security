package codingon.spring_boot_security.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private String userId; // user 테이블의 id 값

    // title을 수정합니다.
    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    // done 값을 수정합니다.
    @Setter
    @Column(name = "done", nullable = false)
    private boolean done;

    public static TodoEntity of(String userId, String title, boolean done) {
        return TodoEntity.builder()
                .title(title)
                .userId(userId)
                .done(done)
                .build();
    }

}
