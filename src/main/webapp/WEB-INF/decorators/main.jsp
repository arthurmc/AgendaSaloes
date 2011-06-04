<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript">
		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', 'UA-23091776-1']);
		  _gaq.push(['_trackPageview']);
		 
		  (function() {
		    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();
		</script>
  
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><decorator:title default="Agenda de Salões"/></title>
		
		<link type="text/css" href="<c:url value="/stylesheets/smoothness/jquery-ui-1.8.12.custom.css"/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value="/stylesheets/scaffold.css"/>" rel="stylesheet" />  

		<script type="text/javascript" src="<c:url value="/javascripts/jquery-1.5.1.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascripts/jquery-ui-1.8.12.custom.min.js"/>"></script>
		
		<!-- datepicker and slider components (jQueryUI) are required for using any of these -->
		<script type="text/javascript" src="<c:url value="/javascripts/jquery-ui-timepicker-addon.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascripts/jquery.validate.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascripts/jquery.maskedinput-1.3.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascripts/jquery.dataTables.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascripts/jquery.zinputhint-1.2.min.js"/>"></script>
		<script type="text/javascript" charset="utf-8"> 
			$(function(){ 
				$('input[title!=""]').ztInputHint();
			});
		</script> 
		<decorator:head/>
  </head>
  <body>
  	<div id="wrapper">
  		<div id="headerWrapper">
		  	<div id="login_menu">
			  	<c:if test="${not usuarioWeb.autenticado }">
			  		<a class="loginLink" href="<c:url value="/login" />">Login</a>
			  	</c:if>
			  	<c:if test="${usuarioWeb.autenticado }">
			  		<a class="loginLink" href="<c:url value="/logout" />">Sair</a>
			  	</c:if>
		  	</div>
		  	<div id="validationBox"><span id="caixaDeValidacao"></span></div>
  		</div>
		<c:if test="${usuarioWeb.usuario.tipoAutenticacao eq 'ADMIN'}">
  			<div id="menuOptions">
		  		<page:applyDecorator name="menu" />
  			</div>
  		</c:if>
	  	<div id="content">
		    <decorator:body/>
	  	</div>
  	</div>
  </body>
</html>