<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<script type="text/javascript">
		$(document).ready(function () {
			$('.clock').timepicker({});

			$("form[name='form']").validate({
				errorLabelContainer: "#caixaDeValidacao",
				wrapper: "li"
			})
			
			$("input[name='funcionario.nome']").rules("add", {
				required: true,
				messages: {required: "O campo 'nome' é obrigatório"}
			})
			
			$.validator.addMethod("validaCheckboxes", function(value, element) {
				return $(".checkToValidate:checked").length > 0;
			}, "Escolha ao menos uma profissão.");
			
			$("input[name='funcionario.inicioJornadaTrabalho']").mask("99:99",{placeholder:" "});
			$("input[name='funcionario.inicioJornadaTrabalho']").rules("add", {
				required: true,
				messages: {required: "O campo 'Inicio da jornada de trabalho' é obrigatório"}
			})

			$("input[name='funcionario.fimJornadaTrabalho']").mask("99:99",{placeholder:" "});
			$("input[name='funcionario.fimJornadaTrabalho']").rules("add", {
				required: true,
				messages: {required: "O campo 'Fim da jornada de trabalho' é obrigatório"}
			})
			
			$(".checkToValidate:first").rules("add", {
				validaCheckboxes: true
			})
		});
	</script>
</head>

<c:if test="${not empty errors}">
	<c:forEach items="${errors}" var="error">
		 ${error.category} - ${error.message}<br />
	</c:forEach>
</c:if>

<form action="<c:url value="/funcionarios"/>" method="post" name="form">
  
	<c:if test="${not empty funcionario.id}">
		<input type="hidden" name="funcionario.id" value="${funcionario.id}"/>
		<input type="hidden" name="_method" value="put"/>
	</c:if>

	<div class="showInfos">
		<div class="field">
			Nome:<br />
			<input type="text" name="funcionario.nome" value="${funcionario.nome}"/>
		</div>
  
		<div class="field">
			Profissao:<br />
			<c:set var="contemProfissao" value="false" />
			<c:forEach items="${profissoesList }" var="profissao" varStatus="count">
				<c:forEach items="${profissoesDoFuncionario }" var="profissaoDoFuncionario">
			 		<c:if test="${profissao eq profissaoDoFuncionario }">
			 			<c:set var="contemProfissao" value="true" />
			 		</c:if>
			 	</c:forEach>
				<input type="checkbox" value="${profissao.id }" name="profissao[${count.index}].id" class="checkToValidate"
				 	<c:if test="${contemProfissao }">
				 		<c:set var="contemProfissao" value="false" />
				 		checked="true"
				 	</c:if>
			  	/>${profissao.nome }<br />
			</c:forEach>
		</div>
		
		<div class="field">
		  Inicio da jornada de trabalho:<br />
		  <input type="text" name="funcionario.inicioJornadaTrabalho" value="<fmt:formatDate value='${funcionario.inicioJornadaTrabalho}' pattern='HH:mm' />" class="clock" />
		</div>
  
		<div class="field">
			Fim da jornada de trabalho:<br />
			<input type="text" name="funcionario.fimJornadaTrabalho" value="<fmt:formatDate value='${funcionario.fimJornadaTrabalho}' pattern='HH:mm' />" class="clock" />
		</div>
	  
		<div class="actions">
			<button type="submit">Enviar</button>
			<a href="<c:url value="/funcionarios"/>">Retornar</a>
		</div>
	</div>
</form>
