package edu.school21.restful.dto;

import edu.school21.restful.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto{

    private String firstName;

    private String lastName;

    private Role role;

    private String login;

}
