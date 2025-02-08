package org.zerock.libraryapp.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.libraryapp.domain.user.User;
import org.zerock.libraryapp.domain.user.UserRepository;
import org.zerock.libraryapp.dto.user.request.UserCreateRequest;
import org.zerock.libraryapp.dto.user.request.UserUpdateRequest;
import org.zerock.libraryapp.dto.user.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아래 있는 함수가 시작될 때 start transaction;을 해준다. (트랜잭션을 시작!)
    // 함수가 예외 없이 잘 끝났다면 commit
    // 혹시라도 문제가 있다면 rollback
    @Transactional
    public void saveUser(UserCreateRequest request) {
        User u = userRepository.save(new User(request.getName(), request.getAge()));
//        throw new IllegalArgumentException();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
//        return userRepository.findAll().stream().map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
//                .collect(Collectors.toList());
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        // select * from user where id = ?;
        User user = userRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        // 영속성 컨텍스트로 인해서 아래와 같이 save를 하지 않아도 저장이된다.
        // userRepository.save(user);
    }

    public void deleteUser(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}









