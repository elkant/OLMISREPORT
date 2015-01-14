/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OLMIS;

import database.dbConn;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Manuel
 */
public class PEPFARREPORT extends HttpServlet {

    XSSFWorkbook wb;
    int maxmerging=0;
    String maincountqry="";
    String startdate="";
    String enddate="";
    String cbo="";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            
             startdate=request.getParameter("startdate");
             enddate=request.getParameter("enddate");
             cbo=request.getParameter("cbos");
            
            wb = new XSSFWorkbook();
             String columnheaders[]={"OVCID","SERVICES","AGE","GENDER","CBO","DISTRICT","CBO ID","DISTRICTID","SCHOOL LEVEL"};
                
           maxmerging=columnheaders.length;
           
           maincountqry="SELECT     Clientdetails.OVCID, COUNT(DISTINCT Domain.Domain) AS uniquedomaincount, DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) AS Age, Clientdetails.Gender," 
                      +" CBO.CBO, District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel"
+" FROM         CBO INNER JOIN"
                      +" District ON CBO.DistrictID = District.DistrictID INNER JOIN StatusAndServiceVisit INNER JOIN"
                      +" StatusAndServiceMonitoring_Assessment ON StatusAndServiceVisit.SSVID = StatusAndServiceMonitoring_Assessment.SSVID INNER JOIN"
                      +" ServiceStatus ON StatusAndServiceMonitoring_Assessment.SSID = ServiceStatus.SSID INNER JOIN"
                      +" CoreServices ON ServiceStatus.CSID = CoreServices.CSID INNER JOIN"
                      +" Domain ON CoreServices.Domainid = Domain.DomainID INNER JOIN"
                      +" Clientdetails ON StatusAndServiceVisit.OVCID = Clientdetails.OVCID ON CBO.CBOID = Clientdetails.Cbo"
            +" WHERE Clientdetails.cbo in (select cboid from cbo)"
             +" AND not (Clientdetails.schoollevel = 5 and "
            +" DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) > 17 ) and  domain.Domain IN ('Food and Nutrition','Health','Psychosocial') "
          +" and CONVERT(date, StatusAndServiceVisit.DateofVisit, 103)"  
          +" between '"+startdate+"' and '"+enddate+"' group by Clientdetails.OVCID, Clientdetails.DateofBirth,   Clientdetails.Gender,cbo.cbo,district.district, " 
         +" cbo.cboid, district.districtid,clientdetails.schoollevel "
         
         +" union"
         
        +" SELECT     Clientdetails.OVCID, COUNT(DISTINCT Domain.Domain) AS uniquedomaincount, DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) AS Age, Clientdetails.Gender, "
        +" CBO.CBO, District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel"
        +"  FROM         CBO INNER JOIN District ON CBO.DistrictID = District.DistrictID INNER JOIN"
        +" StatusAndServiceVisit INNER JOIN StatusAndServiceMonitoring_Service ON StatusAndServiceVisit.SSVID = StatusAndServiceMonitoring_Service.SSVID INNER JOIN"
        +" ServiceStatus ON StatusAndServiceMonitoring_Service.SSID = ServiceStatus.SSID INNER JOIN "
        +" CoreServices ON ServiceStatus.CSID = CoreServices.CSID INNER JOIN Domain ON CoreServices.Domainid = Domain.DomainID INNER JOIN"
        +" Clientdetails ON StatusAndServiceVisit.OVCID = Clientdetails.OVCID ON CBO.CBOID = Clientdetails.Cbo"
        +" WHERE Clientdetails.cbo in (select cboid from cbo)"
        +" AND not (Clientdetails.schoollevel = 5 and DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) > 17 ) "
        +" and  CONVERT(date, StatusAndServiceVisit.DateofVisit, 103) between '"+startdate+"' and '"+enddate+"' "
        +" group by Clientdetails.OVCID, Clientdetails.DateofBirth,   Clientdetails.Gender,cbo.cbo,district.district,"  
        +" cbo.cboid, district.districtid,clientdetails.schoollevel   ";
           
        


            //=====================================



            //==============================CREATE WORKBOOK AND SHEETS FOR EACH SITE  



            
            XSSFSheet rawdata = wb.createSheet("RAW DATA");


            //%%%%%%%%%%%%%%%%HEADER FONTS AND COLORATION%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            XSSFFont font_header = wb.createFont();
            font_header.setFontHeightInPoints((short) 9);
            font_header.setFontName("Arial Black");



            //    font.setItalic(true);
            font_header.setBoldweight((short) 03);
            font_header.setColor(HSSFColor.BLACK.index);

            //font data
            XSSFFont datafont = wb.createFont();
            datafont.setBoldweight((short) 03);
          
            datafont.setFontHeightInPoints((short) 10);
            datafont.setFontName("Cambria");


            //==============HEADER STYLE==================

            CellStyle style_header = wb.createCellStyle();
            style_header.setFont(font_header);
            style_header.setWrapText(true);
            style_header.setAlignment(style_header.ALIGN_CENTER_SELECTION);
            style_header.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


            //========TABLE HEADER STYLING===========================
            CellStyle th_style = wb.createCellStyle();
            th_style.setFont(datafont);
            th_style.setWrapText(true);
            th_style.setAlignment(th_style.ALIGN_CENTER);
            th_style.setFillForegroundColor(HSSFColor.GOLD.index);
            th_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            th_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            th_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            th_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            th_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            //=======DATA STYLING=========================== 

