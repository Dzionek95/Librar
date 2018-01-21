package com.bartek.library.repository.notifications;

import com.bartek.library.model.notifications.Penalty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends CrudRepository<Penalty, Long>{

    Penalty findPenaltyByAccountId(long id);

}
