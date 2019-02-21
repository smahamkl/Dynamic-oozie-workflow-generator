package com.workflow.oozie.generator;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import com.workflow.oozie.model.DynamicWorkflowConfig;
import com.workflow.oozie.model.ForkNodeDetails;
import com.workflow.oozie.model.JavaNodeDetails;
import com.workflow.oozie.model.JoinNodeDetails;
import com.workflow.oozie.model.SSHNodeDetails;
import com.workflow.oozie.model.SparkNodeDetails;
import com.workflow.oozie.model.SqoopNodeDetails;
import com.workflow.oozie.nodes.ActionNode;
import com.workflow.oozie.nodes.Fork;
import com.workflow.oozie.nodes.OozieNodeFactory;
import com.workflow.oozie.nodes.WorkFlowApp;

public class DynamicWorkflowGeneratorImpl implements WorkflowGenerator {

	Logger LOGGER = Logger.getLogger(DynamicWorkflowGeneratorImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.workflow.oozie.generator.WorkflowGenerator#generateWorkFlow(com.workflow.
	 * oozie.model.DynamicWorkflowConfig)
	 */
	public String generateWorkFlow(DynamicWorkflowConfig dynamicWorkflowConfigObj) {
		LOGGER.info("Start of generateWorkFlow");
		WorkFlowApp workFlowApp = addNodeDetailsToWorkflow(dynamicWorkflowConfigObj);
		LOGGER.info("End of generateWorkFlow");
		return createWorkflowXMLFileAsString(workFlowApp);
	}

	/**
	 * This method is for adding each action node to the workflowApp.
	 * 
	 * @param dynamicWorkflowConfigObj
	 * @return WorkFlowApp
	 * 
	 */
	protected WorkFlowApp addNodeDetailsToWorkflow(DynamicWorkflowConfig dynamicWorkflowConfigObj) {
		LOGGER.info("Start of addNodeDetailsToWorkflow");
		OozieNodeFactory oozieNodeFactory = new OozieNodeFactory();
		WorkFlowApp workFlowApp = oozieNodeFactory.createWorkFlowApp();
		OozieNodeCreator nodeCreator = new OozieNodeCreator();

		if (dynamicWorkflowConfigObj != null) {
			workFlowApp.setName(dynamicWorkflowConfigObj.getWorkflowName());

			// Adding global node details
			if (dynamicWorkflowConfigObj.getGlobalNodeDetails() != null) {
				workFlowApp.setGlobal(nodeCreator.createGlobalNode(dynamicWorkflowConfigObj.getGlobalNodeDetails()));
			}

			// Adding SSH action node details
			if (dynamicWorkflowConfigObj.getSSHNodeDetails() != null) {
				SSHNodeDetails sshNodeDetails = dynamicWorkflowConfigObj.getSSHNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createSSHActionNode(sshNodeDetails.getNodeName(), sshNodeDetails.getHost(),
								sshNodeDetails.getCommand(), sshNodeDetails.getArgs(), sshNodeDetails.getOkToName(),
								sshNodeDetails.getErrorToName()));
			}
			// Adding Java action node details
			if (dynamicWorkflowConfigObj.getJavaNodeDetails() != null) {
				JavaNodeDetails javaNodeDetails = dynamicWorkflowConfigObj.getJavaNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin().add(nodeCreator.createJavaActionNode(
						javaNodeDetails.getNodeName(), javaNodeDetails.getJobTracker(), javaNodeDetails.getNameNode(),
						javaNodeDetails.getPrepareNode(), javaNodeDetails.getJobXML(),
						javaNodeDetails.getConfigurationProperties(), javaNodeDetails.getMainClass(),
						javaNodeDetails.getArgs(), javaNodeDetails.getOkayToName(), javaNodeDetails.getErrorToName()));
			}

			// Adding Sqoop action node details
			if (dynamicWorkflowConfigObj.getSqoopNodeDetails() != null) {
				SqoopNodeDetails sqoopNodeDetails = dynamicWorkflowConfigObj.getSqoopNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createSqoopActionNode(sqoopNodeDetails.getNodeName(),
								sqoopNodeDetails.getJobTracker(), sqoopNodeDetails.getNameNode(),
								sqoopNodeDetails.getPrepareNode(), sqoopNodeDetails.getJobXML(),
								sqoopNodeDetails.getConfigurationProperties(), sqoopNodeDetails.getArgs(),
								sqoopNodeDetails.getFiles(), sqoopNodeDetails.getOkayToName(),
								sqoopNodeDetails.getErrorToName()));
			}

