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
			});
			
			$("input[name='usuario.nome']").rules("add", {
				required: true,
				messages: {required: "O campo 'Nome' é obrigatório"}
			});
			
			$("input[name='usuario.telefoneResidencial']").mask("(99) 9999-9999");
			$("input[name='usuario.celular']").mask("(99) 9999-9999");
			
			$("input[name='usuario.email']").rules("add", {
				required: true,
				email: true,
				messages: {
					required: "O campo 'Email' é obrigatório",
					email: "Digite um email válido"
				}
			});
			
			$("input[name='usuario.senha']").rules("add", {
				required: true,
				messages: {required: "O campo 'Senha' é obrigatório"}
			});
			
			$.validator.addMethod("validaCheckboxes", function(value, element) {
				return $(".radioToValidate:checked").length > 0;
			}, "Escolha um tipo de autenticação");
			
			$(".radioToValidate:first").rules("add", {
				validaCheckboxes: true
			});
			
		});
	</script>
</head>

<form action="<c:url value="/usuarios"/>" method="post" name="form">
  
	<c:if test="${not empty usuario.id}">
		<input type="hidden" name="usuario.id" value="${usuario.id}"/>
		<input type="hidden" name="_method" value="put"/>
	</c:if>
	<div class="showInfos">
		<div class="field">
			Nome:<br />
			<input type="text" name="usuario.nome" value="${usuario.nome}"/>
		</div>
		
		<div class="field">
			Telefone Residencial:<br />
			<input type="text" name="usuario.telefoneResidencial" value="${usuario.telefoneResidencial}"/>
		</div>
		
		<div class="field">
			Celular:<br />
			<input type="text" name="usuario.celular" value="${usuario.celular}"/>
		</div>
		
		<div class="field">
			Email:<br />
			<input type="text" name="usuario.email" value="${usuario.email}"/>
		</div>
		
		<div class="field">
			Senha:<br />
			<input type="password" name="usuario.senha" value="${usuario.senha}"/>
		</div>
		
		<input type="hidden" name="usuario.tipoAutenticacao" value="${usuario.tipoAutenticacao}"/>
		
		<div class="actions">
			<button type="submit">Enviar</button>
			<a href="<c:url value="/usuarios"/>">Retornar</a>
		</div>
		
	</div>
</form>
