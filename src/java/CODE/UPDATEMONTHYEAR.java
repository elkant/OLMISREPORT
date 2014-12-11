/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CODE;

import database.dbConn;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel
 */
public class UPDATEMONTHYEAR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new UPDATEMONTHYEAR();
        
    }
    
    public UPDATEMONTHYEAR(){
        try {
            dbConn conn= new dbConn("1");
            
            
            String qry= "Select * from PepfarSummary";
            String qry1= "Select [OVCID],[uniquedomaincount],[Age] ,[Gender],[CBO] ,[District] ,[DistrictID],[CBOID],[SchoolLevel],[Month] ,[Year] from PepfarSummary";
            
            conn.rs=conn.state.executeQuery(qry1);
            
            
            while(conn.rs.next()){
            
                //String upd="update PepfarSummary set YearMonth='"+conn.rs.getString("Year").trim()+conn.rs.getString("Month").trim() +"' where uniqid='"+conn.rs.getString("uniqid")+"'";
                String ins="Insert into PepfarSummary1([OVCID],[uniquedomaincount],[Age] ,[Gender],[CBO] ,[District] ,[DistrictID],[CBOID],[SchoolLevel],[Month] ,[Year],[YearMonth]) values('"+conn.rs.getString(1) +"','"+conn.rs.getString(2) +"','"+conn.rs.getString(3) +"','"+conn.rs.getString(4) +"','"+conn.rs.getString(5) +"','"+conn.rs.getString(6) +"','"+conn.rs.getString(7) +"','"+conn.rs.getString(8) +"','"+conn.rs.getString(9) +"','"+conn.rs.getString(10).trim() +"','"+conn.rs.getString(11).trim() +"','"+conn.rs.getString(11).trim()+conn.rs.getString(10).trim()+"')";
                
                System.out.println(ins);
                
                //conn.state1.executeUpdate(ins);
            
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(UPDATEMONTHYEAR.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
