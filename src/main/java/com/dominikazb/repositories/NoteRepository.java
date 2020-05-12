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

    @Query(value = "SELECT * FROM note LEFT JOIN user ON user.user_id = note.user_id_fk " +
            "LEFT JOIN notes_categories ON notes_categories.id_note = note.note_id " +
            "LEFT JOIN category ON category.category_id = notes_categories.id_category " +
            "WHERE category.category_name = :categoryName AND user.username = :username", nativeQuery = true)
    List<Note> findAllNotesByCategoryName(String categoryName, String username);

    @Query(value = "SELECT * FROM note LEFT JOIN user ON user.user_id = note.user_id_fk " +
            "LEFT JOIN notes_tags ON notes_tags.id_note = note.note_id " +
            "LEFT JOIN tag ON notes_tags.id_tag = tag.tag_id " +
            "WHERE tag.tag_name = :tagName AND user.username = :username", nativeQuery = true)
    List<Note> findAllNotesByTagName(String tagName, String username);



}
