/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SCRIPTS;

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
public class wardsAlignment extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         
         dbConn aphiacon= new dbConn("APHIAMAINDB_VERSION2_2016");
        try {
          
            String getovcmisplacedlocation=" select OVCID,LOCATION  from clientdetails where location not in (Select locationID from Location) and Exited=0";
           
            aphiacon.rs=aphiacon.state.executeQuery(getovcmisplacedlocation);
            int count=0;
            while(aphiacon.rs.next()){
            count++;
                //System.out.println(count+"__OVC_NAME "+aphiacon.rs.getString("OVC_NAME"));
          
            //selet the respective location id from Clientdetails_wardsbackup
                
                String getdetails2="select Location from Clientdetails_wardsbackup where OVCID='"+aphiacon.rs.getString("OVCID")+"' ";
            
                aphiacon.rs1=aphiacon.st_1.executeQuery(getdetails2);
                while(aphiacon.rs1.next()){
                    
                String updatelocation=" update Clientdetails set Location='"+aphiacon.rs1.getString(1)+"' where OVCID='"+aphiacon.rs.getString("OVCID")+"' ";
                    System.out.println(count+"___"+updatelocation);                
aphiacon.st_2.executeUpdate(updatelocation);
                
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
            Logger.getLogger(wardsAlignment.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(wardsAlignment.class.getName()).log(Level.SEVERE, null, ex);
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
