package fr.eql.aec.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@NamedQueries({
	@NamedQuery(name="Signalement.countByTypeAndIdentifiant", query="SELECT count(*) FROM Signalement s WHERE s.type=:type AND s.identifiant=:identifiant")})
@Entity
@Getter @Setter @NoArgsConstructor 
public class Signalement implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "signalement";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreatedDate
	private Date date_de_signalement = new Date();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy", iso = ISO.DATE)
	private Date date_de_traitement;
	private String type;
	private Long identifiant;
	private Long userid;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private MotifSignalement motifSignalement;
}