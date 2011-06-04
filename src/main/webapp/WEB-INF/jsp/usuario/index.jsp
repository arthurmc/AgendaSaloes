<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<script type="text/javascript">
		$(document).ready(function() {
			var tabela = $("#tabelaDeProfissoes").dataTable({
				"bJQueryUI" : true,
				"sPaginationType" : "full_numbers"
			});
		});
	</script>
	<link type="text/css" href="<c:url value="/stylesheets/demo_table_jui.css"/>" rel="stylesheet" />
	<style type="text/css" media="screen">
		#usuariosTable {
			position: absolute;
			padding-left: 50%;
			left: -360px;
		}
		.dataTables_wrapper {
			width: 720px;
		}
	</style>
</head>
<body>
	<div id="usuariosTable">
		<div class="contentCenter">
			<h1>Lista de Usuários</h1>
		</div>
		<table id="tabelaDeProfissoes" cellpadding="0" cellspacing="0" border="0" class="display">
			<thead>
				<tr>
					<th>nome</th>
					<th class="telefoneTamanhoMinimo">tel. residencial</th>
					<th class="telefoneTamanhoMinimo">celular</th>
					<th>email</th>
					<th class="noImage iconCell"></th>
					<th class="noImage iconCell"></th>
					<th class="noImage iconCell"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarioList}" var="usuario">
					<tr>
						<td>${usuario.nome}</td>
						<td class="contentCenter">${usuario.telefoneResidencial}</td>
						<td class="contentCenter">${usuario.celular}</td>
						<td>${usuario.email}</td>
						<td class="contentCenter">
							<a href="<c:url value="/usuarios/${usuario.id}"/>" class="viewButton">visualizar</a>
						</td>
						<td class="contentCenter">
							<a href="<c:url value="/usuarios/${usuario.id}/edit"/>" class="editButton">editar</a>
						</td>
						<td class="contentCenter">
							<form action="<c:url value="/usuarios/${usuario.id}"/>" method="post">
								<input type="hidden" name="_method" value="delete"/>
								<button type="submit" onclick="return confirm('Are you sure?')" class="deleteButton">deletar</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<div class="contentCenter">
			<a href="<c:url value="/usuarios/new"/>">Novo Usuário</a>
			<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
				<a href="<c:url value="/"/>">Retornar</a>
			</c:if>
		</div>
	</div>
</body>
