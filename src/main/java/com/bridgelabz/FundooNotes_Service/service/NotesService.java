package com.bridgelabz.FundooNotes_Service.service;

import com.bridgelabz.FundooNotes_Service.Util.EmailSenderService;
import com.bridgelabz.FundooNotes_Service.Util.TokenUtility;
import com.bridgelabz.FundooNotes_Service.dto.NotesDto;
import com.bridgelabz.FundooNotes_Service.exception.NotesException;
import com.bridgelabz.FundooNotes_Service.model.LabelModel;
import com.bridgelabz.FundooNotes_Service.model.Notes;
import com.bridgelabz.FundooNotes_Service.repository.LabelRepository;
import com.bridgelabz.FundooNotes_Service.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService implements INotesService {

    @Autowired
    NotesRepository notesRepository;
    @Autowired
    LabelRepository labelRepository;

    @Autowired
    TokenUtility util;

    @Autowired
    EmailSenderService mailService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Notes createNote(NotesDto notesDto, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Notes model = new Notes(notesDto);
            model.setRegisterDate(LocalDateTime.now());
            model.setUserId(userId);
            notesRepository.save(model);
            mailService.sendEmail(model.getEmailId(), "Notes added ", "your notes added successfully with your userId "
                    + model.getUserId());
            return model;
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes updateNote(NotesDto notesDto, Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotePresent = notesRepository.findByUserIdAndNotesId(userId, notesId);

            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTitle(notesDto.getTitle());
                isNotePresent.get().setDescription(notesDto.getDescription());
                isNotePresent.get().setLabelId(notesDto.getLabelId());
                isNotePresent.get().setEmailId(notesDto.getEmailId());
                isNotePresent.get().setColor(notesDto.getColor());
                isNotePresent.get().setUpdateDate(LocalDateTime.now());
                isNotePresent.get().setUserId(userId);
                notesRepository.save(isNotePresent.get());
                String body = "Note updated successfully with Id " + isNotePresent.get().getNotesId();
                String subject = "Note updated Successfully";
                mailService.sendEmail(isNotePresent.get().getEmailId(), subject, body);
                return isNotePresent.get();
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public List<Notes> readAllNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            List<Notes> readAllNotes = notesRepository.findByUserId(userId);
            if (readAllNotes.size() > 0) {
                return readAllNotes;
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Optional<Notes> readNotesById(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                return isNotesPresent;
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes archieveNoteById(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setArchieve(true);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            };
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes unArchieveNoteById(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setArchieve(false);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes trashNote(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setTrash(true);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Token not find");
    }

    @Override
    public Notes restoreNote(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNotesPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setTrash(false);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes deleteNote(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);

            if (isNoteAndUserIdPresent.isPresent() && isNoteAndUserIdPresent.get().isTrash() == true) {
                notesRepository.delete(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }

    @Override
    public Notes changeNoteColor(Long notesId, String color, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setColor(color);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes pinNote(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setPin(true);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public Notes unPinNote(Long notesId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if (isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setPin(false);
                notesRepository.save(isNoteAndUserIdPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }


    @Override
    public List<Notes> getAllPinnedNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            List<Notes> user = notesRepository.findByUserId(userId);
            if (user.size() > 0) {
                return user;
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public List<Notes> getAllArchievedNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            List<Notes> user = notesRepository.findByUserId(userId);
            if (user.size() > 0) {
                return user;
            }
        }
        throw new NotesException(HttpStatus.NOT_FOUND, "Invalid token");
    }

    @Override
    public List<Notes> getAllTrashNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = util.decodeToken(token);
            List<Notes> user = notesRepository.findByUserId(userId);
                if(user.size()>0) {
                    List<Notes> isNotePresent = notesRepository.findAllByTrash();
                    if(isNotePresent.size() > 0) {
                        return isNotePresent;
                    }
            }
            throw new NotesException(HttpStatus.FOUND,"User Id not present");
        }
            throw new NotesException(HttpStatus.NOT_FOUND, "Invaild token ");

    }

    @Override
    public Notes notesLabelList(Long notesId, Long labelId, String token) {
        Long userId = util.decodeToken(token);
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> label = new ArrayList<>();
            List<Notes> notes = new ArrayList<>();
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if(isLabelPresent.isPresent()) {
                label.add(isLabelPresent.get());
            }
            Optional<Notes> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
            if(isNoteAndUserIdPresent.isPresent()) {
                isNoteAndUserIdPresent.get().setLabelList(label);
                notes.add(isNoteAndUserIdPresent.get());
                isLabelPresent.get().setNotes(notes);
                notesRepository.save(isNoteAndUserIdPresent.get());
                labelRepository.save(isLabelPresent.get());
                return isNoteAndUserIdPresent.get();
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }
    @Override
    public Notes addCollaborator(Long notesId, String collaborator, String token) {
        Long userId = util.decodeToken(token);
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<Notes> isNoteAndUserId= notesRepository.findByUserIdAndNotesId(userId, notesId);
            List<String> collaboratorList = new ArrayList<>();
            if (isNoteAndUserId.isPresent()) {
                boolean isEmailIdPresent = restTemplate.getForObject("http://localhost:8081/admin/validateemail/" + collaborator, Boolean.class);
                collaboratorList.add(collaborator);
                isNoteAndUserId.get().setCollaborator(collaboratorList);
                notesRepository.save(isNoteAndUserId.get());
                List<String> noteList = new ArrayList<>();

                noteList.add(isNoteAndUserId.get().getEmailId());
                Notes note = new Notes();
                note.setTitle(isNoteAndUserId.get().getTitle());
                note.setArchieve(isNoteAndUserId.get().isArchieve());
                note.setCollaborator(isNoteAndUserId.get().getCollaborator());
                note.setColor(isNoteAndUserId.get().getColor());
                note.setDescription(isNoteAndUserId.get().getDescription());
                note.setEmailId(isNoteAndUserId.get().getEmailId());
                note.setLabelId(isNoteAndUserId.get().getLabelId());
                note.setNotesId(isNoteAndUserId.get().getNotesId());
                note.setPin(isNoteAndUserId.get().isPin());
                note.setRegisterDate(isNoteAndUserId.get().getRegisterDate());
                note.setRemindertime(isNoteAndUserId.get().getRemindertime());
                note.setTrash(isNoteAndUserId.get().isTrash());
                note.setUpdateDate(isNoteAndUserId.get().getUpdateDate());
                note.setUserId(isNoteAndUserId.get().getUserId());
                notesRepository.save(note);
                return isNoteAndUserId.get();
            }
            throw new NotesException(HttpStatus.FOUND, "Collaborator not present");
        }
        throw new NotesException(HttpStatus.FOUND, "Token not found");
    }
    @Override
    public Notes setRemainderTime(String remainderTime, String token, Long notesId) {
        boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<Notes> isNotesPresent = notesRepository.findById(notesId);
            if(isNotesPresent.isPresent()) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDate remainder = LocalDate.parse(remainderTime, dateTimeFormatter);
                if(remainder.isBefore(today)) {
                    throw new NotesException(HttpStatus.FOUND, "invalid remainder time");
                }
                isNotesPresent.get().setRemindertime(remainderTime);
                notesRepository.save(isNotesPresent.get());
                return isNotesPresent.get();
            }
        }
        throw new NotesException(HttpStatus.FOUND, "Invalid token");
    }
}