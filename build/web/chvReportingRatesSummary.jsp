<%-- 
    Document   : dashboard
    Created on : Jun 5, 2014, 5:48:42 PM
    Author     : Maureen
--%>

<%-- 
    Document   : index
    Created on : Aug 5, 2013, 9:03:18 PM
    Author     : SIXTYFOURBIT
--%>



<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OLMIS PEPFAR REPORT</title>
       
        
        <!--BUTTON MAKER IMAGE-->
        

        

        <link rel="stylesheet" type="text/css" href="css/Main.css"/>

        <link rel="shortcut icon" href="images/nyb.png"/>
        <script type="text/javascript" src="js/js/jquery-1.10.2.js"></script>

        <script type="text/javascript" src="js/noty/jquery.noty.js"></script>

        <script type="text/javascript" src="js/noty/layouts/top.js"></script>
        <script type="text/javascript" src="js/noty/layouts/center.js"></script>
        <!-- You can add more layouts if you want -->

        <script type="text/javascript" src="js/noty/themes/default.js"></script>


        <!--tooltip-->
        <link href="js/css/blitzer/jquery-ui-1.10.4.custom.css" rel="stylesheet"/>
        <script src="js/js/jquery-ui-1.10.4.custom.js"></script>
        <link rel="stylesheet" href="js/demos.css" />

        <!--  In content -->



        <!-- Main CSS -->
<!--        <link rel="stylesheet" href="css/sass-compiled.css" />-->

        <!-- Modrnizr Lib -->
        <script src="libs/modernizr.js" type="text/javascript"></script>

        <script type="text/javascript">
            $(function() {
                
                
       $("#cbos").html("<option value=\"\">loading clusters...</option>");         

$.ajax({
url:'Loadclusters?database=2014',
type:'post',
dataType:'html',
success:function (data){

       $("#cbos").html(data);

}


});                

                $( document ).tooltip();
                $( "#accordion" ).accordion();
                $( "#tabs" ).tabs({
                    show: function(event, ui) {
                        var $target = $(ui.panel);
                        $('.content:visible').effect(
                        'explode', 
                        {}, 
                        500, 
                        function(){
                            $target.fadeIn();
                        });
                    }
                });
            }); 


            //a function to dynamically add rows


//an ajax function to call the data


function loadclusters(){

$("#cbos").html('loading..');

        var db=document.getElementById("database").value;

$.ajax({
url:'Loadclusters?database='+db,
type:'post',
dataType:'html',
success:function (data){

       $("#cbos").html(data);
       $("#listtype").html("CLUSTERS");
}


});     

}

