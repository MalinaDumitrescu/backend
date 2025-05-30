package com.dailyboost.backend.repository;

import com.dailyboost.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    // 🔍 Pentru căutare parțială (autocomplete) după username (fără diferență între litere mari/mici)
    List<User> findByUsernameContainingIgnoreCase(String username);
}
