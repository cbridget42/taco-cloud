package com.sia.tacocloud.repository;

import com.sia.tacocloud.entity.TacoOrder;
import com.sia.tacocloud.entity.TacoUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByTacoUserOrderByPlacedAtDesc(TacoUser tacoUser, Pageable pageable);
}
