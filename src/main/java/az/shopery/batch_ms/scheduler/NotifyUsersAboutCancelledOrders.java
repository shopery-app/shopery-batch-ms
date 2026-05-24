package az.shopery.batch_ms.scheduler;

import az.shopery.batch_ms.utils.enums.NotificationType;
import az.shopery.batch_ms.utils.enums.OrderStatus;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyUsersAboutCancelledOrders {

    private final OrderRepository orderRepository;
    private final NotificationProducer notificationProducer;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void notifyUsersAboutCancelledOrders() {
        List<OrderEntity> orders = orderRepository.findAllByStatusAndIsUserNotifiedFalse(OrderStatus.CANCELLED);
        for (OrderEntity order : orders) {
            order.setIsUserNotified(Boolean.TRUE);

            notificationProducer.send(
                    new NotificationEvent(
                            order.getUser().getEmail(),
                            NotificationType.ORDER_CANCELLED,
                            Map.of(
                                    "userName", order.getUser().getName(),
                                    "shopOwnerName", order.getShop().getUser().getName()
                            )
                    )
            );
        }
        log.info("Marked {} orders as notified", orders.size());
    }
}
