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
		#profissoesTable {
			position: absolute;
			padding-left: 50%;
			left: -310px;
		}
		.dataTables_wrapper {
			width: 620px;
		}
	</style>
</head>
<body>
	<div id="profissoesTable">
		<div class="contentCenter">
			<h1>Lista de Profissões</h1>
		</div>
		<table id="tabelaDeProfissoes" cellpadding="0" cellspacing="0" border="0" class="display">
			<thead>
				<tr>
					<th>Nome</th>
					<th style="max-width: 60px;">Tempo que consome</th>
					<th class="noImage iconCell"></th>
					<th class="noImage iconCell"></th>
					<th class="noImage iconCell"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${profissaoList}" var="profissao">
					<tr>
						<td>${profissao.nome}</td>
						<td>${profissao.tempoQueConsome}</td>
						<td class="contentCenter">
							<a href="<c:url value="/profissoes/${profissao.id}"/>" class="viewButton">visualizar</a>
						</td>
						<td class="contentCenter">
							<a href="<c:url value="/profissoes/${profissao.id}/edit"/>" class="editButton">editar</a>
						</td>
						<td class="contentCenter">
							<form action="<c:url value="/profissoes/${profissao.id}"/>" method="post">
								<input type="hidden" name="_method" value="delete" />
								<button type="submit" onclick="return confirm('Are you sure?')" class="deleteButton">excluir</button>
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<div class="contentCenter">
			<a href="<c:url value="/profissoes/new"/>">Nova Profissão</a>
			<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
				<a href="<c:url value="/"/>">Retornar</a>
			</c:if>
		</div>
	</div>
</body>
