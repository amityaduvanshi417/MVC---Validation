package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.demo.model.Admin;
import com.example.demo.model.Attendances;
import com.example.demo.model.Login;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.AttendancesRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@org.springframework.stereotype.Service
public class Service {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private AttendancesRepository attendancesRepository;

	public Admin AdminRegistration(Admin admin) throws Exception {
		String email = admin.getEmail();
		if(adminRepo.findByEmail(email)!=null) {
			throw new Exception("Email already exists");
		}
		String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
		admin.setPassword(hashedPassword);
		return adminRepo.save(admin);
	}

	public String admin_login(String email, String password) {
		Admin admin = adminRepo.findByEmail(email);

		if (admin!=null) {
			String stored_hash = admin.getPassword();
			if (BCrypt.checkpw(password, stored_hash)) {
				return "success";
			}
			else {
				return "Password does not match";
			}
		}
		else {
			return "Email not found";
		}
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User getUserById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
	}

	public String submitAttendance(User user, String type) {
		LocalDate today = LocalDate.now();
		boolean attendanceExists = attendancesRepository.existsByUserEmailAndCreatedat(user.getEmail(), today);

		if (attendanceExists) {
			return "Attendance already recorded for today.";
		}

		Attendances attendance = new Attendances();
		attendance.setUser(user);
		attendance.setType(type);
		attendancesRepository.save(attendance);

		return "Attendance successfully submitted!";
	}

	public List<User> searchRole(String role) {
		return userRepo.findAllByRole(role);
	}

	public List<User> findUsersByCreatedDate(LocalDate date) {
		System.out.println(date);
		List<Attendances> attendances = attendancesRepository.findByCreatedAtDate(date);

		for (Attendances attendance : attendances) {
			System.out.println(attendance.toString());
		}

		return attendances.stream()
				.map(Attendances::getUser)  // Extract User from Attendances
				.distinct()                 // Remove duplicates if needed
				.collect(Collectors.toList());
	}

	public User findById(Long id) {
		Optional<User> userOptional = userRepo.findById(id);
		return userOptional.orElse(null);
	}

	public User updateUser(Long id, User userDetails) {
		User user = getUserById(id);
		user.setName(userDetails.getName());
		user.setEmail(userDetails.getEmail());
		user.setMobile(userDetails.getMobile());
		user.setRole(userDetails.getRole());
		String hashedPassword = BCrypt.hashpw(userDetails.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);
		return userRepo.save(user);
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

//---------------------------------------------------------------------------------------------------
	public User registerUser(User user) {
	// Hash the password
	String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
	user.setPassword(hashedPassword);
	return userRepo.save(user);
}

	public String login(Login login) {
		String email = login.getEmail();
		String password = login.getPassword();

		User user = userRepo.findByEmail(email);

		if (user!=null) {
			String stored_hash = user.getPassword();
			if (BCrypt.checkpw(password, stored_hash)) {
				return "success";
			}
			else {
				return "Password does not match";
			}
		}
		else {
			return "Email not found";
		}

	}

	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

}

