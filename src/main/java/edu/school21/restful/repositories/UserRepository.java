package edu.school21.restful.repositories;

import edu.school21.restful.models.Role;
import edu.school21.restful.models.User;
import net.bytebuddy.utility.nullability.NeverNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNullApi;

import java.util.Set;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Set<User> getAllByRole(Role role);

    Set<User> getAllByIdIn(Iterable<Long> idList);
    Slice<User> findAllByIdIn(Iterable<Long> idList, Pageable pageable);


    @Override
    void delete(User entity);

}
