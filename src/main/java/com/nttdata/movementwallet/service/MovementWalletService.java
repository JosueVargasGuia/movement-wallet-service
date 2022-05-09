package com.nttdata.movementwallet.service;

 

import com.nttdata.movementwallet.entity.MovementWallet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementWalletService {
	/** Retorna todos los moviemiento de  wallet registrados */
	Flux<MovementWallet> findAll();

	/** Busqueda de un wallet por idMovementWallet */
	Mono<MovementWallet> findById(Long idMovementWallet);

	/** Registra un nuevo moviemiento de wallet */
	Mono<MovementWallet> save(MovementWallet movementWallet);

	/** Actualiza un moviemiento de  wallet */
	Mono<MovementWallet> update(MovementWallet movementWallet);

	/** Eliminacion fisica de un moviemiento de  wallet */
	Mono<Void> delete(Long idMovementWallet);
}
