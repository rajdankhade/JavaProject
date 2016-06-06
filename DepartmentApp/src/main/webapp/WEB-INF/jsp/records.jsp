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

<title>Department Application</title>
</head>
<body>
<h1>Welcome to Department Application</h1>


<c:url var="editImgUrl" value="/resources/img/edit.png" />
<c:url var="deleteImgUrl" value="/resources/img/delete.png" />
<c:url var="listUrl" value="/krams/main/record/list" />
<c:url var="addUrl" value="/krams/main/record/add" />
<c:url var="searchUrl" value="/krams/main/record/search" />
<p><a href="${listUrl}">Get Department List</a> | 
<a href="${addUrl}">Create new Department</a> 

<table style="border: 1px solid; width: 100%; text-align:center">
	<thead style="background:#d3dce3">
		<tr>
		
			<th>Department Id</th>
			<th>Department Name</th>
			<th>Salary Min</th>
			<th>Salary Max</th>
			<th>Edit</th>
			<th>Delete</th>
			<th colspan="2"></th>
		
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
				<td><a href="${editUrl}"><img src="${editImgUrl}"></img></a></td>
				<td><a href="${deleteUrl}" id="delete_confirm_box"><img src="${deleteImgUrl}"></img></a></td>
				
		</tr>
			</c:forEach>
		</c:if>
		
		<c:if test="${empty department.employees}">
			<tr>
				<td><c:out value="${department.id}" /></td>
				<td><c:out value="${department.name}" /></td>
				<td><c:out value="${department.salaryminrange}" /></td>
				<td><c:out value="${department.salarymaxrange}" /></td>
				<td><a href="${editUrl}"><img src="${editImgUrl}"></img></a></td>
				<td><a href="${deleteUrl}"><img src="${deleteImgUrl}"></img></a></td>
				
	
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