package ru.rogzy.tracker_backend.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private T data;
    private BaseError error;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(BaseError error) {
        this.error = error;
    }
}
