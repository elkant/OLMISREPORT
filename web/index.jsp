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
        <title>OLMIS WEB BASED REPORTS</title>


        <!--BUTTON MAKER IMAGE-->




        <link rel="stylesheet" type="text/css" href="css/Main.css"/>

        <link rel="shortcut icon" href="images/nyb.png"/>
        <script type="text/javascript" src="js/js/jquery-1.10.2.js"></script>

        <script type="text/javascript" src="js/noty/jquery.noty.js"></script>

        <script type="text/javascript" src="js/noty/layouts/top.js"></script>
        <script type="text/javascript" src="js/noty/layouts/center.js"></script>
        <!-- You can add more layouts if you want -->

        <script type="text/javascript" src="js/noty/themes/default.js"></script>




        <script type="text/javascript" src="js/jquery.fileDownload.js"></script>



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
            $(function () {


                $("#cbos").html("<option value=\"\">loading clusters...</option>");

                reporttype();

                $(document).tooltip();
                $("#accordion").accordion();
                $("#tabs").tabs({
                    show: function (event, ui) {
                        var $target = $(ui.panel);
                        $('.content:visible').effect(
                        'explode',
                        {},
                        500,
                        function () {
                            $target.fadeIn();
                        });
                    }
                });
            });


            //a function to dynamically add rows


            //an ajax function to call the data


            function loadclusters() {

                $("#cbos").html('loading..');

                var db = document.getElementById("database").value;
                if (db == '') {
                    db = '2014';
                }
                $.ajax({
                    url: 'Loadclusters?database=' + db,
                    type: 'post',
                    dataType: 'html',
                    success: function (data) {

                        $("#cbos").html(data);
                        $("#listtype").html("CLUSTERS");
                    }


                });

            }

            function loadcbos1() {
                $("#cbos").html('loading..');
                var db = document.getElementById("database").value;
                if (db == '') {
                    db = '2014';
                }
                $.ajax({
                    url: 'loadcbos?database=' + db,
                    type: 'post',
                    dataType: 'html',
                    success: function (data) {

                        $("#listtype").html("CBOs");
                        $("#cbos").html(data);

                    }


                });




            }


            function changeAction() {

                var curaction = document.getElementById("report").value;

                document.getElementById("login").action = curaction;


            }



        </script>
        <script>
            $(function () {



                //		$( "#button" ).button();
                $("#radioset").buttonset();

                $("#startdate").datepicker({changeMonth: true, changeYear: true, yearRange: '2008:2015', dateFormat: 'dd-M-yy'});
                $("#enddate").datepicker({changeMonth: true, changeYear: true, yearRange: '2008:2015', dateFormat: 'dd-M-yy'});



            });


            function getreport() {

                var startdate = "";
                var enddate = "";
                var cbos = "";


                startdate = document.getElementById("startdate").value;
                enddate = document.getElementById("enddate").value;
                cbos = document.getElementById("cbos").value;
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos

                if (startdate != "" && enddate != "") {
                    document.getElementById("loading").innerHTML = "<img src=\"images/ajax_loader.gif\" alt=\"loading\">";
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
                        xmlhttp = new XMLHttpRequest();
                    }
                    else
                    {// code for IE6, IE5
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xmlhttp.onreadystatechange = function ()
                    {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                        {
                            document.location.href = "OLMISPEPFARSTOREDPROCEDURE";
                        }
                    }

                    var url = "OLMISPEPFARSTOREDPROCEDURE?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos;
                    xmlhttp.open("GET", url, true);
                    xmlhttp.send();





                }
                else {
                    alert("please choose dates range");

                }

            }



            function reporttype() {
                if (document.getElementById("clusters").checked == true) {

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
                var today = new Date();
                var h = today.getHours();
                var m = today.getMinutes();
                var s = today.getSeconds();
                m = checkTime(m);
                s = checkTime(s);
                //document.getElementById('txt').innerHTML = h+":"+m+":"+s;
                var t = setTimeout(function () {
                    startTime()
                }, 500);
            }

            function checkTime(i) {
                if (i < 10) {
                    i = "0" + i
                }
                ;  // add zero in front of numbers < 10
                return i;
            }


            function clearfields() {


                //document.getElementById("startdate").value="";
                //document.getElementById("enddate").value="";

            }
            function togglecheckbox() {
                if (document.getElementById("database").value != "") {

                    document.getElementById("clusters").disabled = false;
                }
                else {
                    document.getElementById("clusters").disabled = true;

                }



            }
            
            function validatedate(){
                
                
                var yr= document.getElementById("database").value;       
                var stdate=document.getElementById("startdate").value; 
                var edate=document.getElementById("enddate").value; 
                //substring(0, 6) ==
                if(stdate===""||stdate.substring(0,6)==="01-Oct"){
            
                    document.getElementById("startdate").value="01-Oct-"+(yr-1);
            
                    //var res = str.replace("Microsoft", "W3Schools");                                                  }
            
         
        
                }
            
            }
            
          
            
            
            function comparedates(){
          
                var stdate=document.getElementById("startdate").value; 
                var edate=document.getElementById("enddate").value; 
                var cboz=document.getElementById("cbos").value;
        
                if(stdate!==""&&edate!==""){
                    //now compare the dates
                    //              stdate=new Date(stdate);
                    //          edate=new Date(edate);
           
           
                    var stdat=stdate.replace(/-/g, ",");
                    var edat=edate.replace(/-/g, ",");       
                  
                    if( new Date(stdat)> new Date(edat)){
               
           
               
                        document.getElementById('notice').innerHTML="<font color='red'> Start date ( "+stdate+" ) should not be greater than end date ( "+edate+" ). Please correct that to proceed !</font>";   
                        $("#notice").css("border-color","#FF0000");
                        $("#notice").css("background-color","#FFFFFF");
             
                        $("#enddate").slideToggle("slow",function() {});
                        $("#enddate").slideToggle("slow",function() {});  
                        $("#enddate").css("border-color","#FF0000");  
           
                        document.getElementById('startdate').value;  
                        return false;
                    }
                    else if(document.getElementById("startdate").value===''){
                  
                        $("#startdate").css("border-color","#ff0000");   
                  
                        $("#startdate").slideToggle("slow",function() {});
                        $("#startdate").slideToggle("slow",function() {});   
                        $("#startdate").focus();
                        return false; 
                    }
              
                    else if(document.getElementById("enddate").value===''){
                  
                  
                        $("#enddate").css("border-color","#ff0000");          
                        $("#enddate").slideToggle("slow",function() {});
                        $("#enddate").slideToggle("slow",function() {});   
                        $("#enddate").focus();
                        return false; 
                    }
              
              
                    else {
                  
                        var stdate=document.getElementById("startdate").value; 
                        var edate=document.getElementById("enddate").value;     
        
                        var stdate1=stdate.replace(/-/g, ",");
                        var edate1=edate.replace(/-/g, ",");
       
                        var monthsbetween=miezi
                        ( new Date(edate1), new Date(stdate1));
                        if(monthsbetween==1){ monthsbetween=monthsbetween+" month"}
                        if(monthsbetween>1){ monthsbetween=monthsbetween+" months"}
        
                        if(monthsbetween==0){
            
                            monthsbetween=days( new Date(stdate1) , new Date(edate1)) +" days";
            
                        }
                  
                        $("#startdate").css("border-color","#E0E0D1");   
                        $("#enddate").css("border-color","#E0E0D1");   
                        document.getElementById('notice').innerHTML="<font color='green'> report between date "+stdate+" and date "+edate+". </font>  [ <b> "+monthsbetween+" </b>  ]";   
                        $("#notice").css("border-color","#00FF00");
                        $("#notice").css("background-color","#FFFFFF");
          
                        if(cboz==''){
                            document.getElementById('notice').innerHTML="<font color='red'> Please choose the cluster/cbo </font>";   
                       
                  
                            $("#clusters").css("border-color","#ff0000");          
                            $("#clusters").slideToggle("slow",function() {});
                            $("#clusters").slideToggle("slow",function() {});   
                            $("#clusters").focus();
                            return false;
                        }
                        else {
                            return true;
                  
                  
                        }
            
                    }
                }
                else if(document.getElementById("enddate").value===''){
             
         
                    document.getElementById('notice').innerHTML="<font color='red'> Please choose the end date</font>";   
                       
                  
                    $("#enddate").css("border-color","#ff0000");          
                    $("#enddate").slideToggle("slow",function() {});
                    $("#enddate").slideToggle("slow",function() {});   
                    $("#enddate").focus();
                    return false; 
                }
              
                else if(document.getElementById("startdate").value===''){
             
         
                    document.getElementById('notice').innerHTML="<font color='red'> Please choose the start date</font>";   
                       
                  
                    $("#startdate").css("border-color","#ff0000");          
                    $("#startdate").slideToggle("slow",function() {});
                    $("#startdate").slideToggle("slow",function() {});   
                    $("#startdate").focus();
                    return false; 
          
                }
              
        

                else if(cboz===''){
             
         
                    document.getElementById('notice').innerHTML="<font color='red'> Please choose the cluster/cbo </font>";   
                       
                  
                    $("#cbos").css("border-color","#ff0000");          
                    $("#cbos").slideToggle("slow",function() {});
                    $("#cbos").slideToggle("slow",function() {});   
                    $("#cbos").focus();
                    return false; 
          
                }
                else{
               
                    return true;
                  
                  
                }

            }     

        </script>


        <script>
            
            function initializedownload(){
                
                
                if(comparedates()==true)
                {
                    
                    downloadrpt();  
                    
                }
            }
            
            
            function displayduration(){
       
                var stdate=document.getElementById("startdate").value; 
                var edate=document.getElementById("enddate").value;     
        
                var stdate1=stdate.replace(/-/g, ",");
                var edate1=edate.replace(/-/g, ",");
       
                var monthsbetween=miezi
                ( new Date(edate1), new Date(stdate1));
                if(monthsbetween==1){ monthsbetween=monthsbetween+" month"}
                if(monthsbetween>1){ monthsbetween=monthsbetween+" months"}
        
                if(monthsbetween==0){
            
                    monthsbetween=days( new Date(stdate1) , new Date(edate1)) +" days";
            
                }
        
                $("#startdate").css("border-color","#E0E0D1");   
                $("#enddate").css("border-color","#E0E0D1");  
                var edatecopy=" _____________ ";
                if(edate1!==""){
                    edatecopy=edate1;
                }
        
                if(stdate===""&&edate===""){
       
                    document.getElementById('notice').innerHTML="<font color='green'> Select appropriate parameters to generate a report. </font>";   
                
                }
                if((stdate===""&&edate!=="")||(stdate!==""&&edate==="")){
       
                    document.getElementById('notice').innerHTML="<font color='green'> Report between date  </font><font color='orange'>"+stdate+"</font> <font color='green'>  and date </font> <font color='orange'>"+edatecopy+" </font>"; 
                }
        
                //if both date are not blank, then show wahts the difference between the periods in monts 
                if(stdate!==""&&edate!==""){
                    document.getElementById('notice').innerHTML="<font color='green'> Report between date  </font><font color='orange'>"+stdate+" </font> <font color='green'> and date </font> <font color='orange'> "+edate+".  </font> [ <b> "+monthsbetween+" </b>  ]"; 
            
            
                }
        
                $("#notice").css("border-color","#00FF00");
                $("#notice").css("background-color","#FFFFFF");
        
        
            }
        
        
     
    
  
            // miezi( Date('01 Oct,2014'),Date('14 Dec,2014'));                
            function miezi(firstDate, secondDate) {
        
       
                var fm = firstDate.getMonth();
                var fy = firstDate.getFullYear();
                var sm = secondDate.getMonth();
                var sy = secondDate.getFullYear();
                var months = Math.abs(((fy - sy) * 12) + fm - sm);
                var firstBefore = firstDate > secondDate;
                firstDate.setFullYear(sy);
                firstDate.setMonth(sm);
                firstBefore ? firstDate < secondDate ? months-- : "" : secondDate < firstDate ? months-- : "";
    
                return months;
       
            }

 
        
        
            function days (d1, d2) {
                var t2 = d2.getTime();
                var t1 = d1.getTime();
 
                return parseInt((t2-t1)/(24*3600*1000));
            }
    
    
            //do a loading function 
            function showprogress() {

   

                $('.loading').show();

                var curaction1 = document.getElementById("report").value;
                var startdate = document.getElementById("startdate").value;
                var enddate = document.getElementById("enddate").value;
                var cbos = document.getElementById("cbos").value;
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos
                var database=document.getElementById("database").value;
                var ur=curaction1+"?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos+"&database="+database;
 

                $.ajax({
                    type: 'HEAD',
                    url: curaction1+"?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos+"&database="+database,
                    complete: function () {

                        $('.loading').hide();
                        document.location.assign(curaction1);
                        //              var iframe = document.createElement("iframe");
                        //  iframe.setAttribute("src",ur);
                        //  iframe.setAttribute("style", "display: none");
                        //  document.body.appendChild(iframe);
            
            
                        //            var ifr = $('<iframe />').attr('src', curaction1).hide().appendTo("body")
                        //            setTimeout(function () {ifr.remove();}, 5000);
                    }

                });

            }
    
            function downloadrpt(){
                $('.loading').show();
                var curaction1 = document.getElementById("report").value;
                var startdate = document.getElementById("startdate").value;
                var enddate = document.getElementById("enddate").value;
                var cbos = document.getElementById("cbos").value;
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos
                var database=document.getElementById("database").value;
                var ur=curaction1+"?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos+"&database="+database;
 
                $.fileDownload(ur).done(function () { $('.loading').hide();}).fail(function () { alert('Report generation failed!');$('.loading').hide(); });
 
                //$('.loading').hide();
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
            <form name="login" id="login" method="post" action="PepfarSummary" onsubmit=""  style="margin-left: 50px; margin-right: 10px;width:1180px;height:610px;background-color: #ffffff;">

                <section class="about" style="width:1177px;" > 
                    <h3 align="center"> <img src="images/aphia_logo.png" alt="logo" height="85px" width="270px"/></h3>

                    <h3 style="text-align:center; background-color:orange;"> OLMIS REPORTS </h3>
                </section>             
                <br/>
                <img src='image/ajax_loader.gif' alt='loading' style="display:none; margin-left:500px ;" class='loading'/>


                <h2 id="notice" style="background-color: yellowgreen;text-align: center;"> Select the appropriate parameters to generate a report </h2>
                <h2 style="width: 600px; margin-left: 200px; font-family:cambria;color: #ff6600;text-align: center;"  > </h2>



                <table style="margin-left:230px ;" cellpadding="5px" cellspacing="5px">

                    <tr><td> Pepfar Year:</td><td>
                            <select title="" onchange="reporttype();togglecheckbox();validatedate();displayduration();" name="database" id="database" required>
                                <option value="">Select Year</option>
                                <option value="2015">2015</option>
                                <option value="2014">2014</option>




                            </select></td>  
                        <td>Select report:</td>
                        <td><select name="report" id="report" onchange="changeAction();">
                                <option value="PepfarSummary">PEPFAR Summary Report</option>
                                <option value="CHVReportingRatesSummary">CHV Reporting Rates Summary </option>
                                <option value="ConsolidatedReport">Consolidated Report </option>
<!--                                <option value="NeedsVsServed">*Needs Vs Served</option>-->
<!--                                <option value="NeedsVsServed">*Needs Vs Served detailed</option>-->
<!--                                <option value="ExitSummary">*Exit Summary </option>-->
                                <!--<option title="This report is slow.please be patient." value="BenefitiaryList">*OVC Beneficiary List(very slow) </option>-->
                                 <option value="RegistrationByCHV">OVC Registration List by CHV</option>
                                <option value="RegistrationByHH">OVC Registration List  by HH</option>
<!--                                <option value="RegistrationPerMonth">Registration Per Month </option>-->
                            </select>
                        </td>
                    </tr> 
                    <tr><td style="text-align: left;">From Date :</td><td><input type="text" required="true" onchange="displayduration();" readonly name="startdate" id="startdate" /></td>
                        <td style="text-align: left;">To:</td><td><input type="text" required="true" onchange="displayduration();"  readonly  name="enddate" id="enddate" /></td></tr>
                    <tr><td style="text-align: left;"><span id="listtype">CLUSTERS</span>:</td><td><select name="cbos" required id="cbos" > <option value="">select cluster</option></select></td>

                        <td><input type="checkbox" disabled="true" onclick="reporttype();" name="clusters" id="clusters"/></td><td><span>Use Cbos <i> (instead of Clusters)</i></span></td>
                    </tr>

                    <tr><td colspan="2" style="text-align: center;">

                            <!--                            <input type="submit" onmouseover="changeAction();displayduration();" value="GENERATE" style="margin-left: 50px; height:50px ;width:140px;" name="generate" /> -->

                            <input type="text" value="Generate" id="generate1" class ='generate1' onmouseover="changeAction();displayduration();" onclick="initializedownload();" readonly style=" cursor:pointer;margin-left: 50px; text-transform:uppercase ; height: 50px; width:140px;text-align:center ; color:white ;background:coral; border-style:ridge ;" />
                        </td></tr>
                    <tr><td></td></tr>
                </table>




                <%                    if (session.getAttribute("login") != null) {%>

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
        <script>
    
            displayduration();
      
            //            
        </script>
    </body>


</html>
