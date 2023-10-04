package org.flowable.designer.test;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.h2.store.Data;

import edu.toronto.dbservice.types.RankedProposal;
import edu.toronto.lab.servicetasks.DisplayProposalDatabase;
import edu.toronto.dbservice.config.MIE354DBHelper;
import edu.toronto.lab.servicetasks.DisplayProposalDatabase;;

public class Lab4_1_UnitTest extends LabBaseUnitTest {

	@BeforeClass
	public static void setupFile() {
		filename = ""; // set the file name

	}

	private void startProcess() {
		RuntimeService runtimeService = flowableContext.getRuntimeService();
		processInstance = runtimeService.startProcessInstanceByKey("process1");
	}

	List<String> requiredFields = new ArrayList<>();

	private void fillProposalsForm() {
		// ASSIGN VALUES
		// this provides form input data, simulating a user entering values in fields
		// form entries are filled using a map from field id s to values
		// form entries are also process variable that can be used later in the process
		
		Map<String, String> formEntries = new HashMap<>(); 
		formEntries.put("MontrealProposal", "1");
		formEntries.put("TorontoProposal", "4");
		formEntries.put("VancouverProposal", "2");
		formEntries.put("EdmontonProposal", "5");
		formEntries.put("CalgaryProposal", "3");
		formEntries.put("MumbaiProposal", "6");

		// VALIDATE FORM FIELDS
		// get the user task (collect proposals)
		TaskService taskService = flowableContext.getTaskService();
		Task proposalsTask = taskService.createTaskQuery().taskDefinitionKey("usertask1").singleResult();

		// get the list of fields in the form
		List<String> bpmnFieldNames = new ArrayList<>();
		TaskFormData taskFormData = flowableContext.getFormService().getTaskFormData(proposalsTask.getId());
		for (FormProperty fp : taskFormData.getFormProperties()) {
			bpmnFieldNames.add(fp.getId());
		}

		// build a list of required fields that must be filled
		requiredFields.addAll(Arrays.asList("MontrealProposal","TorontoProposal","QuebecProposal"));
		
		System.out.println(requiredFields);

		// make sure that each of the required fields is in the form
		for (String requiredFieldName : requiredFields) {
			assertTrue(bpmnFieldNames.contains(requiredFieldName));
		}

		// make sure that each of the required fields was assigned a value
		for (String requiredFieldName : requiredFields) {
			assertTrue(formEntries.keySet().contains(requiredFieldName));
		}

		// SUBMIT THE FORM
		// submit the form (will lead to completing the use task)
		flowableContext.getFormService().submitTaskFormData(proposalsTask.getId(), formEntries);

	}

	@Test
	public void runProcess() throws Exception {
		startProcess();
		fillProposalsForm();
		// checkDBResults();
	}

	// validate that proposals entered are in the database are in the db
	public void checkDBResults() throws Exception {

		Connection dbCon = edu.toronto.dbservice.config.MIE354DBHelper.getDBConnection();

		assertNotNull(processInstance);
		assertNotNull(dbCon);
		int counter = 0;

		// Connect to the database and iterate over the results increasing the counter
		// variable as needed
		// Then
		// Assert that we have correct counter value rows
		// assertTrue(counter == 8);
	}

	@After
	public void checkProcessEnded() throws Exception {

		startProcess();

		HistoryService historyService = flowableContext.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished()
				.singleResult();
		assertNotNull(historicProcessInstance);

		System.out.println("Process instance END time: " + historicProcessInstance.getEndTime());
	}

}
