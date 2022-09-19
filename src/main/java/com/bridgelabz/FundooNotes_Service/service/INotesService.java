package com.bridgelabz.FundooNotes_Service.service;

import com.bridgelabz.FundooNotes_Service.dto.NotesDto;
import com.bridgelabz.FundooNotes_Service.dto.ResponseDTO;
import com.bridgelabz.FundooNotes_Service.model.Notes;
import java.util.List;
import java.util.Optional;

public interface INotesService {

    Notes createNote(NotesDto notesDto, String token);

    Notes updateNote(NotesDto notesDto, Long notesId, String token);

    List<Notes> readAllNotes(String token);

    Optional<Notes> readNotesById(Long notesId, String token);

    Notes archieveNoteById(Long notesId, String token);

    Notes unArchieveNoteById(Long notesId, String token);

    Notes trashNote(Long notesId, String token);

    Notes restoreNote(Long notesId, String token);

    Notes deleteNote(Long notesId, String token);

    Notes changeNoteColor(Long notesId, String color, String token);

    Notes pinNote(Long notesId, String token);

    Notes unPinNote(Long notesId, String token);


    List<Notes> getAllPinnedNotes(String token);

    List<Notes> getAllArchievedNotes(String token);

    List<Notes> getAllTrashNotes(String token);

    Notes notesLabelList(Long notesId, Long labelId, String token);

    Notes addCollaborator(Long notesId, String collaborator, String token);

    Notes setRemainderTime(String remainderTime, String token, Long notesId);
}