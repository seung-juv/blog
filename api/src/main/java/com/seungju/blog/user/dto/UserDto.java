package com.seungju.blog.user.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class UserDto {

    @Value
    @Getter
    @Setter
    public static class Response implements Serializable {

        UUID id;

        String name;

        String email;

        String username;

        String image;

    }

    @Value
    @Getter
    @Setter
    public static class CreateRequest implements Serializable {

        String name;

        String email;

        String username;

        String password;

        String image;

    }

    @Value
    @Getter
    @Setter
    public static class GetUserWithUsernameAndPasswordRequest implements Serializable {

        String username;

        String password;

    }

}
