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
    <title>Account Information</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Account Information</i>
    </div>
    <br><a href="login.jsp"><button>Log Out</button></a> 
    <div class="center">
    <br><br><br>
    <p1>Reservation Info: </p1>
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
            String accID = Integer.toString((int)request.getSession().getAttribute("accountID"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.register WHERE accountID = '" + accID + "'");
            int guestID = 0;
            while(rs.next())
            {
            	guestID = rs.getInt("guestID");
            }
			request.getSession().setAttribute("guestID", guestID);
            rs = stmt.executeQuery("SELECT * FROM proj1test.accounts WHERE username = '" + username + "'");
            String upw = "";
            while(rs.next())
            {
            	upw = rs.getString("password");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.guests WHERE guestID = '" + guestID + "'");
            String name = "";
            String phone = "";
            String address = "";
            String email = "";
            while(rs.next())
            {
            	name = rs.getString("name");
            	phone = rs.getString("phone");
            	address = rs.getString("Address");
            	email = rs.getString("email");
            }
            
            %>
            <table>
      		<thead>
      			<tr>
      				<td>Username</td>
        			<td>Password</td>
        			<td>Name</td> 
        			<td>Email</td>
        			<td>Address</td>
        			<td>Phone</td>
   				</tr>
            </thead>
            <tbody>
            
            	<tr>
            		<td><%=username%></td> 
            		<td><%=upw%></td>
            		<td><%=name%></td>
            		<td><%=email%></td>
            		<td><%=address%></td>
            		<td><%=phone%></td>
            	</tr>
            	
            	</tbody>
            </table><br>
            
            <div class = "bcenter"><form method='post' action ='changeInfo' >
            New password: 
            <input type= 'text' name ="newpw"> 
           	<input type = 'submit' value = 'Change password' name = 'changepw'> <br> <br>
           	</form>
           	
           	<form method='post' action ='changeInfo' >
            New name:
            <input type= 'text' name ="newname">
           	<input type = 'submit' value = 'Change name' name = 'changeame'> <br> <br>
           	</form>
           	
           	<form method='post' action ='changeInfo' >
            New email:
            <input type= 'text' name ="newemail">
           	<input type = 'submit' value = 'Change email' name = 'changeemail'> <br> <br>
           	</form>
           	
           	<form method='post' action ='changeInfo' >
            New address:
            <input type= 'text' name ="newaddress">
           	<input type = 'submit' value = 'Change address' name = 'changeaddress'> <br> <br>
           	</form>
           	
           	<form method='post' action ='changeInfo' >
            New phone number:
            <input type= 'text' name ="newphone"> 
           	<input type = 'submit' value = 'Change phone number' name = 'changephone'> <br> <br>
           	</form>
           	
            <a href="HotelHomepage.jsp"><button>Back</button></a></div> 
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
