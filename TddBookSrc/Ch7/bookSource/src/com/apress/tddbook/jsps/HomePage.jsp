<%@ page import="com.apress.tddbook.FootballPool,
                 com.apress.tddbook.PoolResults,
                 java.util.Vector,
                 com.apress.tddbook.PlayerPickRec"%>
<%
  System.out.println("HomePage JSP is executing...");
  String playersName = request.getParameter("playersName");
  System.out.println("Players name is " + playersName);  
%>
<html>
  <head><title>TDD Football Pool</title></head>
  <body bgcolor=#FFFFFF>
  <h1>Welcome to the Football Pool <%= playersName %></h1>
<div align="left">
  <table border="1" width="100%">
    <tr>
      <td width="50%">
<div align="left">
  <table border="0" width="100%">
    <tr>
      <td width="100%" bgcolor="#000000">
<p align="center"><font size="4" color="#FFFFFF">Week 6 games&nbsp;</font></p>
      </td>
    </tr>
  </table>
</div>
<p align="center">Pool Status Open</p>
        <div align="center">
          <center>
          <table border="1" bordercolordark="#FFFFFF">
          <tr>
              <td bgcolor="#C0C0C0">This Weeks Games</td>
          <tr>
          <%
              FootballPool pool = (FootballPool)request.getAttribute("poolInfo");
              if(pool != null)
              {
              int numGames = pool.getGameList().size();
                  for(int i = 0; i < numGames; i++)
                  {
                      out.println("<tr>");
                      out.println("<td>" + pool.getAwayTeam(i) + " at " + pool.getHomeTeam(i) + "</td>");
                      out.println("</tr>");
                  }
              }
          %>

          </table>
          </center>
        </div>
 <p align="center"><a href="Pick%20Sheet.htm">Make your Picks</a>&nbsp; <a href="Pick%20Summary.htm">View Pick Summary</a>
        <a href="http://sports.espn.go.com/nfl/schedule"> View Team Past
        Results</a>&nbsp;</p>
        <p>&nbsp;</td>

      <td width="50%" valign="top">
        <div align="left">
          <table border="0" width="100%">
            <tr>
              <td width="100%" bgcolor="#000000">
                <p align="center"><font size="4" color="#FFFFFF">Player Standings</font></td>
            </tr>
          </table>
        </div>
        <p>Last Weeks Winner Matt Carol (12-4)</p>
        <p align="center">Last Weeks Results by Player</p>
        <div align="left">
          <table border="1" width="100%">
            <tr>
              <td width="50%" bgcolor="#C0C0C0">Players Name</td>
              <td width="50%" bgcolor="#C0C0C0">Correct Picks</td>
            </tr>
            <%
              Vector resultsList = (Vector)request.getAttribute("poolResults");
              PoolResults results;
              if(resultsList != null)
              {
                  int numPlayers = resultsList.size();
                  for(int i = 0; i < numPlayers; i++)
                  {
                      results = (PoolResults)resultsList.elementAt(i);
                      out.println("<tr>");
                      out.println("<td width='50%'>" + results.getPlayerName() + "</td>");
                      out.println("<td width='50%'>" + results.getCorrectPicks() + "</td>");
                      out.println("</tr>");
                  }
              }
          %>

          </table>
          <p align="center">Your Picks Results by Week</p>
          <div align="left">
            <table border="0" width="100%">
              <tr>
                <td width="50%" bgcolor="#C0C0C0">Week&nbsp;</td>
                <td width="50%" bgcolor="#C0C0C0">Correct Picks</td>
              </tr>
               <%
              Vector playerPickRecs = (Vector)request.getAttribute("playersPickRecs");
              PlayerPickRec picRec;
              if(playerPickRecs != null)
              {
                  int numWeeks = playerPickRecs.size();
                  for(int i = 0; i < numWeeks; i++)
                  {
                      picRec = (PlayerPickRec)playerPickRecs.elementAt(i);
                      out.println("<tr>");
                      out.println("<td width='50%'>Week" + picRec.getWeekNum() + "</td>");
                      out.println("<td width='50%'>" + picRec.getCorrectPicks() + "</td>");
                      out.println("</tr>");
                  }
              }
          %>
            </table>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
<p>&nbsp;</p>

  </body>
</html>
<%
  System.out.println("HomePage JSP is all done\n");
%>

