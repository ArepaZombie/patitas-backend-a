package com.example.patitas_backend_a.controller;

import com.example.patitas_backend_a.dto.LoginReqDTO;
import com.example.patitas_backend_a.dto.LoginResponseDTO;
import com.example.patitas_backend_a.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

  @Autowired
  AutenticacionService autenticacionService;

  @PostMapping("/login")
  public LoginResponseDTO login(@RequestBody LoginReqDTO request){

    try {
      String[] datoUsuario = autenticacionService.validarUsuario(request);
      if(datoUsuario ==null){
        return new LoginResponseDTO("01","Error: Usuario no encontrado","","");
      }
      //Funciona
      return new LoginResponseDTO("00","",datoUsuario[0],datoUsuario[1]);

    } catch (IOException e) {
      return new LoginResponseDTO("99","Error: Ocurrió un problema","","");
    }

  }

}
