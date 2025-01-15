package codingon.spring_boot_security.dto;

import codingon.spring_boot_security.entity.TodoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@Getter
public class TodoDTO {
    private Long id;
    private String title;
    private boolean done;

    // TodoEntity 를 받아 DTO 객체로 변환하는 생성자
    public TodoDTO(TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
    }
}
