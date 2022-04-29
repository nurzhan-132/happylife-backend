package com.happylife.library.myspringbootproject.repository;
import com.happylife.library.myspringbootproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Procedure(name = User.NamedQuery_GET_SUBSCRIBERS_OF_PERIODICAL_ID)
    List<User> get_Subscribers_Of_Periodical_Id(@Param("in_periodical_id") long id);

//    @Procedure(name = User.NamedQuery_GetSubscriptions)
//    List<SubscriptionsDTO> getSubscriptions(@Param("in_id") long id);
}
