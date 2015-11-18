package com.fantasia.bean;

import java.util.Date;

public class KpiEmployeeYear {
	private String id;

	private String kpiId;

	private String workStage;

	private String taskDivision;

	private String workContent;

	private String stageResult;

	private String startTime;

	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String status;

	private String auditStatus;

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	private String createBy;

	private Date createTime;

	private String modifyBy;

	private Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId == null ? null : kpiId.trim();
	}

	public String getWorkStage() {
		return workStage;
	}

	public void setWorkStage(String workStage) {
		this.workStage = workStage == null ? null : workStage.trim();
	}

	public String getTaskDivision() {
		return taskDivision;
	}

	public void setTaskDivision(String taskDivision) {
		this.taskDivision = taskDivision == null ? null : taskDivision.trim();
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent == null ? null : workContent.trim();
	}

	public String getStageResult() {
		return stageResult;
	}

	public void setStageResult(String stageResult) {
		this.stageResult = stageResult == null ? null : stageResult.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}