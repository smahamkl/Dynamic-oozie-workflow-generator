
package com.workflow.oozie.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SqoopNodeDetails {

    @SerializedName("NodeName")
    @Expose
    private String nodeName;
    @SerializedName("JobTracker")
    @Expose
    private String jobTracker;
    @SerializedName("NameNode")
    @Expose
    private String nameNode;
    @SerializedName("JobXML")
    @Expose
    private String jobXML;
    @SerializedName("PrepareNode")
    @Expose
    private PrepareNode prepareNode;
    @SerializedName("ConfigurationProperties")
    @Expose
    private List<ConfigurationProperties> configurationProperties = new ArrayList<ConfigurationProperties>();
    @SerializedName("Args")
    @Expose
    private List<Arg> args = new ArrayList<Arg>();
    @SerializedName("Files")
    @Expose
    private List<File> files = new ArrayList<File>();
    @SerializedName("OkayToName")
    @Expose
    private String okayToName;
    @SerializedName("ErrorToName")
    @Expose
    private String errorToName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getJobTracker() {
        return jobTracker;
    }

    public void setJobTracker(String jobTracker) {
        this.jobTracker = jobTracker;
    }

    public String getNameNode() {
        return nameNode;
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public String getJobXML() {
        return jobXML;
    }

    public void setJobXML(String jobXML) {
        this.jobXML = jobXML;
    }

    public PrepareNode getPrepareNode() {
        return prepareNode;
    }

    public void setPrepareNode(PrepareNode prepareNode) {
        this.prepareNode = prepareNode;
    }

    public List<Arg> getArgs() {
        return args;
    }
    
    public List<File> getFiles() {
        return files;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }
    
    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getOkayToName() {
        return okayToName;
    }

    public void setOkayToName(String okayToName) {
        this.okayToName = okayToName;
    }

    public String getErrorToName() {
        return errorToName;
    }

    public void setErrorToName(String errorToName) {
        this.errorToName = errorToName;
    }

	public List<ConfigurationProperties> getConfigurationProperties() {
		return configurationProperties;
	}

	public void setConfigurationProperties(List<ConfigurationProperties> configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

}
