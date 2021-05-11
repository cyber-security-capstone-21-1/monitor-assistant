package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class CustomResponseEntity<T> {
    private T data;
}
