package org.cms.domain;

import java.time.Instant;

import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.cms.converters.InstantConverter;
import org.cms.serializers.DateInstantDeserializer;
import org.cms.serializers.DateInstantSerializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAuditedEntity extends VersionedEntity{
	
	@CreatedDate
	private Instant createdDate;
	
	@LastModifiedDate
	private Instant lastModifiedDate;
	
	@Convert(converter = InstantConverter.class)
	@JsonSerialize(using = DateInstantSerializer.class)
	@JsonDeserialize(using = DateInstantDeserializer.class)
	public Instant getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	@Convert(converter = InstantConverter.class)
	@JsonSerialize(using = DateInstantSerializer.class)
	@JsonDeserialize(using = DateInstantDeserializer.class)
	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
