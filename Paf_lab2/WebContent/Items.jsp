<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import ="pkg.Item" %>


<%
if (request.getParameter("action") != null) {
	
if (request.getParameter("itemCode") != null)
{
 Item itemObj = new Item();
 String stsMsg = itemObj.insertItem(request.getParameter("itemCode"),
 request.getParameter("itemName"),
 request.getParameter("itemPrice"),
 request.getParameter("itemDesc"));
 session.setAttribute("statusMsg", stsMsg);
}
}
%>
<% 
if (request.getParameter("action") != null) {
 if(request.getParameter("action").toString().equalsIgnoreCase("update")) {
	Item itemObj1 = new Item();
	String stsMsg = itemObj1.updateItems(Integer.parseInt(request.getParameter("itemID").toString()),
	request.getParameter("itemCode"),
	request.getParameter("itemName"),
	request.getParameter("itemPrice"),
	request.getParameter("itemDesc"));
	session.setAttribute("statusMsg", stsMsg);

}
}
%>
<% 
if (request.getParameter("action") != null) {
if (request.getParameter("action").toString().equalsIgnoreCase("remove")) {
	Item itemObj = new Item();
	String stsMsg = itemObj.delete_Item(Integer.parseInt(request.getParameter("itemID").toString()));
	session.setAttribute("statusMsg", stsMsg);
}
}
%>




 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
	<h1>Items Management</h1>

	<%
		if (request.getParameter("action") != null) {
		if (request.getParameter("action").toString().equalsIgnoreCase("select")) {
			Item itemObj = new Item();
			out.print(itemObj.read_Item_detail(Integer.parseInt(request.getParameter("itemID"))));
		} else {
			out.print("<form method='post' action='Items.jsp'> " + "<input name='action' value='insert' type='hidden'> "+
			 "Item code: <input name='itemCode' type='text' class='form-control'><br>"+
			 "Item name: <input name='itemName' type='text' class='form-control'><br> "+
			 "Item price: <input name='itemPrice' type='text' class='form-control'><br> "+
			 "Item description: <input name='itemDesc' type='text' class='form-control'><br> "+
			 "<input name='btnSubmit' type='submit' value='Save'  class='btn btn-primary'> " + "</form>");
		}
	} else {
		out.print("<form method='post' action='Items.jsp'> " +
		 "<input name='action' value='insert' type='hidden'> "+
		 "Item code: <input name='itemCode' type='text' class='form-control'><br>"+
		 "Item name: <input name='itemName' type='text' class='form-control'><br> "+
		 "Item price: <input name='itemPrice' type='text' class='form-control'><br> "+
		 "Item description: <input name='itemDesc' type='text' class='form-control'><br> "+
		 "<input name='btnSubmit' type='submit' value='Save' class='btn btn-primary' > " + "</form>");
	}
	%>
	

	<%
			if (session.getAttribute("statusMsg") != null) {
	%>
	<div class="alert alert-success">
	<% 
			out.print(session.getAttribute("statusMsg"));
	%>
	</div>
	<%
			session.setAttribute("statusMsg", null);
		}
		%>


	
	<%
 		Item itemObj = new Item(); %>
	<div class="container">
	 <div class="row">
	 <div class="col">
<% 
 		out.print(itemObj.readItems());
	%>
	</div>
 </div>
</div>

</body>
</html>