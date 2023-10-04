package edu.toronto.lab.servicetasks;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import edu.toronto.dbservice.config.MIE354DBHelper;
import edu.toronto.dbservice.exceptions.SQLExceptionHandler;


public class DisplayProposalDatabase implements JavaDelegate{
	
	Connection dbCon = null;

	public DisplayProposalDatabase() {
		dbCon = MIE354DBHelper.getDBConnection();
	}


	@Override
	public void execute(DelegateExecution execution) {
		Statement statement;
		ResultSet resultSet = null;
		
		try {
			statement = dbCon.createStatement();
			// search for the person's name based on what was entered on the form
			resultSet = statement.executeQuery("SELECT * FROM Proposal where pRank = 1");
			while (resultSet.next()) {
				String proposalName = resultSet.getString("pName");
				String proposalCost = resultSet.getString("pCost");
				System.out.println("Winning proposal Name: " + proposalName);
				System.out.println("Winning proposal Cost: " + proposalCost);
			}

			resultSet.close();
			statement.close();

			
		} catch (SQLException se) {
			SQLExceptionHandler.handleException(se);
		}
		

	}

	
}
