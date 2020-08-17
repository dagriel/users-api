package com.picpay.users.controllers;

import com.picpay.users.models.UserModel;
import com.picpay.users.repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value="Realiza uma listagem de usuários baseada em filtros. Se um filtro não for especificado, deve listar os usuários odernados por nome.")
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> listUsersUsingGET(){
        List<UserModel> usersList = userRepository.findAllByFullName();
        if(usersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            for(UserModel user : usersList) {
                long id = user.getId();
                user.add(linkTo(methodOn(UserController.class).getUserUsingGET(id)).withSelfRel());
            }
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }
    }

    @ApiOperation(value="Realiza o cadastro de novos usuários no sistema.")
    @PostMapping("/users")
    public ResponseEntity<UserModel> createUserUsingPOST(@RequestBody @Valid UserModel user) {
        return new ResponseEntity<UserModel>(userRepository.save(user), HttpStatus.CREATED);
    }

    @ApiOperation(value="Retorna dados detalhados de um usuário.")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUserUsingGET(@PathVariable(value="id") long id){
        Optional<UserModel> userO = userRepository.findById(id);
        if(!userO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            userO.get().add(linkTo(methodOn(UserController.class).listUsersUsingGET()).withRel("Lista de Usuários"));
            return new ResponseEntity<UserModel>(userO.get(), HttpStatus.OK);
        }
    }

}
