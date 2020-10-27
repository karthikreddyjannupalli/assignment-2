package fetch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FetchServelet
 */
@WebServlet("/FetchServelet")
public class FetchServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = new DBConnection().connect();
		PrintWriter out = response.getWriter();
		System.out.println(conn);
		PreparedStatement stmt;
		ResultSet rs = null;
		String s1="";
		try {
			stmt = conn.prepareStatement("select u.RollNo RollNum , u.Name UserName ,u.DateOfBirth , u.Gender ,u.profilepic,u.proctId,p.name proctorName , p.Phone,p.Branch ,a.attendance,m.GPA from users u , proctor p,attendance a, marks m where u.proctId = p.proctid and u.RollNo = a.RollNo and u.RollNo = m.RollNo and u.RollNo=?");
			String rollno = request.getParameter("rollno"); 
			stmt.setString(1, rollno);
			rs= stmt.executeQuery();
			
			if(rs.next()) {
				s1+="<table style='border: double;'>"+"<tr>Personal Information</tr>";
				s1+="<tr><td>UserName</td>"+
					"<td>"+rs.getString("UserName")+"</td></tr>"+
					"<tr><td>DateOfBirth</td><td>"+rs.getDate("DateOfBirth")+"</td></tr>"+
					"<tr><td>Gender</td><td>"+rs.getString("Gender")+"</td></tr>"+
					"<tr><td>profilePic</td><td>"+rs.getBlob("profilepic")+"</td></tr></table>";
				s1+="<table style='border: double;'><tr>Proctor Information</tr>";
				s1+="<tr><td>ProctorName</td><td>"+rs.getString("proctorName")+"</td></tr>"+
					"<tr><td>Phone Number</td><td>"+rs.getInt("Phone")+"</td></tr>"+
					"<tr><td>Branch</td><td>"+rs.getString("Branch")+"</td></tr></table>";
				s1+="<table style='border: double;'><tr>Attendance Information</tr>"+
					"<tr><td>attendance</td><td>"+rs.getFloat("attendance")+"</td></tr></table>";
				s1+="<table style='border: double;'><tr>Academic Information</tr>"+
					"<tr><td>GPA</td><td>"+rs.getFloat("GPA")+"</td></tr></table>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String html="<div class='row'>\r\n" + 
				"      	<div class='col'>\r\n" + s1+ 
		"      	</div>\r\n" + 
		"      </div>";
		out.println(html);
	}

}
