package pre012.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByuserName(String userName);

    Optional<User> findBySocialProviderAndProviderId(String socialProvider, String providerId);
}
