<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="showInfos">
		<p>
			<b>Nome:</b> ${usuario.nome}
		</p>
		<p>
			<b>Telefone Residencial:</b> ${usuario.telefoneResidencial}
		</p>
		<p>
			<b>Celular:</b> ${usuario.celular}
		</p>
		<p>
			<b>Email:</b> ${usuario.email}
		</p>
		<p>
			<b>Tipo de usuário:</b> ${usuario.tipoAutenticacao}
		</p>
		
		<div class="contentCenter">
			<a href="<c:url value="/usuarios/${usuario.id}/edit"/>">Editar</a>
			<a href="<c:url value="/usuarios"/>">Retornar</a>
		</div>
	</div>
</body>