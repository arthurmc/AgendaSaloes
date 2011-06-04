<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<style type="text/css">
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
		.ui-timepicker-div dl { text-align: left; }
		.ui-timepicker-div dl dt { height: 25px; }
		.ui-timepicker-div dl dd { margin: -25px 0 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
	</style>
	<script type="text/javascript">
	var indexUltimaProfissao = 0;
	
	$(document).ready(function() {
		$("form[name='form']").validate({
			errorLabelContainer: "#caixaDeValidacao",
			wrapper: "li"
		});
		
		$('#profissoesCombo').rules("add", {
			required: true,
			messages: {required: "Campo 'Profissão' é obrigatório"}
		});

		$('#profissoesCombo').change(function(event) {
			if (this.value == "" || this.selectedIndex != indexUltimaProfissao) {
				indexUltimaProfissao = this.selectedIndex;
				$("#funcionarioSelectDiv").html("");
				$("#datePickerDiv").html("");
				$("#horariosDisponiveisDiv").html("");
			}
			
			if (this.value != "") {
				$("#ajaxLoad").css("display", "block");
				$.getJSON('/funcionarios/profissoes/json', {id: this.value}, listaDeFuncionarios);
			}
		});
	});
		
	var listaDeFuncionarios = function (funcionarios) {
		$("#ajaxLoad").css("display", "none");
		$("#funcionarioSelectDiv").html("");
		$("#funcionarioSelectDiv").append("Funcionário(a):<br />");
		$("#funcionarioSelectDiv").append("<select name='funcionario.id' id='funcionarios'><option value=''></option></select>");
		
		for (i = 0; i < funcionarios.length; i++) {
			$("#funcionarios").append($("<option></option>").val(funcionarios[i].id).html(funcionarios[i].nome));
		}
		
		$("#funcionarios").rules("add", {
			required: true,
			messages: {required: "Campo 'Funcionário' é obrigatório"}
		});
		
		$("#funcionarios").change(function(event) {
			if (this.value == "") {
				$("#datePickerDiv").html("");
				$("#horariosDisponiveisDiv").html("");
				return;
			}
			
			$("#horariosDisponiveisDiv").html("");
			horariosFuncionario(this);
		});
	}
	
	function horariosFuncionario(selectFuncionarios) {
		$("#datePickerDiv").html("");
		$("#datePickerDiv").append("Data:<br />");
		$("#datePickerDiv").append("<input name='consulta.dia' type='text' id='datepicker' />");
		$("#datepicker").mask("99/99/9999", {placeholder: " "});
		$("#datepicker").rules("add", {
			required: true,
			messages: {required: "Campo 'Data' é obrigatório"}
		});

		$.datepicker.setDefaults({dateFormat: 'dd/mm/yy',
			dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'],
			dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
			dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
			monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			nextText: 'Próximo',
			prevText: 'Anterior'
		});

		$("#datepicker").datepicker();
		$("#datepicker").change(function() {
			if (this.value == "") {
				$("#horariosDisponiveisDiv").html("");
				return;
			}
			var idFuncionario = $("#funcionarios").val();
			var idProfissao = $("#profissoesCombo").val();
			$("#ajaxLoad").css("display", "block");
			$.getJSON('/funcionarios/horarios/json', {idFuncionario: idFuncionario, idProfissao: idProfissao, dataParaAConsulta: this.value}, horariosDisponiveisDoFuncionario);
		});
	}
	
	var horariosDisponiveisDoFuncionario = function (horarios) {
		$("#ajaxLoad").css("display", "none");
		$("#horariosDisponiveisDiv").html("");
		$("#horariosDisponiveisDiv").append("Horários disponiveis(a):<br />");
		var primeiroHorario = horarios[0].substring(0, 2);
		for (i = 0, modCount = 1; i < horarios.length; i++, modCount++) {
			if (primeiroHorario != horarios[i].substring(0, 2)) {
				$("<br />").appendTo("#horariosDisponiveisDiv");
				primeiroHorario = horarios[i].substring(0, 2);
			}
			$("<input type='radio' name='consulta.hora' class='validaRadios' id='horarios' value='" + horarios[i] + "'>" + horarios[i] + "</input>").appendTo("#horariosDisponiveisDiv");
		}
		
		$.validator.addMethod("validaRadios", function(value, element) {
			return $(".validaRadios:checked").length > 0;
		}, "Escolha um horário.");
		
		$(".validaRadios").rules({
			validaRadios: true
		});

	}
	</script>
</head>

<c:if test="${not empty errors}">
	<c:forEach items="${errors}" var="error">
		 ${error.category} - ${error.message}<br />
	</c:forEach>
</c:if>

<form action="<c:url value="/consultas"/>" method="post" id="formConsultas" name="form">
  
  <c:if test="${not empty consulta.id}">
    <input type="hidden" name="consulta.id" value="${consulta.id}"/>
    <input type="hidden" name="_method" value="put"/>
  </c:if>
  
  <div class="showInfos">
	  <c:if test="${not empty clientesList}">
		  <div class="field">
		  	Cliente: <br />
			<select id="clientesCombo" name="cliente.id">
				<option value="">&nbsp;</option>
				<c:forEach items="${clientesList }" var="cliente">
					<option value="${cliente.id}">${cliente.nome}</option>
				</c:forEach>
			</select><br />
		  </div>
	  </c:if>
	  
	  <div class="field">
	  	Profissão: <br />
		<select id="profissoesCombo" name="profissao.id">
			<option value="">&nbsp;</option>
			<c:forEach items="${profissoesList }" var="profissao">
				<option value="${profissao.id}">${profissao.nome}</option>
			</c:forEach>
		</select><br />
	  </div>
	
		<div class="field" id="funcionarioSelectDiv" >
		</div>
		
		<div class="field" id="datePickerDiv" >
		</div>
		
		<div class="field" id="horariosDisponiveisDiv">
		</div>
		
		<div class="field" id="ajaxLoad" style="display: none;">
		<img src="<c:url value="/images/ajax-loader.gif"/>">
		</div>
		
		<div class="actions">
			<button type="submit">Cadastrar</button>
			<a href="<c:url value="/consultas"/>">Retornar</a>
		</div>
		
	</div>
  
</form>
