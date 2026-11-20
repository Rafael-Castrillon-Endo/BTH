package iio.github.rafaelcastrillonendo.bht_app.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    Long countByIdIn(Collection<Long> ids);

}