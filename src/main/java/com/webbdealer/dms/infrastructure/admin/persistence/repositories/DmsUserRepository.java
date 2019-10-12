package com.webbdealer.dms.infrastructure.admin.persistence.repositories;

import com.webbdealer.dms.infrastructure.admin.persistence.entities.DmsUser;
import org.springframework.data.repository.CrudRepository;

public interface DmsUserRepository extends CrudRepository<DmsUser, Long> {

    public DmsUser findByEmail(String email);
}
