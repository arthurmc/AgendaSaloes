<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
	<li><a href="<c:url value="/consultas" />">Consultas</a></li>
	<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
		<li><a href="<c:url value="/usuarios" />">Usuários</a></li>
		<li><a href="<c:url value="/funcionarios" />">Funcionários</a></li>
		<li><a href="<c:url value="/profissoes" />">Profissões</a></li>
	</c:if>
</ul>
