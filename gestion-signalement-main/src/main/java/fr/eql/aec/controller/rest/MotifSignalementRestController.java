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

import fr.eql.aec.entity.MotifSignalement;
import fr.eql.aec.exception.IllegalOperationException;
import fr.eql.aec.exception.AecServiceException;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;
import fr.eql.aec.service.MotifSignalementService;

@RestController
@RequestMapping("/motifSignalements")
public class MotifSignalementRestController {
	@Autowired
	private MotifSignalementService motifSignalementService;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/simpleListe")
	public List<MotifSignalement> getAll() {
		return motifSignalementService.findAll();
	}

	@GetMapping
	public Page<MotifSignalement> findAllByPage(Pageable page) {
		return motifSignalementService.findAllByPage(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(required = true) Long id) {
		MotifSignalement result;
		try {
			result = motifSignalementService.findOne(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody(required = true) MotifSignalement motifSignalement, @PathVariable(required = true) Long id) {
		MotifSignalement saved;
		try {
			Assert.notNull(motifSignalement, "Ne peut pas être null.");
			Assert.notNull(motifSignalement.getId(), "L'identifiant ne peut être null");
			Assert.isTrue(id.equals(motifSignalement.getId()), "L'id de l'url ne correspond pas à celui de l'objet envoyé.");
		
			saved = motifSignalementService.save(motifSignalement);
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
	public ResponseEntity<?> create(@RequestBody(required = true) MotifSignalement motifSignalement){
		MotifSignalement b;
		try {
			Assert.isNull(motifSignalement.getId(), "L'identifiant doit être null");
			Assert.notNull(motifSignalement, "Ne peut pas être null.");
		
			b = motifSignalementService.save(motifSignalement);
		} catch (NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(b, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			motifSignalementService.delete(id);
			mapRes.put("message", "motifSignalement bien supprimée pour l'id "+id);
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