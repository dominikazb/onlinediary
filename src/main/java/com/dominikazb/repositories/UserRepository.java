package com.dominikazb.repositories;

import com.dominikazb.beans.Category;
import com.dominikazb.beans.Tag;
import com.dominikazb.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserNotOptionalByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT n.categories FROM User u JOIN Note n ON n.user.id = u.id WHERE u.username = :username")
    Set<Category> findCategoriesByUser(String username);

    @Query("SELECT n.tags FROM User u JOIN Note n ON n.user.id = u.id WHERE u.username = :username")
    Set<Tag> findTagsByUser(String username);

}
