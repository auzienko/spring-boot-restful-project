package edu.school21.restful.services;

import edu.school21.restful.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {
}
