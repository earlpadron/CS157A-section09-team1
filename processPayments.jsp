<%@ page import="java.sql.*"%>
<html>
  <head>
  <style>
  .b1{float:right;
  }
  p1{text-align: left;
  	font-size:125%;
  }
  body{
  	margin:0; padding:0;
  }
  .p2Div{text-align: center;
  	margin-top: 10px;
  	margin-bottom: 10px;
  }
  .center{margin:0;
  	position: absolute;
  	top: 50%;
  	left: 50%;
  	-ms-transform: translate(-50%, -50%);
  	transform: translate(-50%, -50%);
  }
  .bcenter{
  	text-align: center;
  }
  table{border: 1px solid black;
  }
  td{border: 1px solid black;
  	height: 25px;
  	width: 100px;
  	text-align: center;
  	vertical-align: bottom;
  }
  
  </style>
    <title>Process Payments</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Process Payments</i>
    </div>
    <a href="login.jsp"><button>Log Out</button></a>
    <br>
    <div class="center">
    <p1>Invoices: </p1>
    <% 
     String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
          user = "root";
        String password = "brian";
        try {
        	String username = (String)request.getSession().getAttribute("username");
            java.sql.Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password); //change cs157a to db name
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.invoices WHERE payment = 'unpaid'"); //change to schema name.table name
            %>
            <form method='post' action ='paymentProcess'>
            <table>
      		<thead>
      			<tr>
      				<td>Invoice ID</td>
        			<td>Total</td>
        			<td>Status</td>
   				</tr>
            </thead>
            <tbody>
            <%while(rs.next())
            {
            	%>
            	<tr>
            		<td><%=rs.getInt("invoiceID")%></td> 
            		<td>$<%=rs.getInt("amount")%></td>
            		<td><%=rs.getString("payment")%></td>
            	</tr>
            	<%}%>
            	</tbody>
            </table><br>
            Invoice: <select name = "invoice">
            <%
            rs = stmt.executeQuery("SELECT * FROM proj1test.invoices WHERE payment = 'unpaid'");
            while(rs.next())
            {
            	%>
            	 <option><%=rs.getInt("invoiceID")%></option>
            <%}%>
            </select>
            <input type = "submit" value = "Process payment">
            <br>
            </form>
            <div class="bcenter"><a href="HotelEmployeeHomepage.jsp"><button>Back</button></a></div>
            <%
        		rs.close();
                stmt.close();
                con.close();
            }
        	
        	catch(SQLException e) { 
                out.println("SQLException caught: " + e.getMessage()); 
        	}%></div>
  </body>
</html>
