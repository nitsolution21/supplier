package org.fintexel.supplier.controller;

import java.util.List;

import org.fintexel.supplier.procedureentity.GlobalEntity;
import org.fintexel.supplier.procedurerepo.GlobalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProceduresController {
	
	@Autowired
	private GlobalRepo globalRepo;
	
	@GetMapping("/getOpenPo")
	public List<GlobalEntity> getOpenPo() {
		return globalRepo.getOpenPo();
	}

}
