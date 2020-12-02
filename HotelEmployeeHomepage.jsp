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
    <title>Hotel Management System</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Hotel Management System</i>
    </div>
    <br>
    <a href="login.jsp"><button>Log Out</button></a>
    <div class="center">
    <p1>List of Rooms: </p1>
    <% 
     String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
          user = "root";
        String password = "brian";
        try {
        	String username = request.getParameter("username");
        	if (username == null)
        	{
        		username = (String)request.getSession().getAttribute("username");
        	}
        	request.getSession().setAttribute("username", username);
            java.sql.Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password); //change cs157a to db name
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.accounts WHERE username = '" + username + "'");
            int accID = 0;
            while (rs.next())
            {
            	accID = rs.getInt("AccountID");
            }
            request.getSession().setAttribute("accountID", accID);
            rs = stmt.executeQuery("SELECT * FROM proj1test.rooms"); //change to schema name.table name
            %>
            <table>
      		<thead>
      			<tr>
        			<td>Room Number</td>
        			<td>Room Type</td>
        			<td>Room Availability</td>
        			<td>Room Price</td>
   				</tr>
            </thead>
            <tbody>
            <%while(rs.next())
            {
            	%>
            	<tr>
            		<td><%=rs.getInt("roomNumber") %></td> 
            		<td><%=rs.getString("roomType") %></td>
            		<td><%=rs.getString("availability") %></td>
            		<td><%=rs.getInt("price") %></td>
            	</tr>
            	<%}%>
            	</tbody>
            </table><br>
            <div class="bcenter"><a href="manageRooms.jsp"><button>Manage Rooms</button></a></div>
            <br>
            <div class="bcenter"><a href="processPayments.jsp"><button>Process Payments</button></a></div>
            <br>
            <div class="bcenter"><a href="reservation.jsp"><button>Manage Accounts</button></a></div>
            
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
