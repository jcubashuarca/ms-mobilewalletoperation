package com.nttdata.bootcamp.msmobilewalletoperation.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msmobilewalletoperation.model.MobileWalletOperation;

@Repository
public interface MobileWalletOperationRepository extends ReactiveMongoRepository<MobileWalletOperation, String> {

}
