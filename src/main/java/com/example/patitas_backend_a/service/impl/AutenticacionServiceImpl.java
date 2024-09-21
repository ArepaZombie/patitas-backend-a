package com.example.patitas_backend_a.service.impl;

import com.example.patitas_backend_a.dto.LoginReqDTO;
import com.example.patitas_backend_a.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

  @Autowired
  ResourceLoader resourceLoader;

  @Override
  public String[] validarUsuario(LoginReqDTO request) throws IOException {

    String[] datosUsuario = null;
    Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

    try(BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))){

      String linea;


      while ( (linea = br.readLine()) != null ){

        String[] datos = linea.split(";");
        if (request.tipoDocumento().equals(datos[0]) &&
        request.numeroDocumento().equals(datos[1]) &&
          request.password().equals(datos[2])
        ){

          datosUsuario = new String[2];

          datosUsuario[0]  = datos[3];
          datosUsuario[1]  = datos[4];
          break;
        }
      };

    }catch (IOException e){
      datosUsuario = null;
      throw new IOException(e);
    }

    return datosUsuario;
  }
}
