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
import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

  @Autowired
  AutenticacionService autenticacionService;

  @PostMapping("/login")
  public LoginResponseDTO login(@RequestBody LoginReqDTO request){

    try {
      Thread.sleep(Duration.ofSeconds(60));
      String[] datoUsuario = autenticacionService.validarUsuario(request);
      System.out.println("Resultado: "+Arrays.toString(datoUsuario));
      if(datoUsuario ==null){
        return new LoginResponseDTO("01","Error: Usuario no encontrado","","");
      }
      //Funciona
      return new LoginResponseDTO("00","",datoUsuario[0],datoUsuario[1]);

    } catch (IOException e) {
      return new LoginResponseDTO("99","Error: Ocurrió un problema","","");
    } catch (InterruptedException e) {
      return new LoginResponseDTO("99","Error: Ocurrió un problema","","");
    }

  }

}
