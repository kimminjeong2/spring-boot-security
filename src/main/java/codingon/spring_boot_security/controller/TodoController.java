package codingon.spring_boot_security.controller;

import codingon.spring_boot_security.dto.ResponseDTO;
import codingon.spring_boot_security.dto.TodoDTO;
import codingon.spring_boot_security.entity.TodoEntity;
import codingon.spring_boot_security.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO todoDTO) {
        // @AuthenticationPrincipal
        // - 현재 인증된 사용자 정보에 접근할 수 있게 함
        // - spring security 는 security context 에서 현재 인증된 사용자의 principal 을 가져옴
        // 우리 코드) JwtAuthenticationFilter 클래스에서 userId 를 바탕으로 인증 객체를 생성 했었음

        try {

            // 1) dto -> entity
            TodoEntity todoEntity = TodoEntity.of(userId, todoDTO.getTitle(), todoDTO.isDone());

            List<TodoEntity> entities = todoService.create(todoEntity);

            // entities -> dtos
            List<TodoDTO> dtos = new ArrayList<>();
            for (TodoEntity entity : entities) {
                TodoDTO dto = new TodoDTO(entity);
                dtos.add(dto);
            }

            // responseDTO 생성
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String err = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(err).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        List<TodoEntity> entities = todoService.retrieve(userId);

        // entities -> dtos
        List<TodoDTO> dtos = new ArrayList<>();
        for (TodoEntity entity : entities) {
            TodoDTO dto = new TodoDTO(entity);
            dtos.add(dto);
        }

        // responseDTO 생성
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id,  // PathVariable로 id를 받아옵니다.
            @RequestBody TodoDTO todoDTO) {

        try {
            // 수정할 TodoEntity 찾기
            TodoEntity updatedEntity = todoService.update(id, userId, todoDTO);

            // 수정된 엔티티 -> DTO 변환
            TodoDTO updatedDto = new TodoDTO(updatedEntity);

            // ResponseDTO 생성
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .data(List.of(updatedDto))
                    .build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String err = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .error(err)
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id) {

        try {
            // 삭제할 TodoEntity 찾기
            todoService.delete(id, userId);

            // ResponseDTO 생성 (삭제 성공)
            ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                    .message("Todo deleted successfully")
                    .build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String err = e.getMessage();
            ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                    .error(err)
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
