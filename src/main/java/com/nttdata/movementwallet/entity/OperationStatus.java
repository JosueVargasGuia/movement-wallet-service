package com.nttdata.movementwallet.entity;

/**
 * Enum para identificar el estado de la transaccion 
 * <ul>
 * <li>saldo insuficiente=insufficient balance</li>
 * <li>procesando=processing</li>
 * <li>operación exitosa=successful operation</li>
 * </ul>
 */
public enum OperationStatus {
	insufficientBalance,processing,successfulOperation;
}
