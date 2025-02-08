package org.zerock.libraryapp.controller.user;

import org.springframework.web.bind.annotation.*;
import org.zerock.libraryapp.domain.user.User;
import org.zerock.libraryapp.dto.user.request.UserCreateRequest;
import org.zerock.libraryapp.dto.user.request.UserUpdateRequest;
import org.zerock.libraryapp.dto.user.response.UserResponse;
import org.zerock.libraryapp.service.user.UserServiceV1;
import org.zerock.libraryapp.service.user.UserServiceV2;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserServiceV2 userServiceV1;

    public UserController(UserServiceV2 userServiceV1) {
        this.userServiceV1 = userServiceV1;
    }

    private final List<User> users = new ArrayList<>();

    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        userServiceV1.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers(){
        return userServiceV1.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        // controller 에서는 controller 의 역할을 수행하기 위해서 @RequestBody 어노테이션이 있는데 UserService 에서는 controller 가 객체로 변환한것을 그냥 받기만 할 예정이기 때문에 앞에 어노테이션을 사용하지 않고 그냥 객체만 받게 된다.
        userServiceV1.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){ // DELETE 같은 경우는 쿼리가 사용되어서 바디를 받지 않는다.
        userServiceV1.deleteUser(name);
    }

}
