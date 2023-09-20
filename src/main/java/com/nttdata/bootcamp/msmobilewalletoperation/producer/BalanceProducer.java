package com.nttdata.bootcamp.msmobilewalletoperation.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.nttdata.bootcamp.msmobilewalletoperation.model.MobileWalletOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceProducer {
	private final KafkaTemplate<String, MobileWalletOperation> kafkaTemplate;

	@Value(value = "${kafka.topic.balance.name}")
	private String topic;

	public void sendMessage(MobileWalletOperation balanceModel) {

		ListenableFuture<SendResult<String, MobileWalletOperation>> future = kafkaTemplate.send(this.topic, balanceModel);

		future.addCallback(new ListenableFutureCallback<SendResult<String, MobileWalletOperation>>() {
			@Override
			public void onSuccess(SendResult<String, MobileWalletOperation> result) {
				log.info("Message {} has been sent ", balanceModel);
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Something went wrong with the balanceModel {} ", balanceModel);
			}
		});
	}
}
