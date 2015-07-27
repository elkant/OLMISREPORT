/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OLMIS;

import SCRIPTS.copytemplates;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import database.dbConn;
import database.dbConn1;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Manuel
 */
public class BenefitiaryList extends HttpServlet {

    
    int maxmerging = 0;
    String maincountqry = "";
    String startdate = "";
    String enddate = "";
    String cbo = "";
    String pathtodelete=null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        XSSFWorkbook wb1=null;
        
        try {
           

  
 String allpath = getServletContext().getRealPath("/OVCbeneficiarylist.xlsm");
 String spath = getServletContext().getRealPath("/OVCbeneficiarylist.xlsm");

    String mydrive = allpath.substring(0, 1);
        //dbconnpath=mydrive+":\\MNHC_SYSTEM_APHIA_PLUS\\"; 
      allpath=mydrive+":\\OLMIS\\OLMIS\\MACROS\\OVCbeneficiarylist.xlsm"; 
    String  np=mydrive+":\\OLMIS\\OLMIS\\MACROS\\OVCbeneficiarylist.xlsm"; 
    
    
   
  //define the date and calender  
Date dat = new Date();
String dat1 = dat.toString().replace(" ", "_");
 dat1 = dat1.toString().replace(":", "_");

   String perminpath=mydrive+":\\OLMIS\\OLMIS\\MACROS\\OVCbeneficiarylist"+dat1+".xlsm";  
 
copytemplates ct= new copytemplates();
File f = new File(np);
if(!f.exists()&& !f.isFile() ) { /* do something */

    ct.copymacros(spath,perminpath);
    allpath=perminpath;
    //ct.transfermacros(spath);
    System.out.println("Copying macros first time..");
    
        }
else {
    
//now copy a unique template for each person accessing the application
ct.copymacros(spath,perminpath);
    
//ct.transfermacros(spath);
allpath=perminpath;


}
    
 long heapsize=Runtime.getRuntime().totalMemory();
    System.out.println("heapsize is::"+heapsize);


 System.out.println(allpath);
 pathtodelete=allpath;
   
     System.out.println("0 open of excel finished");
     File allpathfile= new File(allpath);
     
      OPCPackage pkg = OPCPackage.open(allpathfile);
  System.out.println("1 open of excel finished");
    wb1 = new XSSFWorkbook(pkg);
    SXSSFWorkbook wb = new SXSSFWorkbook(wb1, 100);
System.out.println("2 open of excel finished");
 

//OVCID","OVCName","Gender","age","CareTaker","parentname","Service","CHWName","CBONAME","DISTRICTNAME","Exited	DateofExit","HHVulnerabilityStatus","Quantity","Received_Yes_NO	DateReceived","ParentSignature"
String columnheaders[]={"OVCID","OVCName","Gender","age","CareTaker","parentname","Service","CHWName","CBONAME","DISTRICTNAME","Exited	DateofExit","HHVulnerabilityStatus","Quantity","Received_Yes_NO	DateReceived","ParentSignature"};
    
//"Clientdetails.OVCID","Clientdetails.FirstName","Clientdetails.MiddleName","Clientdetails.Surname","Clientdetails.Gender","DateofBirth","age","BirthCert","HIVStatus.HIVStatus","CHWFirstName","CHWMiddleName","CHWSurname","CHWNationalID","ParentNationalIDNumber","ParentDetailsFirstName","ParentDetailsMiddleName","ParentDetailsSurname","SchoolLevel.SchoolLevel","Schools.SchoolName","DateofRegistration","ExitStatus","ReasonforExit","DateofExit","Immunization.ImmunizationStatus","Clientdetails.HHVulnerabilityStatus","CBO.CBO","District.District","Location.Location","County.County"           
  System.out.println("3 open of excel finished");  
   Sheet rawdata = wb.getSheet("Sheet1");


            //%%%%%%%%%%%%%%%%HEADER FONTS AND COLORATION%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
           Font font_header = wb.createFont();
            font_header.setFontHeightInPoints((short) 9);
            font_header.setFontName("Arial Black");



            //    font.setItalic(true);
            font_header.setBoldweight((short) 03);
            font_header.setColor(HSSFColor.BLACK.index);

            //font data
            Font datafont = wb.createFont();
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
        
            innerdata_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            innerdata_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            innerdata_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);



          


 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
            startdate = request.getParameter("startdate");
            enddate = request.getParameter("enddate");
            cbo = request.getParameter("cbos");

            
        

