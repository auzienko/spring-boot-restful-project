package edu.school21.restful.security.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    @Setter(AccessLevel.NONE)
    private List<String> roles;
}
