package org.flowable.designer.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.task.api.Task;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


// RUN WITH PARAMETERS
@RunWith(Parameterized.class)
public class Lab4_2_UnitTest extends LabBaseUnitTest {

	@BeforeClass
	public static void setupFile() {
		filename = ""; // set the file name
	}

	// START OF PARAMETERIZED CODE
	private String name1;
	private String rank1;
	private String name2;
	private String rank2;
	private String name3;
	private String rank3;
	private String name4;
	private String rank4;
	private String name5;
	private String rank5;
		

	// Constructor has two string parameter
	public Lab4_2_UnitTest(String pname1 , String prank1, String pname2 , String prank2, String pname3 , String prank3, String pname4 , String prank4, String pname5 , String prank5){
		this.name1 = pname1;
		this.rank1 = prank1;
		this.name2 = pname2;
		this.rank2 = prank2;
		this.name3 = pname3;
		this.rank3 = prank3;
		this.name4 = pname4;
		this.rank4 = prank4;
		this.name5 = pname5;
		this.rank5 = prank5;
	}

	// Setup parameters to provide pairs of strings to the constructor
	@Parameters
	public static Collection<String[]> data() {
		ArrayList<String[]> parameters = new ArrayList<String[]>();
		parameters.add(new String[]{ "MontrealProposal", "2", "TorontoProposal" , "5", "EdmontonProposal" , "3" , "CalgaryProposal", "1" , "HalifaxProposal", "4"});
		parameters.add(new String[]{ "MontrealProposal", "5", "TorontoProposal" , "1", "EdmontonProposal" , "4" , "CalgaryProposal", "3", "HalifaxProposal", "2"});
		parameters.add(new String[]{ "MontrealProposal", "1", "TorontoProposal" , "3", "EdmontonProposal" , "2" , "CalgaryProposal", "4", "" , ""});
		parameters.add(new String[]{ "MontrealProposal", "3", "TorontoProposal" , "4", "EdmontonProposal" , "1" , "CalgaryProposal", "2", "OttawaProposal", "5"});
		parameters.add(new String[]{ "MontrealProposal", "3", "TorontoProposal" , "4", "EdmontonProposal" , "1" , "CalgaryProposal", "3", "HalifaxProposal", "5"});
		return parameters;
	}
	
	List<String> requiredFields = new ArrayList<>();
	
	public void submitFormData() {
		Task usertask1 = flowableContext.getTaskService().createTaskQuery().taskDefinitionKey("usertask1")
				.singleResult();

		// ASSIGN VALUES
		// One way to complete any task (even if it includes as form) is to call
		// the method TaskService.complete(taskid). Another way to complete a
		// task that has a form is to submit the task's form data
		// form fields are filled using a map from form field ids to values
		HashMap<String, String> formEntries = new HashMap<String, String>();
		
		formEntries.put("pName1",name1);
		formEntries.put("pRank1",rank1);
		formEntries.put("pName2",name2);
		formEntries.put("pRank2",rank2);	
		formEntries.put("pName3",name3);
		formEntries.put("pRank3",rank3);
		formEntries.put("pName4",name4);
		formEntries.put("pRank4",rank4);
		formEntries.put("pName5",name5);
		formEntries.put("pRank5",rank5);
		

		// VALIDATE FORM FIELDS
		// check that the right form fields have been created
		// checks that the task's form fields have been assigned// get the user task (input proposal ranking)
		TaskService taskService = flowableContext.getTaskService();
		Task proposalsTask = taskService.createTaskQuery().taskDefinitionKey("usertask1")
				.singleResult();
		
		// get the list of fields in the form
		List<String> bpmnFieldNames = new ArrayList<>();
		TaskFormData taskFormData = flowableContext.getFormService().getTaskFormData(proposalsTask.getId());
		for (FormProperty fp : taskFormData.getFormProperties()){
			bpmnFieldNames.add(fp.getId());
		}
		
		// build a list of required fields that must be filled
		requiredFields.addAll(Arrays.asList("pName1", "pRank1", "pName2", "pRank2", "pName3", "pRank3", "pName4", "pRank4", "pName5", "pRank5"));
		
	
		// make sure that each of the required fields was assigned a value
		for (String requiredFieldName : requiredFields) {
			assertTrue(formEntries.keySet().contains(requiredFieldName));
		}
		
		
		// SUBMIT THE FORM
		// submit the form (will lead to completing the user task)
		flowableContext.getFormService().submitTaskFormData(proposalsTask.getId(), formEntries);
	}

	private void startProcess() {
		RuntimeService runtimeService = flowableContext.getRuntimeService();
		processInstance = runtimeService.startProcessInstanceByKey("process1");
	}

	@Test
	public void startProcessTest() {
		startProcess();
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());
	}


	@Test
	public void testCheckMultipleChoice() throws Exception {
		startProcessTest();
		submitFormData();
	}

	
	


	

	

	

}
