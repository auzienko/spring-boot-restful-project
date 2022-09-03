package edu.school21.restful.services;

import edu.school21.restful.exeptions.ResourceNotFoundException;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.UserRepository;
import edu.school21.restful.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.DESC;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Set<User> findAll() {
        return new HashSet<>((Collection<User>) userRepository.findAll(Sort.by(DESC, "id")));
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

    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserDetailsImpl.build(user.get());
    }
}
