package fr.eql.aec.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.eql.aec.entity.MotifSignalement;
import fr.eql.aec.entity.Signalement;
import fr.eql.aec.exception.NotValidObjectException;
import fr.eql.aec.exception.ResourceNotFoundException;
import fr.eql.aec.service.MotifSignalementService;

@Profile("initData")
@Component
public class InitDataSet {
	@Autowired
	private MotifSignalementService motifSignalementService;;

	@PostConstruct
	public void initJeuxDeDonnees() {

		MotifSignalement ms = new MotifSignalement();
		ms.setId(null);
		ms.setLibelle_motif_signalement("contenu innaproprié");

		MotifSignalement ms1 = new MotifSignalement();
		ms1.setId(null);
		ms1.setLibelle_motif_signalement("contenu violant");

		MotifSignalement ms2 = new MotifSignalement();
		ms2.setId(null);
		ms2.setLibelle_motif_signalement("contenu à caractère indécent");

		MotifSignalement ms3 = new MotifSignalement();
		ms3.setId(null);
		ms3.setLibelle_motif_signalement("autre");

		Signalement s = new Signalement();		
		s.setId(null);
		s.setIdentifiant(1L);
		s.setType("annonce");
		s.setDate_de_traitement(null);
		s.setMotifSignalement(ms);

		Signalement s1 = new Signalement();		
		s1.setId(null);
		s1.setIdentifiant(1L);
		s1.setType("publication");
		s1.setDate_de_traitement(null);
		s1.setMotifSignalement(ms1);

		Signalement s2 = new Signalement();		
		s2.setId(null);
		s2.setIdentifiant(1L);
		s2.setType("annonce");
		s2.setDate_de_traitement(null);
		s2.setMotifSignalement(ms2);

		try {
			motifSignalementService.save(ms);
			motifSignalementService.save(ms1);
			motifSignalementService.save(ms2);
			motifSignalementService.save(ms3);

			//signalementService.save(s);
			//signalementService.save(s1);
			//signalementService.save(s2);
		} catch (NotValidObjectException e) {
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

	}
}