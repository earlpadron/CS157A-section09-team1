

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class roomService
 */
@WebServlet("/roomService")
public class roomService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public roomService() {
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
		String db = "CS157A"; //change to db name
        String user; // assumes database name is the same as username
        user = "root";
        String password = "root";
		
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));     
        int accID = (int) request.getSession().getAttribute("accountID");  
        String req = request.getParameter("req");
        int pr = Integer.parseInt(request.getParameter("pr"));
      
        
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM projecttables.roomservice");
           
            String sql = "INSERT INTO projecttables.roomService (accountID, roomNumber, request,price) values (?,?,?,?)";
            PreparedStatement pstatement = conn.prepareStatement(sql);
          
            pstatement.setInt(1, accID);
            pstatement.setInt(2, roomNumber);
            pstatement.setString(3, req);
            pstatement.setInt(4, pr);
            
           int row = pstatement.executeUpdate();    
           if(row > 0)
           {
        	   message = "Request sent successfully.";
           }
                    
            getServletContext().getRequestDispatcher("/roomServiceRequest.jsp").forward(request, response);
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