function loadcbos1(){
$("#cbos").html('loading..');
var db=document.getElementById("database").value;

$.ajax({
url:'loadcbos?database='+db,
type:'post',
dataType:'html',
success:function (data){

       $("#listtype").html("CBOs");
       $("#cbos").html(data);

}


});     




}

  
        </script>
        <script>
            $(function() {
	

		
                //		$( "#button" ).button();
                $( "#radioset" ).buttonset();
		
            $( "#startdate" ).datepicker({changeMonth: true,changeYear: true,yearRange:'2008:2015', dateFormat: 'dd-M-yy'});
            $( "#enddate" ).datepicker({changeMonth: true,changeYear: true,yearRange:'2008:2015',dateFormat: 'dd-M-yy'});
            

		
            });
            
            
            function getreport(){
                
                var startdate="";
                var enddate="";
                var cbos="";
                
                
                startdate=document.getElementById("startdate").value;
                enddate=document.getElementById("enddate").value;
                cbos=document.getElementById("cbos").value;
                
                if(startdate!=""&&enddate!=""){
                    document.getElementById("loading").innerHTML="<img src=\"images/ajax_loader.gif\" alt=\"loading\">";
//                $.ajax({
//                 url:"OLMISPEPFARSTOREDPROCEDURE?startdate="+startdate+"&enddate="+enddate+"&cbos="+cbos,   
//                 type:'post',
//                 dataType:'',
//                 success:function(){
//                     
//                  
//                   document.getElementById("loading").innerHTML="<img src=\"images/done.png\" alt=\"loading\">";  
//                 }
//                    
//                });
//                
                
                
                
// window.open("districtchooser?county="+dist.value);     
var xmlhttp;    

if (window.XMLHttpRequest)
{// code for IE7+, Firefox, Chrome, Opera, Safari
xmlhttp=new XMLHttpRequest();
}
else
{// code for IE6, IE5
xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange=function()
{
if (xmlhttp.readyState==4 && xmlhttp.status==200)
{
document.location.href="OLMISPEPFARSTOREDPROCEDURE";
}
}

 var url="OLMISPEPFARSTOREDPROCEDURE?startdate="+startdate+"&enddate="+enddate+"&cbos="+cbos;
xmlhttp.open("GET",url,true);
xmlhttp.send();
                
                
                
  
                
                }
                else{
                alert("please choose dates range");
                
                }
                
            }
            
            
            
            function reporttype(){
            if( document.getElementById("clusters").checked==true ){
              
                loadcbos1();  
               
                
            }
            else {
                
                loadclusters(); 
            }
            
            
            
            
            
            }
            
            
        </script>

        
        
        <!--PEPFAR TIME-->
        
        <script>
function startTime() {
    var today=new Date();
    var h=today.getHours();
    var m=today.getMinutes();
    var s=today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    //document.getElementById('txt').innerHTML = h+":"+m+":"+s;
    var t = setTimeout(function(){startTime()},500);
}

function checkTime(i) {
    if (i<10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}


function clearfields(){
    
    
    //document.getElementById("startdate").value="";
    //document.getElementById("enddate").value="";
    
}

</script>
        

    </head>

    <body style="margin-top: 0px;" onload="startTime()">
        <div id="container" style="height:690px;  border-top-width: thick ; overflow-y:auto ;">

            <div id="header" style="width:1200px; margin-left: 40px;">
                <br/>



            </div>



            <!--            <div id="content" style="height:590px;margin-left:150px">-->



            <%

         
            %>                


            <!--            <form action="login" method="post" style="margin-left: 90px; margin-right: 10px;width:1080px;height:610px;background-color: #ffffff;">-->
            <form name=login id="login" method="post" action="OLMISPEPFARSTOREDPROCEDURE"  style="margin-left: 90px; margin-right: 10px;width:1080px;height:610px;background-color: #ffffff;">

                <section class="about" style="width:1077px;" > 
                    <h3 align="center"> <img src="images/aphia_logo.png" alt="logo" height="85px" width="270px"/></h3>
                
                <h3 style="text-align:center; background-color:orange;"> CHV REPORTING RATES </h3>
                </section>             
                <br/>
                <h2 style="width: 600px; margin-left: 200px; font-family:cambria;color: #ff6600;text-align: center;"  >Select Start date, End date and Cbo </h2>
               
                
                
                <table style="margin-left:350px ;" cellpadding="5px" cellspacing="5px">
                    
                   <tr><td> Pepfar Year:</td><td>
                            <select title="" onchange="reporttype();" name="database" id="database" required>
                             <option value="">Select Year</option>
                             <option value="2015">2015</option>
                             <option value="2014">2014</option>
                           
                                
                                
                                
                            </select></td></tr> 
                   <tr><td style="text-align: left;">From Date :</td><td><input type="text" required readonly name="startdate" id="startdate" /></td>
                   <td style="text-align: left;">To:</td><td><input type="text" required readonly  name="enddate" id="enddate" /></td></tr>
                    <tr><td style="text-align: left;"><span id="listtype">CLUSTERS</span>:</td><td><select name="cbos" required id="cbos" > <option value="">select cluster</option></select></td>
                    
                        <td><input type="checkbox" onclick="reporttype();" name="clusters" id="clusters"/></td><td><span>Use Cbos</span></td>
                    </tr>
                    
                    <tr><td colspan="2" style="text-align: center;">
                   <input type="submit" value="GENERATE" style="margin-left: 50px; height:50px ;width:140px;" name="generate" /> 
                  
<!--               <input type="text" value="Generate" id="generate" readonly style=" cursor:pointer;margin-left: 50px; text-transform:uppercase ; height: 30px; width:124px;text-align:center ; color:white ;background:coral; border-style:ridge ;" onclick=" getreport();"/>-->
               </td></tr>
                <tr><td><p id="loading"></p></td></tr>
                </table>
                
                
                
                
                <%

                    if (session.getAttribute("login") != null) {%>
                    
                <script type="text/javascript"> 
                    var n = noty({text: '<%=session.getAttribute("login")%>',
                        layout: 'center',
                        type: 'Success',
 
                        timeout: 1800});
                    
                </script> <%

        session.removeAttribute("login");
                    }%>

             



            </form>     
           



            <div id="footer">
                <!--  <h2 align="left"> <img src="images/Coat of arms.JPG" alt="logo" height="76px" /></h2>-->

                <%
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);

                %>
                <section class="about" style="width:1250px;" > 
                    <p align="center"> &copy <a href="#" ></a> OLMIS Reports | USAID <%=year%></p>
                </section>
            </div>
        </div>

    </body>


</html>