            CellStyle data_style = wb.createCellStyle();
            data_style.setFont(datafont);
            data_style.setWrapText(true);
            data_style.setAlignment(data_style.ALIGN_CENTER);
            data_style.setFillForegroundColor(HSSFColor.WHITE.index);
            data_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            data_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            data_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            data_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            data_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);




            //=======INNER DATA STYLING=========================== 

            CellStyle innerdata_style = wb.createCellStyle();
            innerdata_style.setFont(datafont);
            innerdata_style.setWrapText(true);
            innerdata_style.setAlignment(data_style.ALIGN_CENTER);
            innerdata_style.setFillForegroundColor(HSSFColor.WHITE.index);
            innerdata_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            innerdata_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);



          






            //===============ALL SITES HEADER===============================

            XSSFRow rw0 = rawdata.createRow(0);
            rw0.setHeightInPoints(35);
            XSSFRow rw1 = rawdata.createRow(1);
            rw1.setHeightInPoints(35);
            XSSFCell rw0cell0 = rw1.createCell(1);
            rw0cell0.setCellValue("OLMIS PEPFAR REPORT FROM DATE "+startdate+" TO "+enddate+" FOR "+cbo+" CBO(s)");
            rw0cell0.setCellStyle(style_header);
            
            
            rawdata.addMergedRegion(new CellRangeAddress(1, 1, 1, maxmerging));
            


        //===========================MAIN SITE COLUMN HEADERS========================================
           XSSFRow allsitescolumnheader = rawdata.createRow(2);
                allsitescolumnheader.setHeightInPoints(30);
                XSSFCell rwcolheader=null;
                
               
                for(int d=0;d<maxmerging;d++){
                 rwcolheader = allsitescolumnheader.createCell(d+1);
                  rwcolheader.setCellValue(columnheaders[d]);
                  rwcolheader.setCellStyle(th_style);
                }
                for (int a = 1; a <=maxmerging; a++) {
                    if(a==1||a==5||a==6){
                     rawdata.setColumnWidth(a, 8000);
                    }
                    else{
                        rawdata.setColumnWidth(a, 3000);
                    }
                
                }


               






            dbConn conn = new dbConn("1");


            //===============DISTINCT FACILITY NAMES===========


           

            XSSFRow rw2 = null;

            conn.rs=conn.state.executeQuery(maincountqry);

            int rowno=2;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            //THE FAMOUS WHILE LOOP STARTS HERE
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            
 
            
            
            //========LOAD THE FOLLOWING CELLS INTO THE FORM CELLS
            
            //Clientdetails.OVCID, COUNT(DISTINCT Domain.Domain) AS uniquedomaincount, 
            //DATEDIFF(year, Clientdetails.DateofBirth, GETDATE()) AS Age, Clientdetails.Gender, 
            //CBO.CBO, District.District, CBO.CBOID, District.DistrictID, Clientdetails.SchoolLevel
            
            while(conn.rs.next()){
            rowno++;
            
            rw2=rawdata.createRow(rowno);
            rw2.setHeightInPoints(25);
            
            //OVCID
            XSSFCell cell1 = rw2.createCell(1);
            cell1.setCellValue(conn.rs.getString("OVCID"));
            cell1.setCellStyle(innerdata_style);
            
            //DOMAINCOUNT
            XSSFCell cell2 = rw2.createCell(2);
            cell2.setCellValue(conn.rs.getString("uniquedomaincount"));
            cell2.setCellStyle(innerdata_style);
            //AGE
             XSSFCell cell3 = rw2.createCell(3);
            cell3.setCellValue(conn.rs.getString("Age"));
            cell3.setCellStyle(innerdata_style);
            
            //GENDER
             XSSFCell cell4 = rw2.createCell(4);
            cell4.setCellValue(conn.rs.getString("Gender"));
            cell4.setCellStyle(innerdata_style);
            
            //CBO
             XSSFCell cell5 = rw2.createCell(5);
            cell5.setCellValue(conn.rs.getString("CBO"));
            cell5.setCellStyle(innerdata_style);
            
            //DISTRICT
            XSSFCell cell6 = rw2.createCell(6);
            cell6.setCellValue(conn.rs.getString("District"));
            cell6.setCellStyle(innerdata_style);
            
            //CBOID
            XSSFCell cell7 = rw2.createCell(7);
            cell7.setCellValue(conn.rs.getString("CBOID"));
            cell7.setCellStyle(innerdata_style);
            
            //DistrictID
            XSSFCell cell8 = rw2.createCell(8);
            cell8.setCellValue(conn.rs.getString("DistrictID"));
            cell8.setCellStyle(innerdata_style);
            //SCHOOL LEVEL
            
            XSSFCell cell9 = rw2.createCell(9);
            cell9.setCellValue(conn.rs.getString("SchoolLevel"));
            cell9.setCellStyle(innerdata_style);
            
            System.out.println(rowno+"==========="+conn.rs.getString("OVCID"));
            
            }
            

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            //THE FAMOUS WHILE LOOP ENDS HERE
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%            



            //================================================================================================================
      






        

        Date dat = new Date();

        String dat1 = dat.toString().replace(" ", "_");

        // write it as an excel attachment
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte[] outArray = outByteStream.toByteArray();
        response.setContentType("application/ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0"); // eliminates browser caching
        response.setHeader("Content-Disposition", "attachment; filename=PEPFAR_REPORT" + dat1 + ".xlsx");
        response.setHeader("Set-Cookie","fileDownload=true; path=/");
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();
        } catch (SQLException ex) {
            Logger.getLogger(PEPFARREPORT.class.getName()).log(Level.SEVERE, null, ex);
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
