package com.funding.sandbox.repository;
import com.funding.sandbox.model.StartupProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.funding.sandbox.model.User;
import java.util.Optional;
@Repository

public interface StartupProfileRepository extends JpaRepository<StartupProfile,Long> {
    Optional<StartupProfile> findByUser(User user);
}
