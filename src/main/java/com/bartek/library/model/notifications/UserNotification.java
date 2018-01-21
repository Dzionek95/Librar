package com.bartek.library.model.notifications;

import com.bartek.library.model.accounts.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Account account;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeOfCreationOfNotification;
    private String message;
    private NotificationType notificationType;
}
