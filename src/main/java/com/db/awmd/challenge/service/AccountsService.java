package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.MoneyTransferDTO;
import com.db.awmd.challenge.exception.AccountBalanceInSufficientException;
import com.db.awmd.challenge.exception.TransferAmountNegativeException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.util.Constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	//@Autowired
	//NotificationService notificationService;
	
	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	public String transferMoney(MoneyTransferDTO moneyTransfer) {

		Account fromAccount = getAccount(moneyTransfer.getAccountFromId());
		Account toAccount = getAccount(moneyTransfer.getAccountToId());

		BigDecimal balanceInFromAccount = fromAccount.getBalance();
		BigDecimal amountToTransfer = moneyTransfer.getAmount();

		if (amountToTransfer.compareTo(BigDecimal.ZERO) < 0) {
			throw new TransferAmountNegativeException(Constant.TRANSFERAMOUNTNEGATIVE); 																										// file
		}
		
		if (amountToTransfer.compareTo(balanceInFromAccount) > 0) {
			throw new AccountBalanceInSufficientException(Constant.ACCOUNTBALANCEINSUFFICIENT); 																										// file
		}
		
		try {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					fromAccount.setBalance(fromAccount.getBalance().subtract(amountToTransfer));
					toAccount.setBalance(toAccount.getBalance().add(amountToTransfer));
					// notificationService.notifyAboutTransfer(toAccount, "Amount " + amountToTransfer +"transfered in account " );
				}
			}
		} catch (Exception exception) {
			log.error("Exception occured {}", exception.getMessage());
			return "Error occurred while transfering amount";
		}

		return "success";
	}
}
