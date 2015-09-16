package org.cms.data;

import org.cms.domain.CMSUser;

public interface CMSUserRepositoryCustomization{
	CMSUser findByUsername(String username);
}