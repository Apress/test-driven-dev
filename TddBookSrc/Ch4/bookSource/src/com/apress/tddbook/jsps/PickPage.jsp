<%@ page import="com.apress.tddbook.FootballPool,
                 com.apress.tddbook.Game"%>

<HTML><HEAD><TITLE>Pick Sheet Form</TITLE>
<BODY text=#000000 bgColor=#00FFFF background="" ;>
<%
  System.out.println("PickPage JSP is executing...");
  String playersName = request.getParameter("playersName");

%>
<FORM name=theForm action=http://localhost:8080/FootBallPool/PlayerPickServlet method=post
encType=x-www-form-encoded>
<CENTER>
<h2><%out.println(request.getParameter("playersName") +  "'s Picksheet for Week " + request.getParameter("weekNum"));%></h2><BR>
<HR>
<FONT color=#000080>For each row in the table select the team you expect to win.

<HR>
</FONT></CENTER><BR>
<DIV align=center>
<CENTER>
<TABLE cellSpacing=0 cellPadding=1 border=1>
  <TBODY>
  <TR align=middle bgColor=#000000>
    <TD align=middle width=50><FONT color=#ffffff><BIG><B></B></BIG></FONT></TD>
    <TD align=middle width=220><FONT
      color=#ffffff><BIG><B>Home</B></BIG></FONT></TD>
    <TD align=middle width=220><FONT
      color=#ffffff><BIG><B>Away</B></BIG></FONT></TD></TR>


      <%
              FootballPool pool = (FootballPool)request.getAttribute("openPool");
              if(pool != null)
              {
              int numGames = pool.size();
                  for(int i = 0; i < numGames; i++)
                  {
                      String homeTeamName = pool.getHomeTeam(i);
                      String awayTeamName = pool.getAwayTeam(i);

                      out.println("<TR align=middle>");
                      out.println("<TD align=middle width=50>" + (i + 1) + "</TD>");
                      out.println("<TD align=right width=220><IMG src='images/" + homeTeamName + ".gif'" +
                              "align=left><FONT color=#000000>" +  homeTeamName+ "<INPUT type=radio size=2 " +
                              "name=Game_"+ i + " ></FONT></TD>");
                      out.println("<TD align=right width=220><IMG src='images/" + awayTeamName + ".gif'" +
                              "align=left><FONT color=#000000>" +  awayTeamName+ "<INPUT type=radio size=2 " +
                              "name=Game_"+ i + " ></FONT></TD>");
                      out.println("</tr>");
                  }
              }
          %>
      </TBODY></TABLE></CENTER></DIV>
<DIV align=center>
<CENTER><FONT color=#000000>
<P>Monday Night Total <INPUT size=2 name=MNTotal></P></FONT></CENTER></DIV>
<DIV align=center>
<CENTER><FONT color=#000000>
<P>E-Mail Address <INPUT name=email type=text>&nbsp; </P></FONT></CENTER></DIV>
<HR align=center>

<DIV align=center>
<CENTER>
<P><INPUT type=button value="Send Picks"> </P></DIV></FORM></CENTER></BODY></HTML>