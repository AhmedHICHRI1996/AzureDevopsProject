package fr.eql.aec.dao;

import fr.eql.aec.entity.Signalement;

public interface SignalementRepository extends AbstractRepository<Signalement, Long> {
	long countByTypeAndIdentifiant(String type, Long identifiant);
}