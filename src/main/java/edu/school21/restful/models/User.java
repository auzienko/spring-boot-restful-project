package edu.school21.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties({ "password", })
@Table(schema = "rstf", name = "tbl_users")
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    private Role role;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
