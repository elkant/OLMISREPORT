/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OLMIS;

import database.dbConn;
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
 * @author Manuel
 */
public class Loadclusters extends HttpServlet {

   String allcbos="";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
           
            
            
            
    
    allcbos="<option value=\"\">select cluster</option>";
   
    
    response.setContentType("text/html;charset=UTF-8");
    
    String getcbos="select * from clusters order by ClusterName";
        
        dbConn conn= new dbConn("1");
          
            
              if(!request.getParameter("database").equals("2014")){
                 //we are assuming that every database will have the year suffix
                 //e.g APHIAMAINDB_VERSION2_2014
                 String selyear=request.getParameter("database");
                 String yearsuffix=conn.dbsetup[1].substring(conn.dbsetup[1].length()-4, conn.dbsetup[1].length());
                 System.out.println("year suffix is "+yearsuffix);
                 conn.dbsetup[1]=conn.dbsetup[1].replace(yearsuffix, selyear);
                 
                 conn = new dbConn(conn.dbsetup[1]); 
          }
            
            System.out.println("Database after is "+conn.dbsetup[1]);
        
        conn.rs=conn.state.executeQuery(getcbos);
        
        while(conn.rs.next()){
        
        allcbos+="<option value=\""+conn.rs.getString(3) +"\">"+conn.rs.getString(2) +"</option>";
        
        
                             }
    
    
    PrintWriter out = response.getWriter();
    try {
       
        
       out.println(allcbos); 
        
        
    }
    
   
    
    finally {            
        out.close();
            }
}
        catch (SQLException ex) {
            Logger.getLogger(loadcbos.class.getName()).log(Level.SEVERE, null, ex);
        
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
