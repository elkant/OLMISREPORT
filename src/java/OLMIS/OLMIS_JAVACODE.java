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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Manuel
 */
public class OLMIS_JAVACODE extends HttpServlet {

    
     XSSFWorkbook wb;
    int maxmerging=0;
    String maincountqry="";
    String startdate="";
    String enddate="";
    String cbo="";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
    
    
    startdate=request.getParameter("startdate");
         enddate=request.getParameter("enddate");
         cbo=request.getParameter("cbos");
    
         dbConn conn= new dbConn("1");
         
         
String createtempcbo="CREATE TABLE #temp_cbo(CBOID INT NOT NULL,CBO VARCHAR(50) NULL,DISTRICTID INT NOT NULL,COUNTYID INT NOT NULL, CBOACTIVE INT NOT NULL, DISTRICTACTIVE INT NOT NULL,COUNTYACTIVE INT NOT NULL)";
         

String insertintotemp="insert into #temp_cbo SELECT     CBO.CBOID, CBO.CBO, District.DistrictID, County.CountyID, "
            +" (select count(ovcid) from clientdetails "
            +" where (([exited] = 0 and dateofregistration <= '"+enddate+"') "
            +" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
            +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
            +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' ) "
                                                                            +")  "
+" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "
+" and (cbo = cbo.cboid) ) as cboActive, "
+" (select count(ovcid) from clientdetails where (([exited] = 0 and dateofregistration <= '"+enddate+"') "
+" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
+" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
+" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' ) "
                                                                            +" )" 
+" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 )"
+" and (district = district.DistrictID) ) as DistrictActive, "
+" (select count(ovcid) from clientdetails where (([exited] = 0 and dateofregistration <= '"+enddate+"') " 
+" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
+" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
+" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' )"
                                                                            +")" 
+" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "
+" and (county.Countyid = (select countyid from District where DistrictID = clientdetails.District)) ) " 
+" as CountyActive FROM CBO INNER JOIN District ON CBO.DistrictID = District.DistrictID INNER JOIN County ON District.Countyid = County.CountyID";



String insertintotemp2="insert into #temp_cbo SELECT     CBO.CBOID, CBO.CBO, District.DistrictID, County.CountyID, (select count(ovcid) from clientdetails "
    +" where (([exited] = 0 and dateofregistration <= '"+enddate+"') "
    +" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) " 
    +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) " 
    +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' ))" 
    +" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "
    +"  and (cbo = cbo.cboid) ) as cboActive, "
    +" (select count(ovcid) from clientdetails where (([exited] = 0 and dateofregistration <= "+enddate+") "
    +" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
    +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
    +" or ([exited] = 1 and DateofRegistration <= "+enddate+"  and DateofExit between '"+startdate+"' and '"+enddate+"' ) ) "
    +" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "
    +" and (district = district.DistrictID) ) as DistrictActive,"
    +" (select count(ovcid) from clientdetails "
    +" where (([exited] = 0 and dateofregistration <= '"+enddate+"') "
    +" or ([exited] = 1 and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
    +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
    +" or ([exited] = 1 and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' )"
    +" ) " 
    +" and not (clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "
    +" and (county.Countyid = (select countyid from District where DistrictID = clientdetails.District)) ) "
    +" as CountyActive"
    +" FROM CBO INNER JOIN District ON CBO.DistrictID = District.DistrictID INNER JOIN"
    +" County ON District.Countyid = County.CountyID where CBO.cboid in (' "+cbo+" ')";





