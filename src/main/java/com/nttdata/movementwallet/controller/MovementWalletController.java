package com.nttdata.movementwallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.movementwallet.entity.MovementWallet;
import com.nttdata.movementwallet.service.MovementWalletService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/api/v1/movement-wallet")
public class MovementWalletController {
	@Autowired
	MovementWalletService movementWalletService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<MovementWallet> findAll() {
		return movementWalletService.findAll();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<MovementWallet>> save(@RequestBody MovementWallet movementWallet) {
		return movementWalletService.save(movementWallet)
				.map(_movementWallet -> ResponseEntity.ok().body(_movementWallet)).onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idMovementWallet}")
	public Mono<ResponseEntity<MovementWallet>> findById(
			@PathVariable(name = "idMovementWallet") Long idMovementWallet) {
		return movementWalletService.findById(idMovementWallet)
				.map(movementWallet -> ResponseEntity.ok().body(movementWallet)).onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<MovementWallet>> update(@RequestBody MovementWallet movementWallet) {
		Mono<MovementWallet> mono = movementWalletService.findById(movementWallet.getIdMovementWallet())
				.flatMap(objMovementWallet -> {
					return movementWalletService.update(movementWallet);
				});
		return mono.map(_movementWallet -> {
			return ResponseEntity.ok().body(_movementWallet);
		}).onErrorResume(e -> {
			log.info("Error:" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@DeleteMapping("/{idMovementWallet}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idMovementWallet") Long idMovementWallet) {
		MovementWallet movementWallet = movementWalletService.findById(idMovementWallet).blockOptional().orElse(null);
		if (movementWallet != null) {
			return movementWalletService.delete(idMovementWallet).map(r -> ResponseEntity.ok().<Void>build());
		} else {
			return Mono.just(ResponseEntity.noContent().build());
		}

	}

}
