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
    <title>Reservation</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Reservation</i>
    </div>
    <br><a href="login.jsp"><button>Log Out</button></a> 
    <div class="center">
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
            int accID = (int) request.getSession().getAttribute("accountID");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.books WHERE registeredID = '" + accID + "'");
            int rID = 0;
            while(rs.next())
            {
            	rID = rs.getInt("reservationID");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.catalogs WHERE reservationID = '" + rID + "'");
            int room = 0;
            while(rs.next())
            {
            	room = rs.getInt("roomNumber");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.reservations WHERE reservationID = '" + rID + "'");
            Timestamp din = new Timestamp(System.currentTimeMillis());
            Timestamp dout = new Timestamp(System.currentTimeMillis());
            java.sql.Date inDate = new java.sql.Date(din.getTime());
            java.sql.Date outDate = new java.sql.Date(dout.getTime());
            while(rs.next())
            {
            	inDate = rs.getDate("CheckIn");
            	outDate = rs.getDate("CheckOut");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.transaction WHERE reservationID = '" + rID + "'");
            int invID = 0;
            while(rs.next())
            {
            	invID = rs.getInt("invoiceID");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.invoices WHERE invoiceID = '" + invID + "'");
            int total = 0;
            while(rs.next())
            {
            	total = rs.getInt("amount");
            }
            
            %>
            <table>
      		<thead>
      			<tr>
      				<td>Username</td>
        			<td>Reservation ID</td>
        			<td>Room Number</td> 
        			<td>Total</td>
        			<td>Check In</td>
        			<td>Check Out</td>
   				</tr>
            </thead>
            <tbody>
            
            	<tr>
            		<td><%=username%></td> 
            		<td><%=rID%></td>
            		<td><%=room%></td>
            		<td>$<%=total%></td>
            		<td><%=inDate%></td>
            		<td><%=outDate%></td>
            	</tr>
            	
            	</tbody>
            </table><br>
            <div class="bcenter"><a href="HotelHomepage.jsp"><button>Back</button></a></div> 
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
