package fr.eql.aec.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.aec.entity.Signalement;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;

public interface SignalementService {
	Signalement findOne(Long id) throws ResourceNotFoundException;
	Page<Signalement> findAllByPage(Pageable page);
	List<Signalement> findAll();
	List<Signalement> search(String Search) throws ResourceNotFoundException;
	Signalement save(Signalement signalement) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long countByTypeAndIdentifiant(String type, Long identifiant);
	long count();
}