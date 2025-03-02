package com.sia.taco.cloud.server.repository;

import com.sia.taco.cloud.api.entity.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Page<Taco> findAll(Pageable pageable);
}
