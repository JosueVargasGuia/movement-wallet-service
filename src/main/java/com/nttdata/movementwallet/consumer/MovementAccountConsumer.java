package com.nttdata.movementwallet.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nttdata.movementwallet.model.TranfersResponse;
import com.nttdata.movementwallet.service.MovementWalletService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MovementAccountConsumer {

	@Autowired
	MovementWalletService walletService;

	@KafkaListener(topics = "${api.kafka-uri.tranfers-wallet-topic-respose}", groupId = "group_id")
	public void walletConsumerResponse(TranfersResponse tranfersResponse) {
		log.info("walletConsumerResponse Transaccion[MovementWalletResponse] result" + tranfersResponse.toString());
		this.walletService.findById(tranfersResponse.getIdMovementWallet()).flatMap(e->{
			log.info("Update Transaccion");
			e.setOriginIdMovementAccount(tranfersResponse.getOriginIdMovementAccount());
			e.setDestinyIdMovementAccount(tranfersResponse.getDestinyIdMovementAccount());
			e.setOperationStatus(tranfersResponse.getOperationStatus());			 
			return this.walletService.update(e);
		}).subscribe();
	}
}
