package edu.school21.restful.repositories;

import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Set<User> getAllByRole(Role role);

    @Override
    void delete(User entity);

}
