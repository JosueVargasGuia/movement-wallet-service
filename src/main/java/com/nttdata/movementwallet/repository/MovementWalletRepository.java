package com.nttdata.movementwallet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.movementwallet.entity.MovementWallet;

@Repository
public interface MovementWalletRepository extends ReactiveMongoRepository<MovementWallet, Long>{

}
