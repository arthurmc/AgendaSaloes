package br.com.saloes.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="profissao")
public class Profissao implements Serializable {
	
	private static final long serialVersionUID = -2604556464215274321L;
	
	@Id
	private Long id;
	private String nome;
	private String tempoQueConsome;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setTempoQueConsome(String tempoQueConsome) {
		this.tempoQueConsome = tempoQueConsome;
	}
	
	public String getTempoQueConsome() {
		return tempoQueConsome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tempoQueConsome == null) ? 0 : tempoQueConsome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Profissao other = (Profissao) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tempoQueConsome == null) {
			if (other.tempoQueConsome != null)
				return false;
		} else if (!tempoQueConsome.equals(other.tempoQueConsome))
			return false;
		return true;
	}
}