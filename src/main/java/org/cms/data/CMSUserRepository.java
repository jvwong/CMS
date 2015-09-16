package org.cms.data;

import org.cms.domain.CMSUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CMSUserRepository extends JpaRepository<CMSUser, Long>, 
										   CMSUserRepositoryCustomization{	
}