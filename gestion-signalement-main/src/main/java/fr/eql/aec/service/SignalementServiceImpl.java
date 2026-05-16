package fr.eql.aec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.eql.aec.dao.SignalementRepository;
import fr.eql.aec.entity.Signalement;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;

@Service
@Transactional
public class SignalementServiceImpl implements SignalementService{
	@Autowired
	private SignalementRepository signalementRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Signalement findOne(Long id) throws ResourceNotFoundException {
		if(!signalementRepository.existsById(id))		
			throw new ResourceNotFoundException("Signalement introuvable avec cet id : "+id);
		return signalementRepository.findById(id).get();
	}	

	@Override
	public List<Signalement> search(String search) throws ResourceNotFoundException { 
		return null;
	}	

	@Override
	public Page<Signalement> findAllByPage(Pageable page) {
		return signalementRepository.findAll(page);
	}

	@Override
	public List<Signalement> findAll() {
		return signalementRepository.findAll();
	}

	@Override
	public Signalement save(Signalement signalement) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(signalement, "Ne peut pas être null.");
		if(signalement.getId() != null)
			this.findOne(signalement.getId());
		Set<ConstraintViolation<Signalement>> errors = validator.validate(signalement);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Signalement> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return signalementRepository.save(signalement);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!signalementRepository.existsById(id))
			throw new ResourceNotFoundException("Signalement inexistante (pas supprimable) pour l'id : "+id);
		signalementRepository.deleteById(id);
	}

	@Override
	public long countByTypeAndIdentifiant(String type, Long identifiant) {
		return signalementRepository.countByTypeAndIdentifiant(type, identifiant);
	}
	
	@Override
	public long count() {
		return signalementRepository.count();
	}
}