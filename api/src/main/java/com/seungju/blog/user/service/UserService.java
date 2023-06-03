package com.seungju.blog.user.service;

import com.seungju.blog.exception.AlreadyExistsException;
import com.seungju.blog.user.dto.UserDto;
import com.seungju.blog.user.dto.UserDtoMapper;
import com.seungju.blog.user.entity.User;
import com.seungju.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDto.Response createUser(UserDto.Request request) {
        boolean isExistsUser = userRepository.findByEmail(request.getEmail()).isPresent();

        if (isExistsUser) {
            throw new AlreadyExistsException("This user already exists");
        }

        User user = UserDtoMapper.INSTANCE.requestToUser(request);
        User userEntity = userRepository.save(user);
        UserDto.Response response = UserDtoMapper.INSTANCE.userToResponse(userEntity);
        return response;
    }

}
