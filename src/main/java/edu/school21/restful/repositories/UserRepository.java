package edu.school21.restful.repositories;

import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import net.bytebuddy.utility.nullability.NeverNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    List<User>     getAllByRole(Role role);

    @Override
    void delete(User entity);

}
