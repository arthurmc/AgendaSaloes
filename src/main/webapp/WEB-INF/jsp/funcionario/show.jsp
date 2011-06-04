<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
	<div class="showInfos">
		<p>
		  <b>Nome:</b>
		   ${funcionario.nome}
		</p>
		<p>
		  <b>Profissoes:</b>
		  <c:forEach items="${profissoesMap[funcionario]}" var="profissao">
		 	${profissao.nome}, 
		 </c:forEach>
		</p>
		<p>
		  <b>Inicio da jornada de trabalho:</b> <fmt:formatDate value='${funcionario.inicioJornadaTrabalho}' pattern='HH:mm' />
		</p>
		  
		<p>
		  <b>Fim da jornada de trabalho:</b> <fmt:formatDate value='${funcionario.fimJornadaTrabalho}' pattern='HH:mm' />
		</p>
		
		<div class="contentCenter">
			<a href="<c:url value="/funcionarios/${funcionario.id}/edit"/>">Edit</a>
			<a href="<c:url value="/funcionarios"/>">Back</a>
		</div>
	</div>
</body>
