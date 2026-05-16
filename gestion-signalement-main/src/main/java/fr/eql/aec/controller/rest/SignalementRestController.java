package fr.eql.aec.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.aec.entity.Signalement;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.IllegalOperationException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;
import fr.eql.aec.service.MotifSignalementService;
import fr.eql.aec.service.SignalementService;

@RestController
@RequestMapping("/signalements")
public class SignalementRestController {
	@Autowired
	private SignalementService signalementService;
	@Autowired
	private MotifSignalementService motifSignalementService;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/simpleListe")
	public List<Signalement> getAll() {
		return signalementService.findAll();
	}
	
	@GetMapping("/countForMe/{type}/{identifiant}")
	public Long countForMe(@PathVariable(required = true) String type, @PathVariable(required = true) Long identifiant) {
		return signalementService.countByTypeAndIdentifiant(type, identifiant);
	}

	@GetMapping
	public Page<Signalement> findAllByPage(Pageable page) {
		return signalementService.findAllByPage(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(required = true) Long id) {
		Signalement result;
		try {
			result = signalementService.findOne(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody(required = true) Signalement signalement, @PathVariable(required = true) Long id) {
		Signalement saved;
		try {
			Assert.notNull(signalement, "Ne peut pas être null.");
			Assert.notNull(signalement.getId(), "L'identifiant ne peut être null");
			Assert.isTrue(id.equals(signalement.getId()), "L'id de l'url ne correspond pas à celui de l'objet envoyé.");
			motifSignalementService.findOne(signalement.getMotifSignalement().getId());
			saved = signalementService.save(signalement);
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch(NotValidObjectException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(saved,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody(required = true) Signalement signalement){
		Signalement saved;
		try {
			Assert.isNull(signalement.getId(), "L'identifiant doit être null");
			Assert.notNull(signalement, "Ne peut pas être null.");
			motifSignalementService.findOne(signalement.getMotifSignalement().getId());
			saved = signalementService.save(signalement);
		} catch (NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(saved, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			signalementService.delete(id);
			mapRes.put("message", "signalement bien supprimée pour l'id "+id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (IllegalOperationException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Object>(mapRes,HttpStatus.OK);
	}
}