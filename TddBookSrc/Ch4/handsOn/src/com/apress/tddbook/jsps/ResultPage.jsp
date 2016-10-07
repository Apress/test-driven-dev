<%@ page import="java.util.Vector,
                 com.apress.tddbook.PoolResults,
                 com.apress.tddbook.PlayerResultsData"%>

<HTML><HEAD><TITLE>Pick Sheet Form</TITLE>
<BODY text=#000000 bgColor=#FFFFFF background="" ;>
<%
  System.out.println("ResultPage JSP is executing...");  

%>
<FORM name=theForm action=http://localhost:8080/FootBallPool/PlayerResultsServlet method=post >
<CENTER>
<h2><%out.println("Results for Week " + request.getParameter("poolDate")); %></h2> <BR>
<HR>

<DIV align=center>
<CENTER>
<p>Last Weeks Winner 
        <% 
           PoolResults poolResults = (PoolResults)request.getAttribute("poolResults");
           
           out.println(" " + poolResults.winningPlayersName); 
        %>
        </p>
        <p align="center">Last Weeks Results by Player</p>
        <div align="left">
          <table border="1" width="100%">
            <tr>
              <td width="50%" bgcolor="#C0C0C0">Players Name</td>
              <td width="50%" bgcolor="#C0C0C0">Correct Picks</td>
            </tr>
            <%
              PlayerResultsData results;
                            
              for(int i = 0; i < poolResults.playerResultsList.size(); i++)
              {
                  results = (PlayerResultsData)poolResults.playerResultsList.elementAt(i);
                  out.println("<tr>");
                  out.println("<td width='50%'>" + results.playerName + "</td>");
                  out.println("<td width='50%'>" + results.correctPicks + "</td>");
                  out.println("</tr>");
              }             
          %>

          </table></CENTER></DIV>

       </body>
</html>



