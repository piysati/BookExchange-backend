package com.bits_wilp.bookexchangeplatform.service;

import com.bits_wilp.bookexchangeplatform.dto.LoginDTO;
import com.bits_wilp.bookexchangeplatform.dto.UserDTO;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;

public interface UsersService {

    UserDTO getUserByEmail(String email) throws NoRecordFoundException;

    UserDTO createUser(UserDTO userDTO);
    void loginUser(LoginDTO loginDTO);
}
