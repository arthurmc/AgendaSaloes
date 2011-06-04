<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty errors}">
	<c:forEach items="${errors}" var="error">
		 ${error.category} - ${error.message}<br />
	</c:forEach>
</c:if>
<div id="divFormularioLogin">
	<form action="<c:url value="/login"/>" method="post">
	  <div class="field">
	    <input type="text" name="usuario.email" title="email" />
	  </div>
	  
	  <div class="field">
	    <input type="password" name="usuario.senha" title="senha" />
	  </div>
	
	  <div class="contentCenter">
	    <button type="submit">Entrar</button>
	  </div>
	</form>
</div>