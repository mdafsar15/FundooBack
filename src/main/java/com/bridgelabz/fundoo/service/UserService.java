package com.bridgelabz.fundoo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.configuration.RabbitMQSender;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegisterDto;
import com.bridgelabz.fundoo.dto.UpdatePassDto;
import com.bridgelabz.fundoo.exception.InvalidCredentialsException;
import com.bridgelabz.fundoo.exception.UserDoesNotExistException;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.response.MailObject;
import com.bridgelabz.fundoo.util.EmailSender;
//import com.bridgelabz.fundoo.util.EmailSender;
import com.bridgelabz.fundoo.util.JwtGenerator;
import com.bridgelabz.fundoo.util.Util;

@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private IUserRepository urepo;

	@Autowired
	private Environment environment;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	@Autowired
	private MailObject mailobject;

	@Override
	public boolean register(RegisterDto UserDto) {

		User u1 = urepo.getUser(UserDto.getEmail());

		if (u1 != null) {
			throw new UserDoesNotExistException(environment.getProperty("status.register.already.exists"));
		}
		if (!UserDto.getPassword().equals(UserDto.getReTypePassword())) {
			throw new UserDoesNotExistException(environment.getProperty("status.register.incorrectpassword"));
		}

		User newU = new User();

		BeanUtils.copyProperties(UserDto, newU);

		newU.setPassword(pe.encode(newU.getPassword()));
		newU.setVerified(false);

		urepo.save(newU);

		String emailBodyContentLink = Util.createLink("http://localhost:4200/user/verification",
				JwtGenerator.generateToken(newU.getId()));
//		 rabbitmq
		mailobject.setEmail(newU.getEmail());
		mailobject.setMessage("registration link " + emailBodyContentLink);
		mailobject.setSubject("verification");

		rabbitMQSender.send(mailobject);

//		emailobj.sendMail(newU.getEmail(), "registration link", emailBodyContentLink);

		return true;

	}

	@Override
	public boolean isVerified(String token) {
		urepo.verify(JwtGenerator.decodeToken(token));
		return true;

	}

	@Override
	public User login(LoginDto Ldto) {
		User inputUser = urepo.getUser(Ldto.getEmail());
		System.out.println("user which was fetched from urepo" + inputUser);
		// valid user
		if (inputUser != null) {
			// send for verification if not verified
			if (pe.matches(Ldto.getPassword(), inputUser.getPassword())) {

				if (inputUser.isVerified()) {
					return inputUser;
				}

				String emailBodyLink = Util.createLink("http://localhost:4200" + "user/verification",
						JwtGenerator.generateToken(inputUser.getId()));
				mailobject.setEmail(inputUser.getEmail());
				mailobject.setMessage("Registration verification link " + emailBodyLink);
				mailobject.setSubject("verification");

				rabbitMQSender.send(mailobject);

//			emailobj.sendMail(inputUser.getEmail(), "Registration Verification link", emailBodyLink);
				return inputUser;
			}
			throw new InvalidCredentialsException("Opps...Invalid Credentials!", 400);
		}
		// not registered
		return null;
	}

	@Override
	public boolean is_User_exists(String email) {
		System.out.println("Inside is_user exists");
		User user = urepo.getUser(email);
		if (user != null) {
			System.out.println("After urepo call" + user);

			if (user.isVerified()) {

				String mail = Util.createLink("http://localhost:4200" + "/user/forgotPassword",
						JwtGenerator.generateToken(user.getId()));

				mailobject.setEmail(user.getEmail());
				mailobject.setMessage("Registration verification link " + mail);
				mailobject.setSubject("verification");

				rabbitMQSender.send(mailobject);

//				emailobj.sendMail(user.getEmail(), "verification", mail);
				System.out.println("After mail sent");
				return true;

			}
			System.out.println("After if statement");
			System.out.println("Inside if user.isVerified()");
			return false;
		}
		return false;

	}

	@Override
	public boolean updatePassword(UpdatePassDto updatePasswordInformation, String token) {
		if (updatePasswordInformation.getPassword().equals(updatePasswordInformation.getConfirmPassword())) {
			updatePasswordInformation.setConfirmPassword(pe.encode(updatePasswordInformation.getConfirmPassword()));
			urepo.updatePassword(updatePasswordInformation, JwtGenerator.decodeToken(token));
			// sends mail after updating password

			mailobject.setEmail(updatePasswordInformation.getEmailId());
			mailobject.setMessage("Password updated successfully!");
			mailobject.setSubject(postUpdatePassMail(updatePasswordInformation));

			rabbitMQSender.send(mailobject);

//			emailobj.sendMail(updatePasswordInformation.getEmailId(), "Password updated sucessfully!",
//					postUpdatePassMail(updatePasswordInformation));
			return true;
		}
		return false;

	}

	private String postUpdatePassMail(UpdatePassDto updatePasswordInformation) {
		String passwordUpdateBodyContent = "Login Details \n" + "UserId : " + updatePasswordInformation.getEmailId()
				+ "\nPassword : " + updatePasswordInformation.getPassword();
		String loginString = "\nClick on the link to login\n";
		String loginLink = "http://localhost:" + environment.getProperty("server.port") + "/user/login";
		return passwordUpdateBodyContent + loginString + loginLink;
	}

}
