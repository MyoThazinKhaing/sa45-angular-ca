/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa45.giphy.ca;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ASUS
 */
@WebServlet(urlPatterns = "/save/*")
public class SaveGiphyServlet extends HttpServlet{
    @Resource(lookup = "jdbc/giphydb")
	private DataSource connPool;
    private static String query = "INSERT INTO giphy (userid, giphypath) VALUES (?, ?);";
   

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//No error checks
                 try (Connection conn = connPool.getConnection())
        {
            String userid = req.getParameter("userid");
            String giphypath = req.getParameter("giphypath");
           
                    
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, userid);
            ps.setString(2, giphypath);
           

            ps.executeUpdate();
                       
            conn.close();
        } 
        catch (SQLException ex) 
        {
            log(ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } 
       
        // set status code
        resp.setStatus(HttpServletResponse.SC_OK);
        
        // set media type
        resp.setContentType(MediaType.TEXT_HTML);
        
                
		

        }

    
}
