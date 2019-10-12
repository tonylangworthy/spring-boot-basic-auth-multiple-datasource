package com.webbdealer.dms.infrastructure.admin.persistence.repositories;

import com.webbdealer.dms.infrastructure.admin.persistence.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
