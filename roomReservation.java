

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
 * Servlet implementation class roomReservation
 */
@WebServlet("/roomReservation")
public class roomReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public roomReservation() {
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
        int roomNumber = Integer.parseInt(request.getParameter("reserveRoom"));
        String username = (String)request.getSession().getAttribute("username");
        int accID = (int) request.getSession().getAttribute("accountID");  
        String d = request.getParameter("days");
        int days = Integer.parseInt(d);
        Long dInMS = Long.valueOf(days*24*60*60*1000);
        
        Connection conn = null; 
        String message = null;  // error message
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM proj1test.rooms WHERE roomNumber = '" + roomNumber + "'");
            
            String rType = "";
            String rAv = "";
            int rP = 0;
            while (rs.next())
            {
            	rType = rs.getString("roomType");
            	rAv = rs.getString("availability");
            	rP = rs.getInt("price");
            }
        	
        	String sql = "UPDATE proj1test.rooms SET availability=? WHERE roomNumber = " + roomNumber;
            PreparedStatement pstatement = conn.prepareStatement(sql);
            pstatement.setString(1, "reserved");
            int row = pstatement.executeUpdate();
            if (row > 0)
            {
            	System.out.println("Update successful.");
            }
            
            sql = "INSERT INTO proj1test.reservations (checkIn, checkOut) values (?, ?)";
            Timestamp in = new Timestamp(System.currentTimeMillis());
            Timestamp out = new Timestamp(in.getTime() + dInMS);
            java.sql.Date inDate = new java.sql.Date(in.getTime());
            java.sql.Date outDate = new java.sql.Date(out.getTime());
            pstatement = conn.prepareStatement(sql);
            pstatement.setDate(1, inDate);
            pstatement.setDate(2, outDate);
            
            row = pstatement.executeUpdate();
            
            rs = statement.executeQuery("SELECT max(reservationID) as reservationID FROM proj1test.reservations");
            int rID = 0;
            
            while (rs.next())
            {
            	rID = rs.getInt("reservationID");
            }
            
            sql = "INSERT INTO proj1test.catalogs (reservationID, roomNumber) values (?, ?)";
            pstatement = conn.prepareStatement(sql);
            pstatement.setInt(1, rID);
            pstatement.setInt(2, roomNumber);
            
            row = pstatement.executeUpdate();
            
            sql = "INSERT INTO proj1test.books (registeredID, reservationID) values (?, ?)";
            pstatement = conn.prepareStatement(sql);
            pstatement.setInt(1, accID);
            pstatement.setInt(2, rID);
            
            row = pstatement.executeUpdate();
            
            int total = (15*days) + rP;
            sql = "INSERT INTO proj1test.invoices (amount, payment, date) values (?, ?, ?)";
            pstatement = conn.prepareStatement(sql);
            pstatement.setInt(1, total);
            pstatement.setString(2, "unpaid");
            pstatement.setDate(3, inDate);
            
            row = pstatement.executeUpdate();
            
            int invoice = 0;
            rs = statement.executeQuery("SELECT max(invoiceID) as invoiceID FROM proj1test.invoices");
            
            while (rs.next()) 
            {
            	invoice = rs.getInt("invoiceID");
            }
            
            sql = "INSERT INTO proj1test.transaction (invoiceID, reservationID) values (?, ?)";
            pstatement = conn.prepareStatement(sql);
            pstatement.setInt(1, invoice);
            pstatement.setInt(2, rID);
            
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