            dbConn conn = new dbConn("1");
            System.out.println("Database before is "+conn.dbsetup[1]);
            
            
              if(!request.getParameter("database").equals("2014")){
                 //we are assuming that every database will have the year suffix
                 //APHIAMAINDB_VERSION2_2014
                 String selyear=request.getParameter("database");
                 String yearsuffix=conn.dbsetup[1].substring(conn.dbsetup[1].length()-4, conn.dbsetup[1].length());
                 System.out.println("year suffix is "+yearsuffix);
                 conn.dbsetup[1]=conn.dbsetup[1].replace(yearsuffix, selyear);
                 
                 conn = new dbConn(conn.dbsetup[1]); 
          }
            
              
              System.out.println("Database after is "+conn.dbsetup[1]);
            
              
           // dbConn1 conn1 = new dbConn1();
            
            //======the second connection
            
            
            
            conn.ps1 = conn.connect.prepareCall("{call	rpt_beneficiarylist(?,?,?)}");

            conn.ps1.setEscapeProcessing(true);
////conn.ps1.setQueryTimeout(30000);
            conn.ps1.setString(1, startdate);
            conn.ps1.setString(2, enddate);
            if (cbo.equals("all")) {
                conn.ps1.setString(3, null);
            } else {
                conn.ps1.setString(3, cbo);

            }
            boolean results = conn.ps1.execute();

            
           
            
              Row allsitescolumnheader = rawdata.createRow(0);
                allsitescolumnheader.setHeightInPoints(30);
                Cell rwcolheader=null;
                
              maxmerging=columnheaders.length;
                
                for(int d=0;d<maxmerging;d++){
                 rwcolheader = allsitescolumnheader.createCell(d);
                  rwcolheader.setCellValue(columnheaders[d]);
                  //rwcolheader.setCellStyle(th_style);
                  
                  
                }
            
            
              Row rw2 = null;

           

            int rowno=0;
            
