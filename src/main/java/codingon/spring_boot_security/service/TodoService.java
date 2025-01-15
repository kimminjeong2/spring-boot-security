package codingon.spring_boot_security.service;

import codingon.spring_boot_security.entity.TodoEntity;
import codingon.spring_boot_security.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    // create Todo
    public List<TodoEntity> create(TodoEntity todoEntity) {
        validate(todoEntity);

        todoRepository.save(todoEntity);

        log.info("Entity Id: {} is saved", todoEntity.getId());

        // db select 수행 (추가한 행을 바로 다시 보여주기)
        return todoRepository.findByUserId(todoEntity.getUserId());
    }


    // read Todo
    public List<TodoEntity> retrieve(String userId) {
        return todoRepository.findByUserId(userId);
    }

    // 유효성 검사
    private void validate(TodoEntity todoEntity) {
        if (todoEntity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (todoEntity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
