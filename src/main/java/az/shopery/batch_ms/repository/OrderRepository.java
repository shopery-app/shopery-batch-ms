package az.shopery.batch_ms.repository;

import az.shopery.batch_ms.model.entity.OrderEntity;
import az.shopery.batch_ms.utils.enums.OrderStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByStatusAndIsUserNotifiedFalse(OrderStatus status);
}