            while (results) {
                conn.rs = conn.ps1.getResultSet();
                System.out.println("###Inside Loop");
                while (conn.rs.next()) {
                    // read the data
                    System.out.println(rowno+"==================" + conn.rs.getString(1) + "_" + conn.rs.getString(2) + " _ " + conn.rs.getString(3) + " _ " + conn.rs.getString(4) + " _ " + conn.rs.getString(5) + " _ " + conn.rs.getString(6));

                    
//Clientdetails.OVCID,Clientdetails.FirstName,Clientdetails.MiddleName,Clientdetails.Surname,Clientdetails.Gender,DateofBirth,age,BirthCert,HIVStatus.HIVStatus,CHWFirstName,CHWMiddleName,CHWSurname,CHWNationalID,ParentNationalIDNumber,ParentDetailsFirstName,ParentDetailsMiddleName,ParentDetailsSurname,SchoolLevel.SchoolLevel,Schools.SchoolName,DateofRegistration,ExitStatus,ReasonforExit,DateofExit,Immunization.ImmunizationStatus,Clientdetails.HHVulnerabilityStatus,CBO.CBO,District.District,Location.Location,County.County
                    
                    
                    rowno++;            
            rw2=rawdata.createRow(rowno);
            rw2.setHeightInPoints(25);
            
            //OVCcount
            if(1==1){
            Cell cell1 = rw2.createCell(0);
             String val1="";
            if(conn.rs.getString(1)!=null){val1=conn.rs.getString(1);}
            cell1.setCellValue(val1);
            //cell13.setCellStyle(innerdata_style);
            }
           
            //cell1.setCellStyle(innerdata_style);
            
            //AGE
            if(1==1){
           Cell cell2 = rw2.createCell(1);
        
           // cell2.setCellStyle(innerdata_style);
             String val1="";
            if(conn.rs.getString(2)!=null){val1=conn.rs.getString(2);}
            cell2.setCellValue(val1);
            }
            
            
            //Number of services
            if(1==1){
            Cell cell3 = rw2.createCell(2);
           
             String val1="";
            if(conn.rs.getString(3)!=null){val1=conn.rs.getString(3);}
            cell3.setCellValue(val1);
           // cell3.setCellStyle(innerdata_style);
            }
            //Age bracket
            
            
            Cell cell4 = rw2.createCell(3);
            cell4.setCellValue(conn.rs.getInt(4));
           // cell4.setCellStyle(innerdata_style);
            
            //Gender
            if(1==1){
            Cell cell5 = rw2.createCell(4);
             String val1="";
            if(conn.rs.getString(5)!=null){val1=conn.rs.getString(5);}
            cell5.setCellValue(val1);
            //cell5.setCellValue(conn.rs.getString(5));
            //cell5.setCellStyle(innerdata_style);
                 }
            //cbo
            
            if(1==1){
            Cell cell6 = rw2.createCell(5);
             String val1="";
            if(conn.rs.getString(6)!=null){val1=conn.rs.getString(6);}
            cell6.setCellValue(val1);
          
            }
           // cell6.setCellStyle(innerdata_style);
            
            //District
            if(1==1){
           Cell cell7 = rw2.createCell(6);
            String val1="";
            if(conn.rs.getString(7)!=null){val1=conn.rs.getString(7);}
            cell7.setCellValue(val1);
           //cell7.setCellValue(conn.rs.getString(7));
                    }
          //  cell7.setCellStyle(innerdata_style);
            
            //cboid
            if(1==1){
            Cell cell8 = rw2.createCell(7);
             String val1="";
            if(conn.rs.getString(8)!=null){val1=conn.rs.getString(8);}
            cell8.setCellValue(val1);
           // cell8.setCellValue(conn.rs.getString(8));
            }
          //  cell8.setCellStyle(innerdata_style);
            //districtid
            if(1==1){
            Cell cell9 = rw2.createCell(8);
            String val1="";
            if(conn.rs.getString(9)!=null){val1=conn.rs.getString(9);}
            cell9.setCellValue(val1);
           // cell9.setCellValue(conn.rs.getString(9));
                    }
           // cell9.setCellStyle(innerdata_style);
            //county id
            if(1==1){
          Cell cell10 = rw2.createCell(9);
           String val1="";
            if(conn.rs.getString(9)!=null){val1=conn.rs.getString(9);}
            cell10.setCellValue(val1);
            cell10.setCellValue(conn.rs.getString(9));
            }
           // cell10.setCellStyle(innerdata_style);         
            
            //county
            if(1==1){
           Cell cell11 = rw2.createCell(10);
            String val1="";
            if(conn.rs.getString(10)!=null){val1=conn.rs.getString(10);}
            cell11.setCellValue(val1);
            //cell11.setCellValue(conn.rs.getString(11));
           // cell11.setCellStyle(innerdata_style);
            }
            
            //Cbo active
            if(1==1){
            Cell cell12 = rw2.createCell(11);
             String val1="";
            if(conn.rs.getString(12)!=null){val1=conn.rs.getString(12);}
            cell12.setCellValue(val1);
            //cell12.setCellValue(conn.rs.getString(12));
            //cell12.setCellStyle(innerdata_style);
            }
            
            
            //district active
            if(1==1){
            Cell cell13 = rw2.createCell(12);
             String val1="";
            if(conn.rs.getString(13)!=null){val1=conn.rs.getString(13);}
            cell13.setCellValue(val1);
           // cell13.setCellValue(conn.rs.getString(13));
            //cell13.setCellStyle(innerdata_style);
            }
            
            //county active
            if(1==1){
           Cell cell14 = rw2.createCell(13);
            String val1="";
            if(conn.rs.getString(14)!=null){val1=conn.rs.getString(14);}
            cell14.setCellValue(val1);
           //cell14.setCellValue(conn.rs.getString(14));
            //cell14.setCellStyle(innerdata_style);
            }
        
            
            if(1==1){
            Cell cell15 = rw2.createCell(14);
             String val1="";
            if(conn.rs.getString(15)!=null){val1=conn.rs.getString(15);}
            cell15.setCellValue(val1);
           // cell15.setCellValue(conn.rs.getString(15));
            //cell14.setCellStyle(innerdata_style);
            }
            
            if(1==1){
            Cell cell16 = rw2.createCell(15);
             String val1="";
            if(conn.rs.getString(16)!=null){val1=conn.rs.getString(16);}
            cell16.setCellValue(val1);
            //cell16.setCellValue(conn.rs.getString(16));
            //cell14.setCellStyle(innerdata_style);
            }
            
            if(1==1){
            Cell cell17 = rw2.createCell(16);
             String val1="";
            if(conn.rs.getString(17)!=null){val1=conn.rs.getString(17);}
            cell17.setCellValue(val1);
           // cell17.setCellValue(conn.rs.getString(17));
            //cell14.setCellStyle(innerdata_style);
            }
                }
                conn.rs.close();

                // are there anymore result sets?
                results = conn.ps1.getMoreResults();
            }


            System.out.println("Finished query");

            
        
 

        // write it as an excel attachment
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte[] outArray = outByteStream.toByteArray();
        response.setContentType("application/ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0"); // eliminates browser caching
        response.setHeader("Content-Disposition", "attachment; filename=BenefitiaryList_" + dat1 + "_.xlsm");
        response.setHeader("Set-Cookie","fileDownload=true; path=/");
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();
        pkg.close();
        wb.dispose();
       // response.sendRedirect("index.jsp");
            
        } catch (InvalidFormatException ex) {
            Logger.getLogger(BenefitiaryList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BenefitiaryList.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file= new File(pathtodelete);
        
        if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
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
