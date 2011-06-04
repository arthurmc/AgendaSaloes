package br.com.saloes.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.googlecode.objectify.Key;

@Entity
@XmlRootElement(name="consulta")
@JsonIgnoreProperties(value={"profissao", "funcionario"})
public class Consulta implements Serializable, Comparable<Consulta> {

	private static final long serialVersionUID = 4606123910078492549L;

	@Id
	private Long id;
	private Key<Funcionario> funcionario;
	private Key<Profissao> profissao;
	private Key<Usuario> usuario;
	private Date dia;
	private Date hora;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFuncionario(Key<Funcionario> funcionario) {
		this.funcionario = funcionario;
	}

	public Key<Funcionario> getFuncionario() {
		return funcionario;
	}

	public void setProfissao(Key<Profissao> profissao) {
		this.profissao = profissao;
	}

	public Key<Profissao> getProfissao() {
		return profissao;
	}
	
	public Key<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(Key<Usuario> usuario) {
		this.usuario = usuario;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Date getDia() {
		return dia;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getHora() {
		return hora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((profissao == null) ? 0 : profissao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consulta other = (Consulta) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (profissao == null) {
			if (other.profissao != null)
				return false;
		} else if (!profissao.equals(other.profissao))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Consulta dataParaComparar) {
		int comparacaoDeDia = dia.compareTo(dataParaComparar.dia);
		if (comparacaoDeDia != 0)
			return comparacaoDeDia;
		
		int comparacaoDeHora = hora.compareTo(dataParaComparar.hora);
		if (comparacaoDeHora != 0)
			return comparacaoDeHora;
		
		return 0;
	}

	public boolean temConflitoComNovoHorario(Profissao servicoDaConsultaJaMarcada, Calendar horarioDaNovaConsulta, Profissao servicoDaNovaConsulta) {
		final int TOLERANCIA_CINCO_MINUTOS = (1000 * 60 * 5) + 50;
		
		if (servicoDaConsultaJaMarcada.getId() != this.getProfissao().getId())
			throw new IllegalStateException("Informar a profissão que é referente a consulta.");

		Calendar horarioDaNovaConsultaCalendar = Calendar.getInstance();
		horarioDaNovaConsultaCalendar.setTimeInMillis(horarioDaNovaConsulta.getTimeInMillis());
		horarioDaNovaConsultaCalendar.add(Calendar.MINUTE, Integer.valueOf(servicoDaNovaConsulta.getTempoQueConsome()));
		if (horarioDaNovaConsultaCalendar.getTimeInMillis() <= hora.getTime() + TOLERANCIA_CINCO_MINUTOS) {
			return false;
		}
		
		horarioDaNovaConsultaCalendar = Calendar.getInstance();
		horarioDaNovaConsultaCalendar.setTimeInMillis(hora.getTime());
		horarioDaNovaConsultaCalendar.add(Calendar.MINUTE, Integer.valueOf(servicoDaConsultaJaMarcada.getTempoQueConsome()));
		
		if (horarioDaNovaConsultaCalendar.getTimeInMillis() <= horarioDaNovaConsulta.getTimeInMillis() + TOLERANCIA_CINCO_MINUTOS) {
			return false;
		}
		
		return true;
	}
}