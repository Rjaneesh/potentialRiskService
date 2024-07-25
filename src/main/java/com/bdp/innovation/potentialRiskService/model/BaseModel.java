package com.bdp.innovation.potentialRiskService.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Version;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Version
	@Column(name = "versionno")
	private Integer versionNo;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		if(createdDate==null) {
			createdDate = new Date();
		}
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
//		if(updatedDate == null)
//			updatedDate = new Date();
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}
	
	
}
