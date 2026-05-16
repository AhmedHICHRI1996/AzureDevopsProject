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

import fr.eql.aec.dao.MotifSignalementRepository;
import fr.eql.aec.entity.MotifSignalement;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;

@Service
@Transactional
public class MotifSignalementServiceImpl implements MotifSignalementService{
	@Autowired
	private MotifSignalementRepository motifSignalementRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public MotifSignalement findOne(Long id) throws ResourceNotFoundException {
		if(!motifSignalementRepository.existsById(id))		
			throw new ResourceNotFoundException("MotifSignalement introuvable avec cet id : "+id);
		return motifSignalementRepository.findById(id).get();
	}	

	@Override
	public List<MotifSignalement> search(String search) throws ResourceNotFoundException { 
		return null;
	}	

	@Override
	public Page<MotifSignalement> findAllByPage(Pageable page) {
		return motifSignalementRepository.findAll(page);
	}

	@Override
	public List<MotifSignalement> findAll() {
		return motifSignalementRepository.findAll();
	}

	@Override
	public MotifSignalement save(MotifSignalement motifSignalement) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(motifSignalement, "Ne peut pas être null.");
		if(motifSignalement.getId() != null)
			this.findOne(motifSignalement.getId());
		Set<ConstraintViolation<MotifSignalement>> errors = validator.validate(motifSignalement);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<MotifSignalement> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return motifSignalementRepository.save(motifSignalement);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!motifSignalementRepository.existsById(id))
			throw new ResourceNotFoundException("MotifSignalement inexistante (pas supprimable) pour l'id : "+id);
		motifSignalementRepository.deleteById(id);
	}

	@Override
	public long count() {
		return motifSignalementRepository.count();
	}
}