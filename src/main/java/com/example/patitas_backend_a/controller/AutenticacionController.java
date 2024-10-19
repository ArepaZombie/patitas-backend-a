package com.example.patitas_backend_a.controller;

import com.example.patitas_backend_a.dto.*;
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
      //Thread.sleep(Duration.ofSeconds(5));
      String[] datoUsuario = autenticacionService.validarUsuario(request);
      System.out.println("Resultado: "+Arrays.toString(datoUsuario));
      if(datoUsuario == null){
        return new LoginResponseDTO("01","Error: Usuario no encontrado","","");
      }
      //Funciona
      return new LoginResponseDTO("00","",datoUsuario[0],datoUsuario[1]);

    } catch (Exception e) {
      return new LoginResponseDTO("99","Error: Ocurrió un problema","","");
    }

  }

  @PostMapping("/close")
  public CloseResponse close(@RequestBody CloseRequest request){
    try{
      Thread.sleep(Duration.ofSeconds(3));
      autenticacionService.cerrarSesion(request);
      return new CloseResponse("00","");
    } catch (IOException | InterruptedException e) {
      System.out.println(e.getMessage());
      return new CloseResponse("99","Ocurrió un problema en el servidor");
    }
  }

  @PostMapping("/close_ef")
  public CloseResponseEF closeEf(@RequestBody CloseRequestEF request){
    if(request.numeroDocumento() == null || request.numeroDocumento().equals("")
    || request.tipoDocumento() == null || request.tipoDocumento().equals("")){
      return new CloseResponseEF("01","Falta información para el cierre de sesión");
    }
    try{
      Thread.sleep(Duration.ofSeconds(11));
      autenticacionService.cerrarSesionEF(request);
      return new CloseResponseEF("00","");
    } catch (IOException | InterruptedException e) {
      System.out.println(e.getMessage());
      return new CloseResponseEF("99","Ocurrió un problema en el servidor");
    }
  }

}
