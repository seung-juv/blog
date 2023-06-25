package com.seungju.blog.user.dto;

import com.seungju.blog.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    User requestToUser(UserDto.Create request);

    UserDto.Response userToResponse(User user);

}
