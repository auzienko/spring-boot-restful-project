package edu.school21.restful.repositories;

import edu.school21.restful.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
