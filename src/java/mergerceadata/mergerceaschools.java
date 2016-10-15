/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergerceadata;

import database.dbConn;
import database.dbConn1;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emmanuel E
 */
public class mergerceaschools extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         dbConn othercons= new dbConn("RCEA_COMP4");
         dbConn aphiacon= new dbConn("APHIAMAINDB");
        try {
          
            String getovcidandschoolname=" select OVCID, SchoolName from Clientdetails left join Schools on Clientdetails.School=Schools.SchoolID " +
" where SchoolName is not null";
           
            othercons.rs=othercons.state.executeQuery(getovcidandschoolname);
            int count=0;
            while(othercons.rs.next()){
            count++;
                System.out.println(count+"__OVC "+othercons.rs.getString(1)+"_ SCHOOL "+othercons.rs.getString(2));
           //look for the id of that school in APHIAMAIN DB and update it
                String aphiaschoolid="Select SchoolID from Schools where SchoolName ='"+othercons.rs.getString(2)+"'";
            aphiacon.rs0=aphiacon.state1.executeQuery(aphiaschoolid);
            
            if(aphiacon.rs0.next()){
            // school id found
                //update the school id where the ovc id is as shown in the list above
                String updateaphiaschoolid="update Clientdetails set School='"+aphiacon.rs0.getString(1)+"' where OVCID='"+othercons.rs.getString(1)+"'";
                String updateaphiaschoolidlondgitud="update ClientLongitudinalDetails set School='"+aphiacon.rs0.getString(1)+"' where OVCID='"+othercons.rs.getString(1)+"' and School !='0'";
                System.out.println(updateaphiaschoolid);
                System.out.println(updateaphiaschoolidlondgitud);
                
                aphiacon.st_1.executeUpdate(updateaphiaschoolid);
                aphiacon.st_1.executeUpdate(updateaphiaschoolidlondgitud);
            }
            }
            out.println("</html>");
        } finally {
            out.close();
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(mergerceaschools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(mergerceaschools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
