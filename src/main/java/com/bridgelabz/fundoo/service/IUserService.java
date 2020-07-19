package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegisterDto;
import com.bridgelabz.fundoo.dto.UpdatePassDto;
import com.bridgelabz.fundoo.model.User;

public interface IUserService {

	public boolean register(RegisterDto UserDto);

	public boolean isVerified(String token);

	public User login(LoginDto lDto);

	public boolean is_User_exists(String email);

	public boolean updatePassword(UpdatePassDto updatePasswordInformation, String token);

	

}
