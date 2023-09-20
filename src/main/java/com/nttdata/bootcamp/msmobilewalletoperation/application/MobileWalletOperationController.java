package com.nttdata.bootcamp.msmobilewalletoperation.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msmobilewalletoperation.model.MobileWalletOperation;
import com.nttdata.bootcamp.msmobilewalletoperation.producer.BalanceProducer;
import com.nttdata.bootcamp.msmobilewalletoperation.service.MobileWalletOperationService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/mobilewalletoperation")
@RefreshScope
public class MobileWalletOperationController {
	
	@Autowired
	MobileWalletOperationService mobileWalletOperationService;
	
	@Autowired
	private BalanceProducer balanceProducer;
	
	@Value("${message.demo}")
	private String demoString;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<MobileWalletOperation> crear(@RequestBody MobileWalletOperation mobileWalletOperation) {
		log.info(demoString);
		return mobileWalletOperationService.saveOperation(Mono.just(mobileWalletOperation))
					.map(send->{
						send.setAmount(send.getAmount()*-1);
						return this.sendOperation(send);
					}).map(oper->this.initReciever(oper))
					.flatMap(reci-> mobileWalletOperationService.saveOperation(Mono.just(reci)))
					.map(this::sendOperation)
		.map(pro->mobileWalletOperation);
	}
	
	
	private MobileWalletOperation sendOperation(MobileWalletOperation operation) {
		log.debug("sendOperation executed {}", operation);
		if (operation != null) {
			balanceProducer.sendMessage(operation);
		}
		return operation;
	}
	
	private MobileWalletOperation initReciever(MobileWalletOperation operation) {
		String accountIdReciever = operation.getIdaccountOriDest();
		String accountIdSender = operation.getIdaccount();
		operation.setIdaccount(accountIdReciever);
		operation.setTypeAction("A");
		operation.setIdaccountOriDest(accountIdSender);
		operation.setAmount(operation.getAmount()*-1);
		operation.setId(null);
		return operation;
	}
	
	
	
}
