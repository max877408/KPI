package com.fantasia.bean;

import java.util.Date;

public class KpiDeptYearDetail {
    private String id;

    private String deptKpiId;

    private String keyPoint;

    private String leadPerson;

    private String startTime;

    private String endTime;

    private String status;

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

    public String getDeptKpiId() {
        return deptKpiId;
    }

    public void setDeptKpiId(String deptKpiId) {
        this.deptKpiId = deptKpiId == null ? null : deptKpiId.trim();
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint == null ? null : keyPoint.trim();
    }

    public String getLeadPerson() {
        return leadPerson;
    }

    public void setLeadPerson(String leadPerson) {
        this.leadPerson = leadPerson == null ? null : leadPerson.trim();
    }

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