package com.nttdata.movementwallet.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.nttdata.movementwallet.entity.MovementWallet;
import com.nttdata.movementwallet.entity.OperationStatus;
import com.nttdata.movementwallet.model.TranfersResponse;
import com.nttdata.movementwallet.service.MovementWalletService;
import com.nttdata.wallet.model.MovementWalletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class WalletConsumer {
	@Autowired
	MovementWalletService walletService;

	@Autowired
	KafkaTemplate<String, TranfersResponse> kafkaTemplate;
	@Value("${api.kafka-uri.tranfers-wallet-topic}")
	String tranfersWalletTopic;

	@KafkaListener(topics = "${api.kafka-uri.movement-wallet-topic}", groupId = "group_id")
	public void walletConsumer(MovementWalletResponse walletResponse) {
		log.info("walletConsumer Transaccion[MovementWalletResponse]" + walletResponse.toString());
		MovementWallet movementWallet = new MovementWallet();
		movementWallet.setIdOriginWallet(walletResponse.getIdOriginWallet());
		movementWallet.setIdDestinyWallet(walletResponse.getIdDestinyWallet());
		movementWallet.setTypeMovement(walletResponse.getTypeMovement());
		movementWallet.setIdCardWallet(walletResponse.getIdCard());
		movementWallet.setOperationStatus(OperationStatus.processing);		
		movementWallet.setIdOriginBankAccount(walletResponse.getOriginIdBankAccount());
		movementWallet.setIdDestinyBankAccount(walletResponse.getDestinyIdBankAccount());
		movementWallet.setAmount(walletResponse.getAmount());
		this.walletService.save(movementWallet).map(e -> {
			walletResponse.setIdMovementWallet(e.getIdMovementWallet());
			
			/**Creando el objeto de tranferts*/
			TranfersResponse tranfersResponse=new TranfersResponse();
			tranfersResponse.setAmount(walletResponse.getAmount());
			tranfersResponse.setDestinyIdBankAccount(walletResponse.getDestinyIdBankAccount());
			tranfersResponse.setDestinyPhoneNumber(walletResponse.getDestinyPhoneNumber());
			tranfersResponse.setIdCard(walletResponse.getIdCard());
			tranfersResponse.setIdDestinyWallet(walletResponse.getIdDestinyWallet());
			tranfersResponse.setIdMovementWallet(e.getIdMovementWallet());
			tranfersResponse.setIdOriginWallet(walletResponse.getIdOriginWallet());
			tranfersResponse.setMensaje(walletResponse.getMensaje());
			tranfersResponse.setOriginIdBankAccount(walletResponse.getOriginIdBankAccount());
			tranfersResponse.setOriginPhoneNumber(walletResponse.getOriginPhoneNumber());
			tranfersResponse.setTypeMovement(walletResponse.getTypeMovement());
			tranfersResponse.setOperationStatus(OperationStatus.processing);
			log.info("kafka send [walletConsumer] tranfers:" + walletResponse.toString());
			kafkaTemplate.send(tranfersWalletTopic, tranfersResponse);
			return e;
		}).subscribe();
	}

}
