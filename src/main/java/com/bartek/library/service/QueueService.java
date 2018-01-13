package com.bartek.library.service;

import com.bartek.library.model.Queue;
import com.bartek.library.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {

    private QueueRepository queueRepository;

    @Autowired
    QueueService(QueueRepository queueRepository){
        this.queueRepository = queueRepository;
    }

    public List<Queue> displayQueueToBook(Long idOfBook) {
        return queueRepository.findAllPeopleInQueue(idOfBook);
    }
}
