<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
	<script type="text/javascript">
		$(document).ready(function () {
			var tabela = $("#tabelaDeFuncionarios").dataTable({
				"bJQueryUI": true,
				"sPaginationType": "full_numbers"
			});
		});
	</script>
	<link type="text/css" href="<c:url value="/stylesheets/demo_table_jui.css"/>" rel="stylesheet" />
	<style type="text/css" media="screen"> 
		#funcionariosTable {
			position: absolute;
			padding-left: 50%;
			left: -330px;
		}
		.dataTables_wrapper {
			width: 660px;
		}
	</style>
</head>

<body>
	<div id="funcionariosTable">
		<div class="contentCenter">
			<h1>Lista de Funcionários</h1>
		</div>
		<table id="tabelaDeFuncionarios" cellpadding="0" cellspacing="0" border="0" class="display">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Início da jornada</th>
					<th>Fim da jornada</th>
					<th class="noImage"></th>
					<th class="noImage"></th>
					<th class="noImage"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${funcionarioList}" var="funcionario">
					<tr>
						<td>${funcionario.nome}</td>
						<td class="contentCenter"><fmt:formatDate value="${funcionario.inicioJornadaTrabalho}" pattern="HH:mm" /></td>
						<td class="contentCenter"><fmt:formatDate value="${funcionario.fimJornadaTrabalho}" pattern="HH:mm" /></td>
						<td class="contentCenter">
							<a href="<c:url value="/funcionarios/${funcionario.id}"/>" class="viewButton">visualizar</a>
						</td>
						<td class="contentCenter">
							<a href="<c:url value="/funcionarios/${funcionario.id}/edit"/>" class="editButton">editar</a>
						</td>
						<td class="contentCenter">
							<form action="<c:url value="/funcionarios/${funcionario.id}"/>" method="post">
								<input type="hidden" name="_method" value="delete"/>
								<button type="submit" onclick="return confirm('Are you sure?')" class="deleteButton">Excluir</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<div class="contentCenter">
			<a href="<c:url value="/funcionarios/new"/>">Novo Funcionário</a>
			<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
				<a href="<c:url value="/"/>">Retornar</a>
			</c:if>
		</div>
	</div>
</body>
