package fr.eql.aec.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.aec.entity.MotifSignalement;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;

public interface MotifSignalementService {
	MotifSignalement findOne(Long id) throws ResourceNotFoundException;
	Page<MotifSignalement> findAllByPage(Pageable page);
	List<MotifSignalement> findAll();
	List<MotifSignalement> search(String Search) throws ResourceNotFoundException;
	MotifSignalement save(MotifSignalement motifSignalement) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}