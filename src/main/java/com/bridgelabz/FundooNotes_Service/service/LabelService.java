package com.bridgelabz.FundooNotes_Service.service;

import com.bridgelabz.FundooNotes_Service.Util.EmailSenderService;
import com.bridgelabz.FundooNotes_Service.Util.TokenUtility;
import com.bridgelabz.FundooNotes_Service.dto.LabelDto;
import com.bridgelabz.FundooNotes_Service.dto.ResponseDTO;
import com.bridgelabz.FundooNotes_Service.exception.NotesException;
import com.bridgelabz.FundooNotes_Service.model.LabelModel;
import com.bridgelabz.FundooNotes_Service.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	TokenUtility tokenUtil;

	@Autowired
	EmailSenderService mailService;

	@Autowired
	RestTemplate restTemplate;

	//Purpose:Service to add label
	@Override
	public LabelModel addLabel(LabelDto labelDto, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" +token, Boolean.class);
		if (isUserPresent) {
			LabelModel model = new LabelModel(labelDto);
			model.setRegisterDate(LocalDateTime.now());
			labelRepository.save(model);
			return model;
		}
		throw new NotesException(HttpStatus.FOUND,"Token Invalid");
	}

	//Purpose:Service to update label
	@Override
	public LabelModel updateLabel(LabelDto labelDto, Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
			if(isLabelPresent.isPresent()) {
				isLabelPresent.get().setLabelName(labelDto.getLabelName());
				isLabelPresent.get().setUpdateDate(LocalDateTime.now());
				labelRepository.save(isLabelPresent.get());
				return isLabelPresent.get();
			}
			throw new NotesException(HttpStatus.FOUND,"Label not present");
		}
		throw new NotesException(HttpStatus.FOUND,"Token Invalid");
	}

	//Purpose:Service to fetch all the labels
	@Override
	public List<LabelModel> getAllLabels(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> getAllLabels = labelRepository.findAll();
			if(getAllLabels.size()>0) {
				return getAllLabels;	
			}else {
				throw new NotesException(HttpStatus.FOUND,"Label not present");
			}
		}
		throw new NotesException(HttpStatus.FOUND,"Token Invalid");
	}

	//Purpose:Method to delete the label
	@Override
	public ResponseDTO deleteLabel(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://localhost:8081/admin/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel> isIdPresent = labelRepository.findById(id);
			if(isIdPresent.isPresent()) {
				labelRepository.deleteById(id);
				return new ResponseDTO("Successfully deleted",  isIdPresent.get());
			} else {
				throw new NotesException(HttpStatus.FOUND, "Label not found");
			}		
		}
		throw new NotesException(HttpStatus.FOUND, "Invalid token");
	}

}
