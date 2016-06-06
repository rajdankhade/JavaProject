<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<script>
$(document).ready(function(){
	  $('#delete_confirm_box').click(function(){
	    return confirm("Do you want to continue ?");
	});
	});
</script>

<title>Employee Application</title>
</head>
<body>
<h1>Welcome to Employee Application</h1>


<c:url var="editImgUrl" value="/resources/img/edit.png" />
<c:url var="deleteImgUrl" value="/resources/img/delete.png" />
<c:url var="listUrl" value="/krams/main/record/list" />
<c:url var="addUrl" value="/krams/main/record/add" />
<c:url var="searchUrl" value="/krams/main/record/search" />
<p><a href="${listUrl}">Get Employee List with Departments</a>


<table style="border: 1px solid; width: 100%; text-align:center">
	<thead style="background:#d3dce3">
		<tr>
		
			<th>Department Id</th>
			<th>Department Name</th>
			<th>Salary Min</th>
			<th>Salary Max</th>

			<th>Employee Id</th>
			<th>Employee Name</th>
			<th>Manager Name</th>
			<th>Department</th>
			<th>Salary</th>
			<th>Add</th>
			<th>Edit</th>
			<th>Delete</th>
	
		</tr>
	</thead>
	<tbody style="background:#ccc">
	<c:forEach items="${departments}" var="department">
		<c:url var="editUrl" value="/krams/main/record/edit?id=${department.id}" />
		<c:url var="deleteUrl" value="/krams/main/record/delete?id=${department.id}" />
		
		<c:if test="${!empty department.employees}">
			<c:forEach items="${department.employees}" var="employee">
			<tr>
				<td><c:out value="${department.id}" /></td>
				<td><c:out value="${department.name}" /></td>
				<td><c:out value="${department.salaryminrange}" /></td>
				<td><c:out value="${department.salarymaxrange}" /></td>
			
				<td><c:out value="${employee.id}" /></td>
				<td><c:out value="${employee.name}" /></td>
				<td><c:out value="${employee.managerName}" /></td>
				<td><c:out value="${employee.department}" /></td>	
				<td><c:out value="${employee.salary}" /></td>			
				<c:url var="addCcUrl" value="/krams/main/employee/add?id=${employee.id}" />
				<c:url var="editCcUrl" value="/krams/main/employee/edit?pid=${department.id}&cid=${employee.id}" />
				<c:url var="deleteCcUrl" value="/krams/main/employee/delete?id=${employee.id}" />
				<td><a href="${addCcUrl}">+</a></td>
				<td><a href="${editCcUrl}"><img src="${editImgUrl}"></img></a></td>
				<td><a href="${deleteCcUrl}" onclick="return getConfirmation(); " ><img src="${deleteImgUrl}"></img></a></td>
			</tr>
			</c:forEach>
		</c:if>
		
		<c:if test="${empty department.employees}">
			<tr>
				<td><c:out value="${department.id}" /></td>
				<td><c:out value="${department.name}" /></td>
				<td><c:out value="${department.salaryminrange}" /></td>
				<td><c:out value="${department.salarymaxrange}" /></td>
		
				<td>N/A</td>
				<td>N/A</td>
				<td>N/A</td>
				<td>N/A</td>
				<td>N/A</td>
				<c:url var="addCcUrl" value="/krams/main/employee/add?id=${department.id}" />
				<td><a href="${addCcUrl}">+</a></td>
				<td></td>
				<td></td>
			</tr>
		</c:if>
		
	</c:forEach>
	</tbody>
</table>

<c:if test="${empty departments}">
	No records found. 
</c:if>



</body>
</html>