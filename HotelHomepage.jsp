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
    <title>Guest Homepage</title>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    </head>
  <body>
  <div class="p2Div">
    <i class='far fa-building' style='font-size:36px'>Guest Homepage</i>
    </div>
    <br>
    <a href="login.jsp"><button>Log Out</button></a>
    <div class="center">
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
            %>
            <br>
            <div class="bcenter"><a href="viewAccount.jsp"><button>Account Information</button></a></div>
            <br>
            <div class="bcenter"><a href="viewReservation.jsp"><button>View Reservation</button></a></div>
            <br>
            <div class="bcenter"><a href="roomServiceList.jsp"><button>Request Room Service</button></a></div>
            <br>
            <div class="bcenter"><a href="reservation.jsp"><button>Make a Reservation</button></a></div>
            
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
