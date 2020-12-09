import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class hotelRegister
 */
@WebServlet("/hotelRegister")
public class hotelRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hotelRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
        user = "root";
        String password = "brian";
		
        // get username and password input from register.jsp
        String username = request.getParameter("username");
        String upassword = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String message = null; 
        if (username != null && upassword != null && name != null && address != null && phone != null && email != null) {
        if (username == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        if (username == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        if (upassword == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        if (name == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        if (phone == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        if (email == "")
        {
        	message = "Please fill in all fields.";
        	request.setAttribute("Message", message);
        	getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
        else
        {
        Connection conn = null; 
      
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            String sql = "INSERT INTO proj1test.accounts (username, password, PermissionType) values (?, ?, ?)";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, upassword);
            statement.setString(3, "guest");
 
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "Registered successfully.";
            }
            
            sql = "INSERT INTO proj1test.guests (name, phone, Address, email) values (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, email);
            
            row = statement.executeUpdate();
            
            sql = "SELECT AccountID FROM proj1test.accounts WHERE username = '" + username + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int accID = 0;
            while (rs.next())
            {
            	accID = rs.getInt("AccountID");
            }
            
            sql = "SELECT guestID FROM proj1test.guests WHERE email = '" + email + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            int guestID = 0;
            while (rs.next())
            {
            	guestID = rs.getInt("guestID");
            }
            
            sql = "INSERT INTO proj1test.register (accountID, guestID) values (?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, Integer.toString(accID));
            statement.setString(2, Integer.toString(guestID));
            
            row = statement.executeUpdate();
            
            sql = "INSERT INTO proj1test.registered (accountID, PaymentInfo) values (?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, accID);
            statement.setString(2, "credit card");
            
            row = statement.executeUpdate();
            
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			conn.close();
            
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // set  
            request.setAttribute("Message", message);
             
            // go to message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
	
}
}
}
