package com.fantasia.bean;

import java.util.Date;

public class kpiEmployeeMonth {
    private String id;

    private String kpiId;
    
    private String userId;

    private String weight;

    private String yearTarget;

    private String standard;

    private Integer kpiYear;

    private Integer kpiMonth;

    private String responsiblePerson;

    private String finishValue;

    private String finishDesc;

    private Double seftScore;

    private Double examScore;

    private String remark;

    private String isScore;

    private String status;
    
    private int sort ;
    
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

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getYearTarget() {
        return yearTarget;
    }

    public void setYearTarget(String yearTarget) {
        this.yearTarget = yearTarget == null ? null : yearTarget.trim();
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }

    public Integer getKpiYear() {
        return kpiYear;
    }

    public void setKpiYear(Integer kpiYear) {
        this.kpiYear = kpiYear;
    }

    public Integer getKpiMonth() {
        return kpiMonth;
    }

    public void setKpiMonth(Integer kpiMonth) {
        this.kpiMonth = kpiMonth;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson == null ? null : responsiblePerson.trim();
    }

    public String getFinishValue() {
        return finishValue;
    }

    public void setFinishValue(String finishValue) {
        this.finishValue = finishValue;
    }

    public String getFinishDesc() {
        return finishDesc;
    }

    public void setFinishDesc(String finishDesc) {
        this.finishDesc = finishDesc == null ? null : finishDesc.trim();
    }

    public Double getSeftScore() {
        return seftScore;
    }

    public void setSeftScore(Double seftScore) {
        this.seftScore = seftScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsScore() {
        return isScore;
    }

    public void setIsScore(String isScore) {
        this.isScore = isScore == null ? null : isScore.trim();
    }

    public String getStatus() {
        return status;
    }
    
    public int getSort() {
		return sort;
	}
    
	public void setSort(int sort) {
		this.sort = sort;
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