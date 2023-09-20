package com.nttdata.bootcamp.msmobilewalletoperation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msmobilewalletoperation.infraestructure.MobileWalletOperationRepository;
import com.nttdata.bootcamp.msmobilewalletoperation.model.MobileWalletOperation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MobileWalletOperationServiceImpl implements  MobileWalletOperationService {
	
	@Autowired
	MobileWalletOperationRepository mobileWalletOperationRepository;
	
	@Override
	public Mono<MobileWalletOperation> saveOperation(Mono<MobileWalletOperation> MobileWalletOperation) {
		log.debug("dato",MobileWalletOperation);
		return MobileWalletOperation.flatMap(mwo ->{
			if(mwo.getAmount() == null) {
				mwo.setAmount(0d);
			}
			return mobileWalletOperationRepository.save(mwo);
		});
	}

}
