/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CODE;

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
public class ADDYEARMONTH extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
    response.setContentType("text/html;charset=UTF-8");
    
    
    dbConn conn= new dbConn("1");
    
    
    String qry= "Select * from PepfarSummary";
    
    conn.rs=conn.state.executeQuery(qry);
    
    
    while(conn.rs.next()){
    
        String upd="update PepfarSummary set YearMonth='"+conn.rs.getString("Year")+conn.rs.getString("Month") +"' where uniqid='"+conn.rs.getString("uniqid")+"'";
        
        System.out.println(upd);
        
        //conn.state1.executeUpdate(upd);
    
    
    }
    
    
    PrintWriter out = response.getWriter();
    try {
        /* TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ADDYEARMONTH</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet ADDYEARMONTH at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
         */
    } finally {            
        out.close();
    }
}       catch (SQLException ex) {
            Logger.getLogger(ADDYEARMONTH.class.getName()).log(Level.SEVERE, null, ex);
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
