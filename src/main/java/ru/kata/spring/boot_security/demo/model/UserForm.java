package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserForm {
    private String name;
    private String lastname;
    private Integer age;
    private String username;
    private String password;
    private List<Long> roleIds = new ArrayList<>();
}
