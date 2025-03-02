package com.sia.taco.cloud.server.repository;

import com.sia.taco.cloud.api.entity.TacoOrder;
import com.sia.taco.cloud.api.entity.TacoUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByTacoUserOrderByPlacedAtDesc(TacoUser tacoUser, Pageable pageable);
}
