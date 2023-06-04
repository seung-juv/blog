package com.seungju.blog.jwt.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessTokenDtoMapper {

  AccessTokenDtoMapper INSTANCE = Mappers.getMapper(AccessTokenDtoMapper.class);

  AccessTokenDto.Response accessTokenToResponse(String accessToken);

}
