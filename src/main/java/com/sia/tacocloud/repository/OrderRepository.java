package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
}
