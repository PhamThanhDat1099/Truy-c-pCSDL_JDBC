/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://PC304;databaseName=demodb", "sa", "sa");
                
                ps = conn.prepareStatement("SELECT * FROM users");
                rs = ps.executeQuery();

                out.println("<h1>List of Users</h1>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>");
                
                while (rs.next()) {
                    out.println("<tr><td>" + rs.getInt("id") + "</td><td>" 
                            + rs.getString("name") + "</td><td>" 
                            + rs.getString("password") + "</td><td>" 
                            + rs.getString("email") + "</td><td>" 
                            + rs.getString("country") + "</td><td><a href='EditServlet?id=" + rs.getInt("id") + "'>edit</a></td><td><a href='DeleteServlet?id=" + rs.getInt("id") + "'>delete</a></td></tr>");
                }
                out.println("</table>");
                
            } catch (Exception e) {
                out.println("<h2>Error retrieving users: " + e.getMessage() + "</h2>");
            } finally {
                if (conn != null) try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ViewServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}