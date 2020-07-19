package com.bridgelabz.fundoo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.dto.LabelUpdate;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.UserDoesNotExistException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.ILabelRepository;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.repository.LabelRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class LabelService implements ILabelService {
	@Autowired
	private IUserRepository uRepo;
	@Autowired
	private ILabelRepository lRepo;
	@Autowired
	private INoteRepository nRepo;
	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository usersRepository;

	@Transactional
	public boolean createLabel(LabelDto labelDto, String token) {
		Long userId = JwtGenerator.decodeToken(token);
		// log.info("----------------------------------------------------------token=" +
		// token);
		User user = usersRepository.getusersByid(userId);
		if (user != null) {
			Label label = new Label();
			Label label1 = lRepo.fetchbyLabel(userId, labelDto.getLabelName());
			if (label1 == null) {
				BeanUtils.copyProperties(labelDto, label);
				label.setU_id(user.getId());
				labelRepository.saveLabel(label);
			} else {
				throw new LabelException("already label exits", 403);
			}
		} else {
			throw new UserDoesNotExistException("there is no user found");
		}
		return true;
	}

	public boolean updateLabel(LabelUpdate labelUpdate, String token) {
		Long userId = JwtGenerator.decodeToken(token);
		User user = usersRepository.getusersByid(userId);
		if (user != null) {
			Label label = labelRepository.fetchLabelById(labelUpdate.getLabelId());
			if (label != null) {
				label.setLabelName(labelUpdate.getLabelName());
				labelRepository.saveLabel(label);
				return true;
			} else {
				throw new LabelException("there is no label with this user id", 403);
			}
		}
		return false;
	}

	@Override
	public boolean addLabel(LabelDto labelDto,long notesId, String token) {
		Note notes = nRepo.findBynotesId(notesId);
	//	User user = usersRepository.getusersByid(userId);
		if (notes != null) {
			Label label = new Label();
			Label label1 = lRepo.fetchLabel(labelDto.getLabelName());
			if (label1 == null) {
				BeanUtils.copyProperties(labelDto, label);
				label.setU_id(notes.getN_id());
				labelRepository.saveLabel(label);
			} else {
				throw new LabelException("already label exits", 403);
			}
		} else {
			throw new UserDoesNotExistException("there is no user found");
		}
		return true;
	}

	@Override
	public boolean removeLabel(Long labelId, Long notesId, String token) {
		Long userId = JwtGenerator.decodeToken(token);
		User user = usersRepository.getusersByid(userId);
		if (user != null) {
			Note note = nRepo.findBynotesId(notesId);

			Label label = labelRepository.fetchLabelById(labelId);
			if (label != null) {
				note.getLabelsList().remove(label);
				labelRepository.saveLabel(label);
				return true;
			}
		} else {
			throw new UserDoesNotExistException("user is not availabe");
		}
		return false;

	}

//	@Override
//	public long createLabel(String token, LabelDto lDto) {
//
//		User fetchedUser = uRepo.getUser(JwtGenerator.decodeToken(token));
//		Label fetchedLabel = lRepo.findOneBylabelName(lDto.getLabelName());
//
//		if (fetchedLabel == null) {
//			Label newLabel = new Label();
//			BeanUtils.copyProperties(lDto, newLabel);
//			fetchedUser.getLabels().add(newLabel);
//			lRepo.save(newLabel);
//			return lRepo.findLabelId(lDto.getLabelName(), fetchedUser.getId());
//		}
//		throw new LabelException("Label already exists", 403);
//
//	}

//	@Override
//	public boolean createLabelAndMap(String token, long noteId, LabelDto lDTO) {
//
//		User fetchedUser = uRepo.getUser(JwtGenerator.decodeToken(token));
//		Note fetchedNote = nRepo.getNote(noteId);
//		Label fetchedLabel = lRepo.findOneBylabelName(lDTO.getLabelName());
//
//		if (fetchedLabel == null) {
//			Label newLabel = new Label();
//			BeanUtils.copyProperties(lDTO, newLabel);
//
//			fetchedUser.getLabels().add(newLabel);
//			fetchedNote.getLabelsList().add(newLabel);
//			lRepo.save(newLabel);
//			return true;
//		}
//		throw new LabelException("Label already exists", 403);
//	}

	@Override
	public boolean editLabel(String token, LabelDto lDto, long lId) {

		uRepo.getUser(JwtGenerator.decodeToken(token));
		Optional<Label> fetchedLabel = lRepo.findById(lId);

		if (fetchedLabel.isPresent()) {
			if (lRepo.checkLabelWithDb(lDto.getLabelName()).isEmpty()) {
				lRepo.updateLabelName(lDto.getLabelName(), fetchedLabel.get().getL_Id());
				return true;
			}
			return false;
		}
		throw new LabelException("Label not found", 400);
	}

//	@Override
//	public boolean deleteLabel(String token, long lId) {
//
//		uRepo.getUser(JwtGenerator.decodeToken(token));
//		Optional<Label> fetchedLabel = lRepo.findById(lId);
//		if (fetchedLabel.isPresent()) {
//			lRepo.delete(fetchedLabel.get());
//			return true;
//		}
//		throw new LabelException("Label not found", 400);
//	}

	@Override
	public List<Label> foundLabelsList(String token) {

		uRepo.getUser(JwtGenerator.decodeToken(token));
		return lRepo.getAllLabels();
	}

//	@Override
//	public boolean addNoteLabel(String token, long noteId, long labelId) {
//
//		uRepo.getUser(JwtGenerator.decodeToken(token));
//		Note fetchedNote = nRepo.getNote(noteId);
//		Label fetchedLabel = labelRepository.fetchLabelById(labelId);
//		if (fetchedNote!=null) {
////			fetchedNote.getLabelsList().add(fetchedLabel.get());
////			lRepo.save(fetchedLabel.get());
//			fetchedLabel.getNoteList().add(fetchedNote);
//			labelRepository.saveLabel(fetchedLabel);
//			return true;
//		}
//		throw new LabelException("Label already exists", 400);
//	}

//	@Override
//	public boolean remoNoteLabel(String token, long noteId, long labelId) {
//
//		uRepo.getUser(JwtGenerator.decodeToken(token));
//		Note fetchedNote = nRepo.getNote(noteId);
//		Optional<Label> fetchedLabel = lRepo.findById(labelId);
//		if (fetchedLabel.isPresent()) {
//			fetchedNote.getLabelsList().remove(fetchedLabel.get());
//			nRepo.saveOrUpdate(fetchedNote);
//			return true;
//		}
//		throw new LabelException("label not found", 400);
//	}

}