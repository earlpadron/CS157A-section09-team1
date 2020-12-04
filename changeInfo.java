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
 * Servlet implementation class changeInfo
 */
@WebServlet("/changeInfo")
public class changeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeInfo() {
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
        int guestID = (int) request.getSession().getAttribute("guestID");
        String pw = request.getParameter("newpw");
        String name = request.getParameter("newname");
        String email = request.getParameter("newemail");
        String address = request.getParameter("newaddress");
        String phone = request.getParameter("newphone");
        
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);  
            Statement statement = conn.createStatement();
            
            if (pw != null)
            {	
            	if (pw == "")
            	{
            		conn.close();
            		getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
            	}
            	else 
            	{
            		String sql = "UPDATE proj1test.accounts SET password =? WHERE AccountID = " + accID;
            		PreparedStatement pstatement = conn.prepareStatement(sql);
            		pstatement.setString(1, pw);
            		int row = pstatement.executeUpdate();
            	}
            }
            
            if (name != null)
            {	
            	if (name == "")
            	{
            		conn.close();
            		getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
            	}
            	else 
            	{
            		String sql = "UPDATE proj1test.guests SET name =? WHERE guestID = " + guestID;
            		PreparedStatement pstatement = conn.prepareStatement(sql);
            		pstatement.setString(1, name);
            		int row = pstatement.executeUpdate();
            	}
            }
            
            if (email != null)
            {	
            	if (email == "")
            	{
            		conn.close();
            		getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
            	}
            	else 
            	{
            		String sql = "UPDATE proj1test.guests SET email =? WHERE guestID = " + guestID;
            		PreparedStatement pstatement = conn.prepareStatement(sql);
            		pstatement.setString(1, email);
            		int row = pstatement.executeUpdate();
            	}
            }
            
            if (address != null)
            {	
            	if (address == "")
            	{
            		conn.close();
            		getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
            	}
            	else 
            	{
            		String sql = "UPDATE proj1test.guests SET address =? WHERE guestID = " + guestID;
            		PreparedStatement pstatement = conn.prepareStatement(sql);
            		pstatement.setString(1, address);
            		int row = pstatement.executeUpdate();
            
            	}
            }
            
            if (phone != null)
            {
            	if (phone == "")
            	{
            		conn.close();
            		getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
            	}
            	else 
            	{
            		String sql = "UPDATE proj1test.guests SET phone =? WHERE guestID = " + guestID;
            		PreparedStatement pstatement = conn.prepareStatement(sql);
            		pstatement.setString(1, phone);
            		int row = pstatement.executeUpdate();
            	}
            }
            
            getServletContext().getRequestDispatcher("/viewAccount.jsp").forward(request, response);
			conn.close();
            
            
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
