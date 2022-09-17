package edu.school21.restful.services;

import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public Set<User> findAll(){
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    @Override
    public void updateUser(User entity, long id) {
        User user = findById(id);
        userRepository.findById(id).
                map(toUpdate -> {
                    toUpdate.setFirstName(entity.getFirstName() != null ? entity.getFirstName()    : user.getFirstName());
                    toUpdate.setLogin(entity.getLogin() !=         null ? entity.getLogin()        : user.getLogin());
                    toUpdate.setLastName(entity.getLastName() !=   null ? entity.getLastName()     : user.getLastName());
                    toUpdate.setPassword(entity.getPassword() !=   null ? entity.getPassword()     : user.getPassword());
                    toUpdate.setRole(entity.getRole() !=           null ? entity.getRole()         : user.getRole());
                    return userRepository.save(toUpdate);
                });
    }
}