String pepfardata=" INSERT INTO #PepfarSummary_tbl SELECT   OVCID, COUNT(DISTINCT Domain) AS uniquedomaincount, datediff(year,DateofBirth,'"+enddate+"') as Age, Gender, CBO, "
						+" District, CBOID, DistrictID, SchoolLevel, CountyID, County,DateofVisit FROM"						
						+" (SELECT   vw_IX_PepfarAssessment.OVCID, vw_IX_PepfarAssessment.Domain, Clientdetails.DateofBirth, Clientdetails.Gender, CBO.CBO, "
						+" District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel, County.CountyID, County.County, "
						+" vw_IX_PepfarAssessment.DateofVisit"
						+" FROM      District INNER JOIN"
										+" CBO ON District.DistrictID = CBO.DistrictID INNER JOIN"
										+" County ON District.Countyid = County.CountyID INNER JOIN"
										+" vw_IX_PepfarAssessment with (noexpand) INNER JOIN"
										+" Clientdetails ON vw_IX_PepfarAssessment.OVCID = Clientdetails.OVCID ON CBO.CBOID = Clientdetails.Cbo "
						  +" WHERE vw_IX_PepfarAssessment.Domain IN ('Health and Nutrition', 'Psychosocial Support') "
						  +" AND vw_IX_PepfarAssessment.DateofVisit between  '"+startdate+"' and '"+enddate+"' "
						  +" and cbo.cboid in (select cboid from #temp_cbo) "
						+"  and (([exited] = 'false' and dateofregistration <= '"+enddate+"') "
												+" or ([exited] = 'true' and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
												+" or ([exited] = 'true' and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
												+" or ([exited] = 'true' and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' )"
											+" ) "
									 +" and not (Clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) "

						+" UNION ALL"

						+" SELECT   vw_IX_PepfarService.OVCID, vw_IX_PepfarService.Domain, Clientdetails.DateofBirth, Clientdetails.Gender, CBO.CBO, "
										+" District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel, County.CountyID, County.County, " 
										+" vw_IX_PepfarService.DateofVisit FROM      District INNER JOIN "
										+" CBO ON District.DistrictID = CBO.DistrictID INNER JOIN "
										+" County ON District.Countyid = County.CountyID INNER JOIN "
										+" vw_IX_PepfarService with (noexpand) INNER JOIN "
						+" Clientdetails ON vw_IX_PepfarService.OVCID = Clientdetails.OVCID ON CBO.CBOID = Clientdetails.Cbo "
						  +" WHERE vw_IX_PepfarService.[DateofVisit] between  '"+startdate+"' and '"+enddate+"'"
						  +" and cbo.cboid in (select cboid from #temp_cbo) "
		+" and (([exited] = 'false' and dateofregistration <= '"+enddate+"') "
		+" or ([exited] = 'true' and  (dateofregistration between '"+startdate+"' and '"+enddate+"' )) "
		+" or ([exited] = 'true' and DateofRegistration <= '"+enddate+"'  and DateofExit > '"+enddate+"' ) "
		+" or ([exited] = 'true' and DateofRegistration <= '"+enddate+"'  and DateofExit between '"+startdate+"' and '"+enddate+"' ) "
								+" )" 
+" and not (Clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, '"+enddate+"') > 17 ) ) as tblservices "
+" GROUP BY OVCID,  datediff(year,DateofBirth,'"+enddate+"') , Gender, CBO, District, CBOID, DistrictID, SchoolLevel, CountyID, County, DateofVisit";



conn.state.executeUpdate(createtempcbo);





if(!cbo.trim().equals("")||!cbo.trim().equals("all")){
    System.out.print("__CODE1"+insertintotemp);
conn.state.executeUpdate(insertintotemp);
 
}
else
{
    System.out.print("===CODE 2"+insertintotemp2);
    
conn.state.executeUpdate(insertintotemp2);

}

//____________create PepfarSummary_tbl table
conn.state.executeQuery(pepfardata);

conn.rs=conn.state.executeQuery("select * from #PepfarSummary_tbl");

while(conn.rs.next()){

System.out.println("______________"+conn.rs.getString(1)+"__"+conn.rs.getString(2));

}

conn.state.executeUpdate(" drop table #temp_cbo");
conn.state.executeUpdate(" drop table #PepfarSummary_tbl");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
       
        
        
        
        
    } finally {            
        out.close();
    }
}       catch (SQLException ex) {
            Logger.getLogger(OLMIS_JAVACODE.class.getName()).log(Level.SEVERE, null, ex);
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
