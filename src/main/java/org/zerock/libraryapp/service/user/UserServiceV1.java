package org.zerock.libraryapp.service.user;

import org.springframework.stereotype.Service;
import org.zerock.libraryapp.dto.user.request.UserCreateRequest;
import org.zerock.libraryapp.dto.user.request.UserUpdateRequest;
import org.zerock.libraryapp.dto.user.response.UserResponse;
import org.zerock.libraryapp.repository.user.UserJDBCRepository;

import java.util.List;

@Service
public class UserServiceV1 {
    private final UserJDBCRepository userJDBCRepository;

    public UserServiceV1(UserJDBCRepository userJDBCRepository) {
        this.userJDBCRepository = userJDBCRepository;
    }

    public void saveUser(UserCreateRequest request){
        userJDBCRepository.saveUser(request.getName(),request.getAge());
    }
    // controller 에서는 controller 의 역할을 수행하기 위해서 @RequestBody 어노테이션이 있는데 UserService 에서는 controller 가 객체로 변환한것을 그냥 받기만 할 예정이기 때문에 앞에 어노테이션을 사용하지 않고 그냥 객체만 받게 된다.

    public List<UserResponse> getUsers(){
        return userJDBCRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request){
        // 존재 하면 0이 저장된 리스트가 반환이 되고, 없다면 빈 리스트가 반환이 될것이다.
        if (userJDBCRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }
        userJDBCRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){
        // 존재 하면 0이 저장된 리스트가 반환이 되고, 없다면 빈 리스트가 반환이 될것이다.
        if (userJDBCRepository.inUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userJDBCRepository.deleteUser(name);
    }
}
