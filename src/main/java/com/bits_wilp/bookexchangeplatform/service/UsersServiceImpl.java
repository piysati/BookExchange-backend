package com.bits_wilp.bookexchangeplatform.service;

import com.bits_wilp.bookexchangeplatform.dto.LoginDTO;
import com.bits_wilp.bookexchangeplatform.dto.UserDTO;
import com.bits_wilp.bookexchangeplatform.entity.Users;
import com.bits_wilp.bookexchangeplatform.exceptions.NoRecordFoundException;
import com.bits_wilp.bookexchangeplatform.exceptions.UserAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserByEmail(String email) throws NoRecordFoundException {

        if (email.contains("@") && email.endsWith(".com")){
            Users user = this.userRepo.findByEmail(email).orElseThrow(() ->
                    new NoRecordFoundException("User with this email " + email + " not found"));

            System.out.println(user.getName());
            return entityToDTO(user);
        }
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Users user = dtoToEntity(userDTO);
        if (this.userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException("Username already taken.");
        }
        Users savedUser = this.userRepo.save(user);
        return entityToDTO(savedUser);
    }

    @Override
    public void loginUser(LoginDTO loginDTO) {
        Users user = this.userRepo.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        Customer customer = customerRepository.findByEmail(loginDTO.getEmail());
        if (user == null || loginDTO.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        // Generate token or return success message

    }

    private UserDTO entityToDTO(Users user){
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    private Users dtoToEntity(UserDTO userdto){
        Users user = this.modelMapper.map(userdto, Users.class);
        return user;
    }
}
