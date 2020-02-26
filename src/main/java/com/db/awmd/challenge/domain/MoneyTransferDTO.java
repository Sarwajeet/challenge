package com.db.awmd.challenge.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MoneyTransferDTO implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@NotNull
  @NotEmpty
  private final String accountFromId;

  @NotNull
  @NotEmpty
  private final String accountToId;
  
  @NotNull
  @NotEmpty
  private BigDecimal amount;

}
