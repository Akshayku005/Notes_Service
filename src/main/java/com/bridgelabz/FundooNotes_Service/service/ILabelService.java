package com.bridgelabz.FundooNotes_Service.service;

import com.bridgelabz.FundooNotes_Service.dto.LabelDto;
import com.bridgelabz.FundooNotes_Service.dto.ResponseDTO;
import com.bridgelabz.FundooNotes_Service.model.LabelModel;

import java.util.List;

public interface ILabelService {

	LabelModel addLabel(LabelDto labelDto, String token);

	LabelModel updateLabel(LabelDto labelDto, Long id, String token);

	List<LabelModel> getAllLabels(String token);

	ResponseDTO deleteLabel(Long id, String token);
}