package com.nttdata.bootcamp.msmobilewalletoperation.service;

import com.nttdata.bootcamp.msmobilewalletoperation.model.MobileWalletOperation;

import reactor.core.publisher.Mono;

public interface MobileWalletOperationService {
	Mono<MobileWalletOperation> saveOperation(Mono<MobileWalletOperation> mobileWalletAccount);
}
