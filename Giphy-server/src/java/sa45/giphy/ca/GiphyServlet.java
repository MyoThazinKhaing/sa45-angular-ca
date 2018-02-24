/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa45.giphy.ca;

/**
 *
 * @author ASUS
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import javax.json.JsonArrayBuilder;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;

@WebServlet(urlPatterns = "/retrieve/*")
public class GiphyServlet extends HttpServlet {

	@Resource(lookup = "jdbc/giphydb")
	private DataSource connPool;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//No error checks
		String pathInfo = req.getPathInfo();
		System.out.println(">>> path info: " + pathInfo);

		String userId = pathInfo.substring(1);
                JsonArrayBuilder gifphyBuilder = Json.createArrayBuilder();
                JsonObject cust=null;

		try (Connection conn = connPool.getConnection()) {

			PreparedStatement ps = conn.prepareStatement("select * from giphy where userid = ?");
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			  if (!rs.isBeforeFirst()) 
                          {
		             resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		             return;
            }
                        while(rs.next()){
                           JsonObjectBuilder obj = Json.createObjectBuilder();
                                  obj.add("userid", rs.getString("userid"))
                        .add("giphypath", rs.getString("giphypath"));
                        gifphyBuilder.add(obj.build());
				
                             
                               
                        }

			

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(MediaType.APPLICATION_JSON);
			try (PrintWriter pw = resp.getWriter()) {
                             JsonArray arr = gifphyBuilder.build();
				pw.println(arr.toString());
			}


		} catch (SQLException ex) {
			log(ex.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}

	
	
}
