package com.example.patitas_backend_a.service.impl;

import com.example.patitas_backend_a.dto.CloseRequest;
import com.example.patitas_backend_a.dto.LoginReqDTO;
import com.example.patitas_backend_a.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;

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

  @Override
  public void cerrarSesion(CloseRequest request) throws IOException {

    Resource resource = resourceLoader.getResource("classpath:cierres.txt");

    try(BufferedWriter bw = new BufferedWriter(new FileWriter(resource.getFile(), true))) {
      bw.write("PROBANDO PROBANDO");
      bw.close();
      System.out.println("asdasds");

    }catch (IOException e){
      throw new IOException(e);
    }

  }
}
