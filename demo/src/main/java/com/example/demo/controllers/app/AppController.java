package com.example.demo.controllers.app;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        exposedHeaders = {"X-Custom-Header"},
        allowedHeaders = {"*"},
        origins = "*",
        maxAge = 3600,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.OPTIONS,
                RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD}
)
@RestController
@RequestMapping("/api/v1")
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    // GET POST PUT PATCH DELETE HEAD OPTIONS
    // Path Parameters — для обязательных параметров (идентификация ресурса).
    // Query Parameters — для необязательных параметров (фильтрация, сортировка).
    // Body — для передачи сложных данных (например, JSON-объектов).
    // Headers — для служебной информации (авторизация, метаданные).
    // Form Data — для загрузки файлов или отправки данных из форм.
    // Matrix Parameters — редко, зависит от реализации API.

    // методы -> авто БД;

    // head + options
    // https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html#mvc-ann-requestmapping-head-options

    @AllArgsConstructor
    @NoArgsConstructor
    public class Some {
        public String some;
        public String elsess;
    }

    @GetMapping("/ping")
    public ResponseEntity<Some> ping() {
        this.logger.info("ping");
        return ResponseEntity.ok(new Some("asdsasd", "12"));
    }
}
