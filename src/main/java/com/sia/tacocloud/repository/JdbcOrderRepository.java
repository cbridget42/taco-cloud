package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.model.Taco;
import com.sia.tacocloud.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {

    private static final String CREATE_TACO_ORDER = "INSERT INTO taco_order " + "(delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, " + "cc_number, cc_expiration, cc_cvv, placed_at) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String CREATE_TACO = "INSERT INTO taco (name, created_at, taco_order, taco_order_key) " + "VALUES (?,?,?,?)";
    private static final String CREATE_INGREDIENT_REF = "INSERT INTO ingredient_ref " + "(ingredient, taco, taco_key) VALUES (?,?,?)";

    private final JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(CREATE_TACO_ORDER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(List.of(order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(), order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(), order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        Long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    private Long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(CREATE_TACO, Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(List.of(taco.getName(), taco.getCreatedAt(), orderId, orderKey));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        Long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(Long tacoId, List<Ingredient> ingredients) {
        int key = 0;
        for (Ingredient ingredient : ingredients) {
            jdbcOperations.update(CREATE_INGREDIENT_REF, ingredient.getId(), tacoId, key++);
        }
    }
}
