package com.nttdata.movementwallet.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nttdata.movementwallet.entity.MovementWallet;
import com.nttdata.movementwallet.repository.MovementWalletRepository;
import com.nttdata.movementwallet.service.MovementWalletService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class MovementWalletServiceImpl implements MovementWalletService {

	@Autowired
	MovementWalletRepository movementWalletRepository;

	@Override
	public Flux<MovementWallet> findAll() {
		// TODO Auto-generated method stub
		return movementWalletRepository.findAll()
				.sort((o1, o2) -> o1.getIdMovementWallet().compareTo(o2.getIdMovementWallet()));
	}

	@Override
	public Mono<MovementWallet> findById(Long idMovementWallet) {
		// TODO Auto-generated method stub
		return movementWalletRepository.findById(idMovementWallet);
	}

	@Override
	public Mono<MovementWallet> save(MovementWallet movementWallet) {
		// TODO Auto-generated method stub
		Long count = this.findAll().collect(Collectors.counting()).blockOptional().get();
		Long idMovementWallet;
		if (count != null) {
			if (count <= 0) {
				idMovementWallet = Long.valueOf(0);
			} else {
				idMovementWallet = this.findAll()
						.collect(Collectors.maxBy(Comparator.comparing(MovementWallet::getIdMovementWallet)))
						.blockOptional().get().get().getIdMovementWallet();
			}

		} else {
			idMovementWallet = Long.valueOf(0);

		}
		movementWallet.setIdMovementWallet(idMovementWallet + 1);
		movementWallet.setDateModified(Calendar.getInstance().getTime());
		return movementWalletRepository.insert(movementWallet);
	}

	@Override
	public Mono<MovementWallet> update(MovementWallet movementWallet) {
		// TODO Auto-generated method stub
		return movementWalletRepository.save(movementWallet);
	}

	@Override
	public Mono<Void> delete(Long idMovementWallet) {
		// TODO Auto-generated method stub
		return movementWalletRepository.deleteById(idMovementWallet);
	}

}
