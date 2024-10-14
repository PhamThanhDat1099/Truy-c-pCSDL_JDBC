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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://PC304;databaseName=demodb", "sa", "sa");
                ps = conn.prepareStatement("SELECT * FROM users WHERE id=?");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h1>Edit User</h1>");
                    out.println("<form action='UpdateServlet' method='POST'>");
                    out.println("<input type='hidden' name='id' value='" + id + "' />");
                    out.println("Name: <input type='text' name='uname' value='" + rs.getString("name") + "' /><br>");
                    out.println("Password: <input type='password' name='upass' value='" + rs.getString("password") + "' /><br>");
                    out.println("Email: <input type='email' name='uemail' value='" + rs.getString("email") + "' /><br>");
                    out.println("Country: <input type='text' name='ucountry' value='" + rs.getString("country") + "' /><br>");
                    out.println("<input type='submit' value='Update' />");
                    out.println("</form>");
                } else {
                    out.println("<h2>User not found</h2>");
                }

            } catch (Exception e) {
                out.println("<h2>Error: " + e.getMessage() + "</h2>");
            }
        }
    }
}