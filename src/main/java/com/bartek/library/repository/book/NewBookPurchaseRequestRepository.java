package com.bartek.library.repository.book;

import com.bartek.library.model.book.NewBookPurchaseRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewBookPurchaseRequestRepository extends CrudRepository<NewBookPurchaseRequest, Long>{
}
