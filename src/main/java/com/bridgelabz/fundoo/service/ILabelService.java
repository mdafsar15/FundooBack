package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.dto.LabelUpdate;
import com.bridgelabz.fundoo.model.Label;

public interface ILabelService {

//	public long createLabel(String token, LabelDto labelDTO);

	//public boolean createLabelAndMap(String token, long noteId, LabelDto labelDTO);

	boolean editLabel(String token, LabelDto labelDTO, long labelId);

	//boolean deleteLabel(String token, long labelId);

	List<Label> foundLabelsList(String token);

	//public boolean addNoteLabel(String token, long noteId, long labelId);

	//boolean remoNoteLabel(String token, long noteId, long labelId);
	
	 boolean createLabel(LabelDto labelDto,String token);
	 boolean updateLabel(LabelUpdate labelUpdate,String token);
	 boolean addLabel(LabelDto labelDto, long notesId, String token);
	 boolean removeLabel(Long labelId,Long notesId,String token);

}
