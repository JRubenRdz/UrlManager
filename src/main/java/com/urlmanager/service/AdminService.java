package com.urlmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.urlmanager.entity.Admin;
import com.urlmanager.entity.Roles;
import com.urlmanager.repository.AdminRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;

public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JWTUtils JWTUtils;
    
	@Transactional
	public Admin saveAdmin(Admin admin) {
		admin.setRol(Roles.ADMIN);
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return adminRepository.save(admin);
	}
	
	@Transactional
	public Admin updateAdmin(Admin adminU) {
		Admin admin = JWTUtils.userLogin();
		if (admin != null) {
			admin.setUsername(adminU.getUsername());
			admin.setPassword(adminU.getPassword());
			return adminRepository.save(admin);
		}
		return null;
	}
	
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	public Optional<Admin> getAdminById(int id) {
		return adminRepository.findById(id);
	}

	public Optional<Admin> findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}
	
	
	@Transactional
	public boolean deleteAdmin() {
		Admin admin = JWTUtils.userLogin();
		if (admin != null) {
			adminRepository.deleteById(admin.getId());
			return true;
		}
		return false;
	}
	
	public void adminPorDefecto() {
		if (this.getAllAdmins().size() <= 0) {
			Admin defaultAdmin = new Admin();
			defaultAdmin.setUsername("admin");
			defaultAdmin.setPassword(passwordEncoder.encode("1234"));
			defaultAdmin.setRol(Roles.ADMIN);

			System.out.println("Usuario Admin creado por defecto");
			adminRepository.save(defaultAdmin);
		}
	}
	
}