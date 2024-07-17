package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {}
