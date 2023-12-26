package com.xmut.managementsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    String status = null;
    String message = null;
    String token = null;
    T data = null;
}
