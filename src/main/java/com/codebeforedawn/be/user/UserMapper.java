package com.codebeforedawn.be.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
}
