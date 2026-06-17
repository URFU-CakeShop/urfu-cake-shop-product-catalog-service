package ru.urfu.cake.shop.product.catalog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Базовый ответ API")
public class ApiResponse<T> {
    @Schema(description = "Признак успеха", example = "true")
    private boolean success;
    @Schema(description = "Данные ответа")
    private T data;
    @Schema(description = "Сообщение", example = "Message")
    private String message;
}
