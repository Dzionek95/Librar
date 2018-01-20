package com.bartek.library.controller;

import com.bartek.library.model.UserNotification;
import com.bartek.library.service.UsersNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class UserNotificationController {

    private UsersNotificationService usersNotificationService;

    @Autowired
    UserNotificationController(UsersNotificationService usersNotificationService){
        this.usersNotificationService = usersNotificationService;
    }

    @GetMapping("/display")
    public List<UserNotification> getUsersAllNotifications(){
        return usersNotificationService.getUsersAllNotifications();
    }

}
