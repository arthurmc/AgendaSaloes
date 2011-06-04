package br.com.saloes.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.googlecode.objectify.Key;

@Entity
@XmlRootElement(name="funcionario")
@JsonIgnoreProperties(value={"profissao"})
public class Funcionario implements Serializable {
	
	private static final long serialVersionUID = -1580894263533784446L;
	
	@Id
	private Long id;
	private String nome;
	private List<Key<Profissao>> profissao;
	private Date inicioJornadaTrabalho;
	private Date fimJornadaTrabalho;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setProfissao(List<Key<Profissao>> profissao) {
		this.profissao = profissao;
	}
	
	public List<Key<Profissao>> getProfissao() {
		return profissao;
	}

	public void setInicioJornadaTrabalho(Date inicioJornadaTrabalho) {
		this.inicioJornadaTrabalho = inicioJornadaTrabalho;
	}

	public Date getInicioJornadaTrabalho() {
		return inicioJornadaTrabalho;
	}

	public void setFimJornadaTrabalho(Date fimJornadaTrabalho) {
		this.fimJornadaTrabalho = fimJornadaTrabalho;
	}

	public Date getFimJornadaTrabalho() {
		return fimJornadaTrabalho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fimJornadaTrabalho == null) ? 0 : fimJornadaTrabalho.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicioJornadaTrabalho == null) ? 0 : inicioJornadaTrabalho.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (fimJornadaTrabalho == null) {
			if (other.fimJornadaTrabalho != null)
				return false;
		} else if (!fimJornadaTrabalho.equals(other.fimJornadaTrabalho))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicioJornadaTrabalho == null) {
			if (other.inicioJornadaTrabalho != null)
				return false;
		} else if (!inicioJornadaTrabalho.equals(other.inicioJornadaTrabalho))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}