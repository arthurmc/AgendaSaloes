<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
	<script type="text/javascript">
		$(document).ready(function () {
			var tabela = $("#tabelaDeConsultas").dataTable({
				"bJQueryUI": true,
				"sPaginationType": "full_numbers"
			});
		});
	</script>
	
	<link type="text/css" href="<c:url value="/stylesheets/demo_table_jui.css"/>" rel="stylesheet" />
	<style type="text/css" media="screen"> 
		#tabelaDeConsultas_wrapper {
			width: 660px;
		}
		
		div#consultaTable {
			position: absolute;
			padding-left: 50%;
			left: -330px;
		}
	</style>
</head>

<body>
	<div id="consultaTable">
		<div class="contentCenter">
			<h1>Lista de Consultas</h1>
		</div>
		<table id="tabelaDeConsultas" cellpadding="0" cellspacing="0" border="0" class="display">
			<thead>
				<tr>
					<th>dia</th>
					<th>hora</th>
					<th>funcionário</th>
					<th>tipo de servico</th>
					<th class="noImage"></th>
					<th class="noImage"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${consultaList}" var="consulta">
					<tr class="gradeC">
						<td><fmt:formatDate value='${consulta.dia}' pattern='dd/MM/yyyy' /></td>
						<td class="contentCenter"><fmt:formatDate value='${consulta.hora}' pattern='HH:mm' /></td>
						<td>${mapConsultasParaFuncionarios[consulta].nome}</td>
						<td>${mapConsultasParaProfissoes[consulta].nome}</td>
						<td class="contentCenter">
							<a href="<c:url value="/consultas/${consulta.id}"/>" class="viewButton">visualizar</a>
						</td>
						<td class="contentCenter">
							<form action="<c:url value="/consultas/${consulta.id}"/>" method="post">
								<input type="hidden" name="_method" value="delete" />
								<button type="submit" onclick="return confirm('Are you sure?')" class="deleteButton">excluir</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<div class="contentCenter">
			<a href="<c:url value="/consultas/new"/>">Nova Consulta</a>
			<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
				<a href="<c:url value="/"/>">Retornar</a>
			</c:if>
		</div>
	</div>
</body>