package org.flowable.designer.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;;

public class Lab4_3_UnitTest extends LabBaseUnitTest {

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
		
		
		/*
		 * 	Write your code here
		 */


	}

	@Test
	public void runProcess() throws Exception {
		startProcess();
		fillProposalsForm();
	}
	
	@Test
	public void processHistory() throws Exception {
		startProcess();
		fillProposalsForm();

		HistoryService historyService = flowableContext.getHistoryService();
		List<HistoricActivityInstance> activities = historyService
		.createHistoricActivityInstanceQuery()
		.processInstanceId(processInstance.getId())
		.finished()
		.orderByHistoricActivityInstanceEndTime()
		.asc()
		.list();
		
		for ( HistoricActivityInstance activity : activities) {
		
		System.out.println("Activity name: " + activity.getActivityName());
		
		}

	}

	@After
	public void checkProcessEnded() throws Exception {

		startProcess();

		HistoryService historyService = flowableContext.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished()
				.singleResult();
//		assertNotNull(historicProcessInstance);
//		System.out.println("Process instance END time: " + historicProcessInstance.getEndTime());
	}

}