			// Adding Spark action node details
			if (dynamicWorkflowConfigObj.getSparkNodeDetails() != null) {
				SparkNodeDetails sparkNodeDetails = dynamicWorkflowConfigObj.getSparkNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createSparkActionNode(sparkNodeDetails.getNodeName(),
								sparkNodeDetails.getJobTracker(), sparkNodeDetails.getNameNode(),
								sparkNodeDetails.getMaster(), sparkNodeDetails.getMode(),
								sparkNodeDetails.getApplicationName(), sparkNodeDetails.getClassName(),
								sparkNodeDetails.getJar(), sparkNodeDetails.getArgs(), sparkNodeDetails.getOkayToName(),
								sparkNodeDetails.getErrorToName()));
			}

			// Adding Fork node to the workflow
			if (dynamicWorkflowConfigObj.getForkNodeDetails() != null) {
				ForkNodeDetails forkNode = dynamicWorkflowConfigObj.getForkNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createForkActionNode(forkNode.getNodeName(), forkNode.getForkPaths()));
			}

			// Adding Join node to the workflow
			if (dynamicWorkflowConfigObj.getJoinNodeDetails() != null) {
				JoinNodeDetails joinNode = dynamicWorkflowConfigObj.getJoinNodeDetails();
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createJoinActionNode(joinNode.getNodeName(), joinNode.getJoinToName()));
			}

			// Adding End node details to workflow.xml
			if (dynamicWorkflowConfigObj.getEndNodeName() != null) {
				workFlowApp.setEnd(nodeCreator.createEndNode(dynamicWorkflowConfigObj.getEndNodeName()));
			}

			// Adding Kill node details to workflow.xml
			if (dynamicWorkflowConfigObj.getKillNodeDetails() != null) {
				workFlowApp.getDecisionOrForkOrJoin()
						.add(nodeCreator.createKillNode(dynamicWorkflowConfigObj.getKillNodeDetails().getKillNodeName(),
								dynamicWorkflowConfigObj.getKillNodeDetails().getKillNodeMessage()));
			}
		}
		LOGGER.info("End of addNodeDetailsToWorkflow");
		return workFlowApp;
	}

	/**
	 * This is to generate workflow xml file from the workflowApp The generated xml
	 * is returned in string format
	 * 
	 * @param workFlowApp
	 * @return String
	 * 
	 */
	protected String createWorkflowXMLFileAsString(WorkFlowApp workFlowApp) {
		LOGGER.info("Start of createWorkflowXMLFileAsString");
		StringWriter writer = new StringWriter();
		try {
			JAXBContext jc = JAXBContext.newInstance(WorkFlowApp.class, ActionNode.class);
			Marshaller marshaller;
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			// This is to handle the unicode and special characters.
			// marshaller.setProperty(CharacterEscapeHandler.class.getName(),new
			// XmlCharacterHandler());
			JAXBElement<WorkFlowApp> jaxbElem = new JAXBElement<WorkFlowApp>(
					new QName("uri:oozie:workflow:0.4", "workflow-app"), WorkFlowApp.class, workFlowApp);
			marshaller.marshal(jaxbElem, writer);
		} catch (JAXBException e) {
			LOGGER.info("Error occured while generating workflow xml: " + e.getMessage());
		}
		LOGGER.info("End of createWorkflowXMLFileAsString");
		return writer.toString();
	}

}
