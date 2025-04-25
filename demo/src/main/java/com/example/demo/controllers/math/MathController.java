package com.example.demo.controllers.math;

import com.example.demo.controllers.app.AppController;
import com.example.demo.dataFunc.entitys.dto.BaseMathDTO;
import com.example.demo.dataFunc.entitys.math.MathHistory;
import com.example.demo.dataFunc.service.MathHistoryService;
import com.example.demo.modules.math.BaseMath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MathController {
    private final Logger logger = LoggerFactory.getLogger(MathController.class);

    private final MathHistoryService mathHistoryService;
    private final ObjectMapper objectMapper;

    public MathController(MathHistoryService mathHistoryService, ObjectMapper objectMapper) {
        this.mathHistoryService = mathHistoryService;
        this.objectMapper = objectMapper;
    }

    // operators = - * / //// operators = - * / //// operators = - * / //
    @PostMapping("/plus")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> plus(@RequestBody BaseMathDTO baseMath) {
        this.logger.info("plus");

        float result = BaseMath.plus(baseMath.getA(), baseMath.getB());
        mathHistoryService.save("+", baseMath.getA(), baseMath.getB(), result);

        return ResponseEntity.ok(String.valueOf(result));
    }

    @PostMapping("/minus")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> minus(@RequestBody BaseMathDTO baseMath) {
        this.logger.info("minus");

        float result = BaseMath.minus(baseMath.getA(), baseMath.getB());
        mathHistoryService.save("-", baseMath.getA(), baseMath.getB(), result);

        return ResponseEntity.ok(String.valueOf(result));
    }

    @PostMapping("/multiply")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> multiply(@RequestBody BaseMathDTO baseMath) {
        this.logger.info("multiply");

        float result = BaseMath.multiply(baseMath.getA(), baseMath.getB());
        mathHistoryService.save("*", baseMath.getA(), baseMath.getB(), result);

        return ResponseEntity.ok(String.valueOf(result));
    }

    @PostMapping("/division")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> division(@RequestBody BaseMathDTO baseMath) {
        this.logger.info("division");

        float result = BaseMath.division(baseMath.getA(), baseMath.getB());
        mathHistoryService.save("/", baseMath.getA(), baseMath.getB(), result);

        return ResponseEntity.ok(String.valueOf(result));
    }

    // get ALL ID OPERATOR //// get ALL ID OPERATOR //// get ALL ID OPERATOR //
    @GetMapping("/getAll")
    public ResponseEntity<List<MathHistory>> getAll() {
        this.logger.info("getAll");

        List<MathHistory> allMath = mathHistoryService.findAll();
        return ResponseEntity.ok(allMath);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<Optional<MathHistory>> getByID(@PathVariable Long id) {
        this.logger.info("getByID");

        Optional<MathHistory> res = mathHistoryService.findById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getByOperator/{operator}")
    public ResponseEntity<List<MathHistory>> getByOperator(@PathVariable String operator) {
        this.logger.info("getByOperator");

        List<MathHistory> res = mathHistoryService.findByOperator(operator);
        return ResponseEntity.ok(res);
    }

    // delete ID OPERATOR //// delete ID OPERATOR //// delete ID OPERATOR //
    @DeleteMapping("/deleteById/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        this.logger.info("deleteById");

        mathHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/deleteByOperator")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MathHistory>> deleteByOperator(@RequestBody BaseMathDTO baseMath) {
        this.logger.info("deleteByOperator");
        System.out.println("operator    " + baseMath.getOperator());

        List<MathHistory> res = mathHistoryService.deleteByOperator(baseMath.getOperator());
        return ResponseEntity.ok(res);
    }

    // patch id //// patch id //// patch id //// patch id //// patch id //
    @PatchMapping(path = "/patchById/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<MathHistory> patchById(@RequestBody JsonPatch patchMath, @PathVariable Long id) {
        this.logger.info("patchById");

        Optional<MathHistory> oldMath = mathHistoryService.findById(id);
        if (oldMath.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            MathHistory newMath = oldMath.get();
            newMath = objectMapper.treeToValue(
                    patchMath.apply(objectMapper.convertValue(newMath, JsonNode.class)),
                    MathHistory.class
            );
            return ResponseEntity.ok(newMath);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // put id //// put id //// put id //// put id //// put id //// put id //
    @PutMapping("/putById/{id}")
    public ResponseEntity<MathHistory> putById(@RequestBody MathHistory newMath, @PathVariable Long id) {
        this.logger.info("putById");
        Optional<MathHistory> oldMath = mathHistoryService.findById(id);
        if (oldMath.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mathHistoryService.save(newMath);
        return ResponseEntity.ok(newMath);
    }

    // header //// header //// header //// header //// header //// header //
    @GetMapping("/head")
    public ResponseEntity<String> headers(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();

        if (host == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(String.format("http://" + host.getHostName() + ":" + host.getPort()), HttpStatus.OK);
    }

    // options //// options //// options //// options //// options //// options //
    @RequestMapping(method = RequestMethod.OPTIONS, path = "/options")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> options() {
        HttpHeaders header = new HttpHeaders();
        CrossOrigin cors = this.getClass().getAnnotation(CrossOrigin.class);
        if (cors != null)
//            System.out.println(Arrays.toString(Arrays.stream(cors.methods()).toArray()));
            return ResponseEntity.ok("AccessControlAllowOrigin : " + String.join(",", cors.origins()) + "\n" +
                    "AccessControlAllowMethods : " + Arrays.stream(cors.methods()).
                    map(RequestMethod::name).
                    collect(Collectors.joining(",")) + "\n" +
                    "AccessControlAllowHeaders : " + String.join(",", cors.allowedHeaders())
            );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
