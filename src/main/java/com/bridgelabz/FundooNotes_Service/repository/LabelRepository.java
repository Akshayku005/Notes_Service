package com.bridgelabz.FundooNotes_Service.repository;

import com.bridgelabz.FundooNotes_Service.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel, Long> {

}