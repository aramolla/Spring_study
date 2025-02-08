package org.zerock.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age; // int -> null을 표현 못하지만 Integer는 null을 표현가능하다.

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
