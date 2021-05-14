package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse {
    private T data;
    private String message;

    public CommonResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

}
