package az.shopery.batch_ms.repository;

import az.shopery.batch_ms.model.entity.task.SupportTicketEntity;
import az.shopery.batch_ms.model.entity.task.TaskEntity;
import az.shopery.batch_ms.utils.enums.TicketStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    @Query("SELECT t FROM SupportTicketEntity t JOIN FETCH t.createdBy WHERE t.ticketStatus = :status AND t.isUserNotified = false")
    List<SupportTicketEntity> findAllByTicketStatusAndIsUserNotifiedFalse(TicketStatus status);
}
