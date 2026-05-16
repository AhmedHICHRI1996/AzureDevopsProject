package fr.eql.aec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<T, ID> extends JpaRepository<T, ID> {
	//@Query("MATCH (n) " + "WHERE (any(prop in keys(n) where n[prop] CONTAINS {search}))" + "RETURN n")
	//public List<T> search(@Param("search") String search);
}