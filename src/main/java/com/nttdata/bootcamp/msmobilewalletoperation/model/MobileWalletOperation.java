package com.nttdata.bootcamp.msmobilewalletoperation.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileWalletOperation {
	
	@Id
	private String id;
	private String idaccount;
	private String typeAction;  // tipo de operacion ABONO, RETIRO
	private Double amount;
	private String idaccountOriDest;
	
}
