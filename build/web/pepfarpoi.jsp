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
        <link rel="stylesheet" type="text/css" href="menu/clerkmenu_files/css3menu1/style.css"/>



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
                
                
       $("#cbos").html("<option value=\"\">loading cbos...</option>");         

$.ajax({
url:'loadcbos',
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






  
        </script>
        <script>
            $(function() {
	

		
                //		$( "#button" ).button();
                $( "#radioset" ).buttonset();
		
            $( "#startdate" ).datepicker({changeMonth: true,changeYear: true,yearRange:'2008:2015', dateFormat: 'dd-M-yy'});
            $( "#enddate" ).datepicker({changeMonth: true,changeYear: true,yearRange:'2008:2015',dateFormat: 'dd-M-yy'});
            

		
            });
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
    document.getElementById('txt').innerHTML = h+":"+m+":"+s;
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
            <form name=login id="login" method="post"  action="OLMISPEPFARSTOREDPROCEDURE" style="margin-left: 90px; margin-right: 10px;width:1080px;height:610px;background-color: #ffffff;">

                <section class="about" style="width:1077px;" > 
                    <h3 align="center"> <img src="images/aphia_logo.png" alt="logo" height="85px" width="270px"/></h3>
                <h4 id="txt"></h4>
                </section>             
                <br/>
                <h2 style="width: 600px; margin-left: 200px; font-family:cambria;color: #ff6600;text-align: center;"  >Select Start date, End date and Cbo </h2>
               
                
                
                <table style="margin-left:350px ;" cellpadding="5px" cellspacing="5px">
                    <tr><td style="text-align: left;">From:</td><td><input type="text" required readonly name="startdate" id="startdate" /></td>
                   <td style="text-align: left;">To:</td><td><input type="text" required readonly  name="enddate" id="enddate" /></td></tr>
                    <tr><td style="text-align: left;">CBO:</td><td><select name="cbos" required id="cbos" > <option value="">select cbo</option></select></td></tr>
                    
                    <tr><td colspan="2" style="text-align: center;">
                            <input onclick="clearfields();" style="text-align: center;margin-left: 20px;" type="submit" name="Generate" value="Generate"/></td></tr>
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
