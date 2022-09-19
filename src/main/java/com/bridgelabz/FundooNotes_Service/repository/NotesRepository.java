package com.bridgelabz.FundooNotes_Service.repository;

import com.bridgelabz.FundooNotes_Service.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query(value = "select * from notes where pin = true", nativeQuery = true)
    List<Notes> findAllByPin();

    @Query(value = "select * from notes where is_archieve  = true", nativeQuery = true)
    List<Notes> findAllByisArchieve();

    @Query(value = "select * from notes where Trash = true", nativeQuery = true)
    List<Notes> findAllByTrash();

    Optional<Notes> findByUserIdAndNotesId(Long userId, Long notesId);
    List<Notes> findByUserId(Long userId);
}
