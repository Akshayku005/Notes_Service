package com.bridgelabz.FundooNotes_Service.model;

import com.bridgelabz.FundooNotes_Service.dto.LabelDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "label")
@Data
@NoArgsConstructor
public class LabelModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String labelName;

	private Long userId;
	
	private Long noteId;
	
	private LocalDateTime registerDate;

	private LocalDateTime updateDate;
	@JsonIgnore
	@ManyToMany
	private List<Notes> notes;

	public LabelModel(LabelDto labelDto) {
		this.labelName = labelDto.getLabelName();
	}
}