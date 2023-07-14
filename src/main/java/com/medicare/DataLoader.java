package com.medicare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.medicare.model.Admin;
import com.medicare.service.IAdminService;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	IAdminService adminService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Admin> allAdmins = this.adminService.fetchAdminList();
		if (allAdmins == null || allAdmins.isEmpty()) {
			Admin firstAdmin = new Admin(0L,"admin@medicare.com","Administrator","","9876543210","Medicare@2023");
			this.adminService.saveAdmin(firstAdmin);
		}
	}

}
