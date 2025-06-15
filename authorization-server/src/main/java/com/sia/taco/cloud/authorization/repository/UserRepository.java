package com.sia.taco.cloud.authorization.repository;

import com.sia.taco.cloud.api.entity.TacoUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<TacoUser, Long> {

    TacoUser findByUsername(String username);
}
