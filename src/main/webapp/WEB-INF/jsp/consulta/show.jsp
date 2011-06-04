<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body>
	<div class="showInfos">
		<p>
		  <b>Dia:</b>
		   <fmt:formatDate value='${consulta.dia}' pattern='dd/MM/yyyy' />
		</p>
		
		<p>
		  <b>Hora:</b>
		   <fmt:formatDate value='${consulta.hora}' pattern='HH:mm' />
		</p>
		
		<p>
		  <b>Funcionário(a):</b>
		   ${mapConsultasParaFuncionarios[consulta].nome}
		</p>
		
		<p>
		  <b>Cliente(a):</b>
		   ${mapConsultasParaClientes[consulta].nome}
		</p>
		
		<p>
		  <b>Tipo de serviço:</b>
		   ${mapConsultasParaProfissoes[consulta].nome}
		</p>
		<div class="centerOfFrame">
			<a href="<c:url value="/consultas"/>">Retornar</a>
		</div>
	</div>
</body>