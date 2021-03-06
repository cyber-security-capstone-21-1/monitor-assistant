package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class ApiResponse<T> {
    @NonNull
    private T data;
    private String status;
    private List<String> errors;
}
