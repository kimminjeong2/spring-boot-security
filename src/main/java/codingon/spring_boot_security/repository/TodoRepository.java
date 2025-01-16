package codingon.spring_boot_security.repository;

import codingon.spring_boot_security.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByUserId(String userId);
    Optional<TodoEntity> findByIdAndUserId(Long id, String userId);
}
