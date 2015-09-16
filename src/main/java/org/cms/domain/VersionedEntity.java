package org.cms.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionedEntity extends BaseEntity{
	
	private Long version;
	
	/**
	 * 
	 * @return Long Version
	 */
	@Version
	@Column(name = "Revision")
	public Long getVersion(){
		return this.version;
	}
	
	/**
	 * Declare package private
	 * @param version The version
	 */
	void setVersion(Long version){
		this.version = version;
	}
}
