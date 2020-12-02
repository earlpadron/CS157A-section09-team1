

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class roomManagement
 */
@WebServlet("/roomManagement")
public class roomManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public roomManagement() {
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
        String roomNumber = request.getParameter("reserveRoom");
        String username = (String)request.getSession().getAttribute("username");
        int accID = (int) request.getSession().getAttribute("accountID");  
        
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            
        	String sql = "UPDATE proj1test.rooms SET availability=? WHERE roomNumber = " + roomNumber;
            PreparedStatement pstatement = conn.prepareStatement(sql);
            pstatement.setString(1, "available");
            int row = pstatement.executeUpdate();
            if (row > 0)
            {
            	System.out.println("Update successful.");
            }
           
            getServletContext().getRequestDispatcher("/HotelEmployeeHomepage.jsp").forward(request, response);
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
            // set message 
            request.setAttribute("Message", message);
             
            // go to message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
}

