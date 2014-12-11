/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CODE;

import database.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class DataSummary extends HttpServlet {

   
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
    try {
    
    
    
   
response.setContentType("text/html;charset=UTF-8");
PrintWriter out = response.getWriter();

String months[]={"jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"};
//String months[]={"aug","sep","oct","nov","dec"};

String monthday[]={"31-jan","28-feb","31-mar","30-apr","31-may","30-jun","31-jul","31-aug","30-sep","31-oct","30-nov","31-dec"};
//String monthday[]={"31-aug","30-sep","31-oct","30-nov","31-dec"};

String leapfeb="29-feb";

String year[]={"2013"};

//an arraylist to store the first part of the array
ArrayList yearmonthdayAL=new ArrayList();

//an arraylist to store the last part of the array.
ArrayList yearmonthAL=new ArrayList();

for (int a=0;a<year.length;a++){

for (int b=0;b<monthday.length;b++){
    
yearmonthAL.add(months[b]+"-"+year[a]);
//if the year is a leap one and the month is feb, then feb should have 29 days

if((isLeapYear(Integer.parseInt(year[a]))==true)&&months[b].equals("feb")){
yearmonthdayAL.add(leapfeb+"-"+year[a]);
}
else{

yearmonthdayAL.add(monthday[b]+"-"+year[a]);

}
}// end monthday forloop

}//end of year loop

//System.out.println("Is leap year? "+isLeapYear(2016));

dbConn conn= new dbConn("1");


for(int c=0;c<yearmonthAL.size();c++){

System.out.println( "01-"+yearmonthAL.get(c) +"==>"+yearmonthdayAL.get(c));






String getData="SELECT  Clientdetails.OVCID, COUNT(DISTINCT Domain.Domain) AS uniquedomaincount, DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) AS Age, Clientdetails.Gender," 
              +"CBO.CBO, District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel,MAX (StatusAndServiceVisit.DateofVisit), MAX (right('0' + DATEPART(MONTH, StatusAndServiceVisit.DateofVisit),2)) as Month,MAX(DATEPART(YEAR,StatusAndServiceVisit.DateofVisit)) AS Year "
+" FROM  CBO INNER JOIN "
              +" District ON CBO.DistrictID = District.DistrictID INNER JOIN "
              +" APHIAMAINDB.dbo.StatusAndServiceVisit INNER JOIN "
              +" APHIAMAINDB.dbo.StatusAndServiceMonitoring ON StatusAndServiceVisit.SSVID = StatusAndServiceMonitoring.SSVID INNER JOIN"
              +" APHIAMAINDB.dbo.ServiceStatus ON StatusAndServiceMonitoring.SSID = ServiceStatus.SSID INNER JOIN"
              +" APHIAMAINDB.dbo.CoreServices ON ServiceStatus.CSID = CoreServices.CSID INNER JOIN"
              +" APHIAMAINDB.dbo.Domain ON CoreServices.Domainid = Domain.DomainID INNER JOIN"
              +" APHIAMAINDB.dbo.Clientdetails ON StatusAndServiceVisit.OVCID = Clientdetails.OVCID ON CBO.CBOID = Clientdetails.Cbo"
    +" WHERE Clientdetails.cbo in (select cboid from APHIAMAINDB.dbo.cbo)"
     +" AND not (Clientdetails.schoollevel = 5 and" 
    +" DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) > 17 )" 
     +" and  (coreservices.type = 'Service'" 
  +" or  (domain.Domain IN ('Food and Nutrition','Health','Psychosocial') and  coreservices.type = 'Monitor'" 
  +" or coreservices.type = 'Service' ) )"
  +" and CONVERT(date, StatusAndServiceVisit.DateofVisit, 103)"  
  +" between '01-"+yearmonthAL.get(c)+"' and '"+yearmonthdayAL.get(c)+"'"
  +" group by Clientdetails.OVCID, Clientdetails.DateofBirth,   Clientdetails.Gender,cbo.cbo,district.district, " 
  +" cbo.cboid, district.districtid,clientdetails.schoollevel ";

conn.rs=conn.state.executeQuery(getData);

while(conn.rs.next()){

//System.out.println(conn.rs.getString(1)+"__"+conn.rs.getString(2)+"__"+conn.rs.getString(3)+"__"+conn.rs.getString(4)+"__");

String inser="insert into PepfarSummary (OVCID,uniquedomaincount,Age,Gender,CBO,District,CBOID,DistrictID,SchoolLevel,date,Month,Year,YearMonth) values"
        + "('"+conn.rs.getString(1) +"','"+conn.rs.getString(2) +"','"+conn.rs.getString(3) +"','"+conn.rs.getString(4) +"','"+conn.rs.getString(5) +"','"+conn.rs.getString(6) +"','"+conn.rs.getString(7) +"','"+conn.rs.getString(8) +"','"+conn.rs.getString(9) +"','"+conn.rs.getString(10) +"','"+conn.rs.getString(11) +"','"+conn.rs.getString(12) +"','"+conn.rs.getString(12).trim()+ conn.rs.getString(11).trim()+"')";

System.out.println(inser);

conn.state1.executeUpdate(inser);



} //end of while loop..




    }//end of for loop

try {
   
    
    
} finally {            
    out.close();
}
  
}       catch (SQLException ex) {
            Logger.getLogger(DataSummary.class.getName()).log(Level.SEVERE, null, ex);
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

    
    public static boolean isLeapYear(int year) {
  if (year % 4 != 0) {
    return false;
  } else if (year % 400 == 0) {
    return true;
  } else if (year % 100 == 0) {
    return false;
  } else {
    return true;
  }
}

}
