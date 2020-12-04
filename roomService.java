

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
		String db = "cs157a"; //change to db name
        String user; // assumes database name is the same as username
        user = "root";
        String password = "brian";
		
        // get username and password input from login.jsp
        String rsReq = request.getParameter("request");
        String username = (String)request.getSession().getAttribute("username");
        int accID = (int) request.getSession().getAttribute("accountID");  
        int price = 0;
        if (rsReq.equals("Breakfast"))
        {
        	price = 10;
        }
        if (rsReq.equals("Brunch"))
        {
        	price = 12;
        }
        if (rsReq.equals("Lunch"))
        {
        	price = 20;
        }
        if (rsReq.equals("Dinner"))
        {
        	price = 35;
        }
        if (rsReq.equals("Snack"))
        {
        	price = 7;
        }
        
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM proj1test.books WHERE registeredID = '" + accID + "'");
            int resID = 0;
            while(rs.next())
            {
            	resID = rs.getInt("reservationID");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.transaction WHERE reservationID = '" + resID + "'");
            int invID = 0;
            while(rs.next())
            {
            	invID = rs.getInt("invoiceID");
            }
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.invoices WHERE invoiceID = '" + invID + "'");
            int current = 0;
            while(rs.next())
            {
            	current = rs.getInt("amount");
            }
            int total = price + current;
        	String sql = "UPDATE proj1test.invoices SET amount=? WHERE invoiceID = " + invID;
            PreparedStatement pstatement = conn.prepareStatement(sql);
            pstatement.setInt(1, total);
            int row = pstatement.executeUpdate();
            if (row > 0)
            {
            	System.out.println("Update successful.");
            }
            sql = "UPDATE proj1test.invoices SET payment=? WHERE invoiceID = " + invID;
            pstatement = conn.prepareStatement(sql);
            pstatement.setString(1, "unpaid");
            row = pstatement.executeUpdate();
            
            rs = stmt.executeQuery("SELECT * FROM proj1test.catalogs WHERE reservationID = '" + resID + "'");
            int room = 0;
            while(rs.next())
            {
            	room = rs.getInt("roomNumber");
            }            
            
            sql = "INSERT INTO proj1test.roomservice (accountID, roomNumber, request, price) VALUES(?, ?, ?, ?)";
            pstatement = conn.prepareStatement(sql);
            pstatement.setString(1, Integer.toString(accID));
            pstatement.setInt(2, room);
            pstatement.setString(3, rsReq);
            pstatement.setInt(4, price);
            row = pstatement.executeUpdate();
            
            getServletContext().getRequestDispatcher("/HotelHomepage.jsp").forward(request, response);
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

