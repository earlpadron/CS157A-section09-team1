import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 * Servlet implementation class hotelLogin
 */
@WebServlet("/hotelLogin")
public class hotelLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hotelLogin() {
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
		
        // get username and password input from login.jsp
        String username = request.getParameter("username");
        String upassword = request.getParameter("password");
          
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM proj1test.accounts");
            
            while (rs.next()) 
            {
            	if (username.equals(rs.getString("username")))
            	{
            		if (upassword.equals(rs.getString("password")))
            		{
            			String accType = rs.getString("PermissionType");
            			if (accType.equals("guest"))
            			{
            				getServletContext().getRequestDispatcher("/HotelHomepage.jsp").forward(request, response);
                			conn.close();
            			}
            			
            			if (accType.equals("authorized"))
            			{
            				getServletContext().getRequestDispatcher("/HotelEmployeeHomepage.jsp").forward(request, response);
                			conn.close();
            			}	
            		}
            	}
            }       
            
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                message = "Incorrect username or password.";
            	try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // set message 
            request.setAttribute("Message", message);
             
            // go to message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
}
