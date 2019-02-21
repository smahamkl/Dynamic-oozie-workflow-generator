package com.workflow.oozie.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.workflow.oozie.generator.DynamicWorkflowGeneratorImpl;
import com.workflow.oozie.generator.WorkflowGenerator;
import com.workflow.oozie.model.DynamicWorkflowConfig;

public class CheckWorkflowCreation {

	static Logger logger = Logger.getLogger(CheckWorkflowCreation.class);

	public String getFile() throws IOException {
		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("json.txt").getFile());

		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}

		scanner.close();

		return result.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		logger.info("Started wfRestService:setSchedule");

		CheckWorkflowCreation driver = new CheckWorkflowCreation();

		DynamicWorkflowConfig dynamicWorkflowConfigObj = null;

		Gson gson = new Gson();
		try {
			dynamicWorkflowConfigObj = gson.fromJson(driver.getFile(), DynamicWorkflowConfig.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WorkflowGenerator workflowGenerator = new DynamicWorkflowGeneratorImpl();
		System.out.println(workflowGenerator.generateWorkFlow(dynamicWorkflowConfigObj));

	}

}
