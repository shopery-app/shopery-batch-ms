package az.shopery.batch_ms.scheduler;

import az.shopery.batch_ms.utils.enums.NotificationType;
import az.shopery.batch_ms.utils.enums.TicketStatus;
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
public class NotifyUsersAboutClosedSupportTickets {

    private final TaskRepository taskRepository;
    private final NotificationProducer notificationProducer;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void notifyUsersAboutClosedSupportTickets() {
        List<SupportTicketEntity> tickets = taskRepository.findAllByTicketStatusAndIsUserNotifiedFalse(TicketStatus.CLOSED);
        for (SupportTicketEntity ticket : tickets) {
            ticket.setIsUserNotified(Boolean.TRUE);

            notificationProducer.send(
                    new NotificationEvent(
                            ticket.getCreatedBy().getEmail(),
                            NotificationType.SUPPORT_TICKET_CLOSED,
                            Map.of(
                                    "userName", ticket.getCreatedBy().getName(),
                                    "ticketId", ticket.getId(),
                                    "ticketSubject", ticket.getSubject()
                            )
                    )
            );
        }
        log.info("Marked {} support tickets as notified", tickets.size());
    }
}
