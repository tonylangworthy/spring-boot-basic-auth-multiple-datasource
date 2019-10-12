package com.webbdealer.dms.infrastructure.admin.persistence.repositories;

import com.webbdealer.dms.infrastructure.admin.persistence.entities.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
}
