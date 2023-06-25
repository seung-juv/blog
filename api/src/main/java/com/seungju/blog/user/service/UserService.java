package com.seungju.blog.user.service;

import com.seungju.blog.exception.AlreadyExistsException;
import com.seungju.blog.exception.NotFoundException;
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

    public User createUser(UserDto.Create request) {
        boolean isExistsUser = userRepository.findByEmail(request.getEmail()).isPresent();

        if (isExistsUser) {
            throw new AlreadyExistsException("This user already exists");
        }

        User userEntity = UserDtoMapper.INSTANCE.requestToUser(request);
        User user = userRepository.save(userEntity);
        return user;
    }

    public User getUserWithUsernameAndPassword(
        UserDto.GetUserWithUsernameAndPassword request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(),
            request.getPassword()).orElseThrow(NotFoundException::new);
        return user;
    }


}
