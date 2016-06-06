<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee Application</title>
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

<h1>Edit Existing Employee</h1>
<c:if test="${not empty error}">
   <font color="red"> Error: ${error} </font>
</c:if>
<c:url var="saveUrl" value="/krams/main/employee/edit?id=${employeeAttribute.id}" />
<form:form modelAttribute="employeeAttribute" method="POST" action="${saveUrl}">
	<table>
	
		<tr>
			<td>Employee Id:</td>
			<td><input type="text" value="${departmentId}" disabled="true"/>
		</tr>
		
	<tr>
			<td><form:label path="name">Name:</form:label></td>
			<td><form:input path="name"/></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="managerName">Manager Name:</form:label></td>
			<td><form:input path="managerName"/></td>
			<td><form:errors path="managerName" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td><form:label path="department">Department:</form:label></td>
			<td><form:input path="department"/></td>
			<td><form:errors path="department" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td><form:label path="salary">Salary:</form:label></td>
			<td><form:input path="salary"/></td>
			<td><form:errors path="salary" cssClass="error" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Save" />
</form:form>

</body>
</html>