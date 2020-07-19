package com.bridgelabz.fundoo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Label;

@Repository
@Transactional
public interface ILabelRepository extends JpaRepository<Label, Long> {

	public Label findOneBylabelName(String name);

	@Modifying
	@Transactional
	@Query(value = " update label set label_name = ? where l_id = ?  ", nativeQuery = true)
	public void updateLabelName(String name, Long id);

	@Query(value = " select l_id from label where label_name = ? and u_id = ?  ", nativeQuery = true)
	public long findLabelId(String name, Long id);

	@Query(value = "select * from label where label_name = ?", nativeQuery = true)
	public List<Label> checkLabelWithDb(String labelName);

	@Query(value = "select * from label", nativeQuery = true)
	public List<Label> getAllLabels();

	@Query("from Label where u_id=:u_id and label_name=:label_name")
	public Label fetchbyLabel(long u_id, String label_name);

	@Query("from Label where label_name=:label_name")
	public Label fetchLabel(String label_name);
}