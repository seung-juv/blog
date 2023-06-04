package com.seungju.blog.jwt.dto;

import com.seungju.blog.jwt.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefreshTokenDtoMapper {

  RefreshTokenDtoMapper INSTANCE = Mappers.getMapper(RefreshTokenDtoMapper.class);

  RefreshTokenDto.Response refreshTokenToResponse(RefreshToken refreshToken);

}
