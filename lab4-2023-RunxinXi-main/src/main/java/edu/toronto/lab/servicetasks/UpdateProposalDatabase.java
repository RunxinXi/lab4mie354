package edu.toronto.lab.servicetasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import edu.toronto.dbservice.config.MIE354DBHelper;
import edu.toronto.dbservice.exceptions.SQLExceptionHandler;
import edu.toronto.dbservice.types.RankedProposal;

public class UpdateProposalDatabase implements JavaDelegate {

 Connection dbCon = null;

 public UpdateProposalDatabase() {
  dbCon = MIE354DBHelper.getDBConnection();
 }

 @Override
 public void execute(DelegateExecution execution) {

  Statement statement;
  ResultSet resultSet = null;

  try {
   statement = dbCon.createStatement();
   resultSet = statement.executeQuery("SELECT pName FROM Proposal");
   while (resultSet.next()) {
    String pName = resultSet.getString("pName");

    if (execution.getVariable(pName) != null) {
     
   /*
    *   Write your code here
    */
   

    }
   }
   resultSet.close();

   ResultSet resultSetPrint = null;
   resultSetPrint = statement.executeQuery("SELECT * FROM Proposal");
   System.out.println("The updated table:");
   while (resultSetPrint.next()) {
    String pNameUpdate = resultSetPrint.getString("pName");
    Integer pCostUpdate = resultSetPrint.getInt("pCost");
    String pRankUpdate = resultSetPrint.getString("pRank");
    System.out
      .println("pName: " + pNameUpdate + ", pCost: " + pCostUpdate + ", pRank: " + pRankUpdate + ".");
   }
   resultSetPrint.close();

  } catch (SQLException se) {
   SQLExceptionHandler.handleException(se);
  }

 }
}