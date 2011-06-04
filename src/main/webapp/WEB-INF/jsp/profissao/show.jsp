<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="showInfos">
		<p>
		  <b>Nome:</b>
		   ${profissao.nome}
		</p>
		<p>
		  <b>Tempo que consome:</b>
		   ${profissao.tempoQueConsome}
		</p>


		<div class="contentCenter">		
			<a href="<c:url value="/profissoes/${profissao.id}/edit"/>">Editar</a>
			<a href="<c:url value="/profissoes"/>">Retornar</a>
		</div>
	</div>
</body>
