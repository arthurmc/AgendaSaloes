<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty errors}">
	<c:forEach items="${errors}" var="error">
		 ${error.category} - ${error.message}<br />
	</c:forEach>
</c:if>

<head>
	<script type="text/javascript">
		$(document).ready(function () {
			$("form[name='form']").validate({
				errorLabelContainer: "#caixaDeValidacao",
				wrapper: "li"
			})

			$("input[name='profissao.nome']").rules("add", {
				required: true,
				messages: {required: "Campo 'Nome' é obrigatório"}
			});
			
			$("input[name='profissao.tempoQueConsome']").rules("add", {
				required: true,
				maxlength: 3,
				digits: true,
				messages: {
					required: "campo 'Tempo que consome' obrigatório",
					digits: "Digite apenas números no campo 'Tempo que consome'"
				}
			});
		});
	</script>
</head>

<form action="<c:url value="/profissoes"/>" method="post" name="form">
	<div class="showInfos">
		<c:if test="${not empty profissao.id}">
		  <input type="hidden" name="profissao.id" value="${profissao.id}"/>
		  <input type="hidden" name="_method" value="put"/>
		</c:if>
		
		<div class="field">
			Nome:<br />
			<input type="text" name="profissao.nome" value="${profissao.nome}"/>
		</div>
		
		<div class="field">
			Tempo que consome (em minutos): <br />
			<input type="text" name="profissao.tempoQueConsome" value="${profissao.tempoQueConsome}" maxlength="3" />
		</div>
		
		<div class="actions">
			<button type="submit">Enviar</button>
			<a href="<c:url value="/profissoes"/>">Retornar</a>
		</div>
	</div>
</form>


