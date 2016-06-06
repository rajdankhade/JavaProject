<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Department Application</title>
<style>
.error {
	color: #ff0000;
}
.errorblock{
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding:8px;
	margin:16px;
}

</style>
</head>
<body>

<h1>Edit Existing Department Details</h1>

<c:url var="saveUrl" value="/krams/main/record/edit?id=${departmentAttribute.id}" />
<form:form modelAttribute="departmentAttribute" method="POST" action="${saveUrl}">
	<table>
		<tr>
			<td><form:label path="id">Id:</form:label></td>
			<td><form:input path="id" disabled="true"/></td>
		</tr>
	
		<tr>
			<td><form:label path="name">Name:</form:label></td>
			<td><form:input path="name"/></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="salaryminrange">Salary Min Range</form:label></td>
			<td><form:input path="salaryminrange"/></td>
			<td><form:errors path="salaryminrange" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td><form:label path="salarymaxrange">Salary Min Range</form:label></td>
			<td><form:input path="salarymaxrange"/></td>
			<td><form:errors path="salarymaxrange" cssClass="error" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Save" />
</form:form>

</body>
</html>