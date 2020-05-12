package com.dominikazb.repositories;

import com.dominikazb.beans.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.id=:id")
    Optional<Note> findById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Note n where n.id = :id")
    void deleteNote(@Param("id") int id);

    @Query("SELECT n FROM Note n WHERE n.user.username = :username ORDER BY n.date DESC")
    List<Note> findAllByUserAndOrderByDate(String username);



}
