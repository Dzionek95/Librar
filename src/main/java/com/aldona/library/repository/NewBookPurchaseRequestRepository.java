package com.aldona.library.repository;

import com.aldona.library.model.NewBookPurchaseRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewBookPurchaseRequestRepository extends CrudRepository<NewBookPurchaseRequest, Long>{
}
