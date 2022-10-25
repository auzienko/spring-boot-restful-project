package edu.school21.restful.services;

import edu.school21.restful.dto.UserDto;
import edu.school21.restful.models.User;

import java.util.Set;

public interface UserService extends BaseService<User>{
    public void updateUser(User entity, long id);
    Set<User> findUsersById(Iterable<Long> idSet);

    Set<User> getUserByCourse(Iterable<Long> idSet, int page, int size);

}
