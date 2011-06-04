package br.com.saloes.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.saloes.infra.security.AutenticacaoType;

@Entity
@XmlRootElement(name="usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1055569761914433647L;
	
	@Id
	private Long id;
	private String nome;
	private String telefoneResidencial;
	private String celular;
	private String senha;
	private String email;
	private AutenticacaoType tipoAutenticacao;

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
	
	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}
	
	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public AutenticacaoType getTipoAutenticacao() {
		return tipoAutenticacao;
	}

	public void setTipoAutenticacao(AutenticacaoType tipoAutenticacao) {
		this.tipoAutenticacao = tipoAutenticacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		
		return true;
	}
}