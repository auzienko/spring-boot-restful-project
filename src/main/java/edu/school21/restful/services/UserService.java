package edu.school21.restful.services;

import edu.school21.restful.models.User;

public interface UserService extends BaseService<User>{
    public void updateUser(User entity, long id);
}
