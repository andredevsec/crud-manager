<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">

		<h3 class="page-header">${not empty serviceorder ? 'Atualizar' : 'Adicionar'} Ordem de serviço</h3>

		<form action="${pageContext.request.contextPath}/serviceorder/${action}"
			method="POST">
			
			<input type="hidden" value="${serviceorder.getId()}" name="serviceorderId">
				
			<div class="row">
				<div class="form-group col-md-6">
					<label for="description">Descrição</label> 
					<input type="text"
						class="form-control" id="description" name="description" autofocus="autofocus"
						placeholder="Descrição da ordem" required
						oninvalid="this.setCustomValidity('Por favor, informe a descrição da ordem.')"
						oninput="setCustomValidity('')"
						value="${serviceorder.getDescription()}">
				</div>

				<div class="form-group col-md-6">
					<label for="valueos">Valor</label> <input type="number"
						class="form-control" id="valueos" name="valueos" autofocus="autofocus"
						placeholder="Valor da ordem de serviço" required
						oninvalid="this.setCustomValidity('Por favor, informe o valor.')"
						oninput="setCustomValidity('')"
						value="${serviceorder.getValueos()}">
				</div>

			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="start">Data inicio</label> <input type="date"
						class="form-control" id="start" name="start" autofocus="autofocus"
						placeholder="Data de inicio" required
						oninvalid="this.setCustomValidity('Por favor, informe a data de inicio.')"
						oninput="setCustomValidity('')"
						value="${serviceorder.getStart()}">
				</div>

				<div class="form-group col-md-4">
					<label for="end">Data de termino</label> <input type="date"
						class="form-control" id="end" name="end" autofocus="autofocus"
						placeholder="Data de termino"
						oninvalid="this.setCustomValidity('Por favor, informe a data de termino.')"
						oninput="setCustomValidity('')"
						value="${serviceorder.getEnd()}">
				</div>
				
				<div class="form-group col-md-4">
					<label for="company">Empresa prestadora</label>
					 <select id="company"
						class="form-control selectpicker" name="company" required
						oninvalid="this.setCustomValidity('Por favor, informe a empresa prestadora.')"
						oninput="setCustomValidity('')">
						<option value="" ${not empty serviceorder ? "" : 'selected'}>Selecione uma empresa
						</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}" ${serviceorder.getCompany().getId() == company.getId() ? 'selected' : ''}>
								${company.getName()}
							</option>
						</c:forEach>
					</select>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/serviceorders" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">
						${not empty serviceorder ? 'Atualizar' : 'Cadastrar'} Ordem de serviço</button>
					</div>
				</div>

			</div>

		</form>
	</div>

</body>
</html>