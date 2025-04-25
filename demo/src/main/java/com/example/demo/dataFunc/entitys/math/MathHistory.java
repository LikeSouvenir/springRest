package com.example.demo.dataFunc.entitys.math;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "history", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MathHistory {
    public MathHistory(String operator, float a, float b, float result) {
        this.operator = operator;
        this.a = a;
        this.b = b;
        this.result = result;
        this.date = LocalDate.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String operator;

    @Column(columnDefinition = "FLOAT DEFAULT 0")
    private float a;

    @Column(columnDefinition = "FLOAT DEFAULT 0")
    private float b;

    private float result;

    @Temporal(TemporalType.DATE)
    private LocalDate date;
}