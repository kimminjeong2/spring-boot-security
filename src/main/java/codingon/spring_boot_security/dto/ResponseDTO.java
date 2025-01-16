package codingon.spring_boot_security.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter // 없으면 HttpMediaTypeNotAcceptableException: No acceptable representation 에러 발생
@Builder
public class ResponseDTO<T> {
    private String error; // error message - fail
    private List<T> data; // response data - success
    private String message;
}

// HTTP Response 할 때 사용할 DTO
// 서버에서 클라이언트로 응답할 때 사용할 데이터 구조 정의
