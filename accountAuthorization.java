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
 * Servlet implementation class accountAuthorization
 */
@WebServlet("/accountAuthorization")
public class accountAuthorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountAuthorization() {
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
        int accID = (int) request.getSession().getAttribute("accountID");
          
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);  
            Statement statement = conn.createStatement();
            if (username != null)
            {
            	if (username == "")
            	{
            		getServletContext().getRequestDispatcher("/authorizeAccounts.jsp").forward(request, response);
            		conn.close();
            	}
            
            	else
            	{
            		ResultSet rs = statement.executeQuery("SELECT * FROM proj1test.accounts WHERE username = '"+ username + "'");
            		int userAccount = 0;
            		while (rs.next()) 
            		{
            			userAccount = rs.getInt("AccountID");
            		}
            		if (userAccount == 0)
            		{
            			getServletContext().getRequestDispatcher("/authorizeAccounts.jsp").forward(request, response);
            			conn.close();
            		}
            		else
            		{
            			String sql = "UPDATE proj1test.accounts SET PermissionType=? WHERE AccountID = " + userAccount;
            			PreparedStatement pstatement = conn.prepareStatement(sql);
            			pstatement.setString(1, "authorized");
            			int row = pstatement.executeUpdate();
            		
            			sql = "INSERT INTO proj1test.authorized (AccountID) values (?)";
            			pstatement = conn.prepareStatement(sql);
            			pstatement.setInt(1, userAccount);
            			row = pstatement.executeUpdate();
            			
            			sql = "INSERT INTO proj1test.manages (authorizedAccountID, registeredAccountID) values (?, ?)";
            			pstatement = conn.prepareStatement(sql);
            			pstatement.setInt(1, accID);
            			pstatement.setInt(2, userAccount);
            			row = pstatement.executeUpdate();
            
            			getServletContext().getRequestDispatcher("/HotelEmployeeHomepage.jsp").forward(request, response);
            			conn.close();
            		}
            	}
            }
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
            // set message 
            request.setAttribute("Message", message);
             
            // go to message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
}
