package com.example.patitas_backend_a.service.impl;

import com.example.patitas_backend_a.dto.CloseRequest;
import com.example.patitas_backend_a.dto.LoginReqDTO;
import com.example.patitas_backend_a.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;

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

    Resource cierres = resourceLoader.getResource("classpath:cierres.txt");
    Resource usuarios = resourceLoader.getResource("classpath:usuarios.txt");
    String[] datosUsuario = null;

    //Buscamos la info del usuario con su correo electronico
    try(BufferedReader br = new BufferedReader(new FileReader(usuarios.getFile()))) {
      String linea;
      while ((linea = br.readLine()) != null) {

        String[] datos = linea.split(";");
        if (request.email().equals(datos[4])
        ) {
          datosUsuario = new String[2];

          datosUsuario[0] = datos[0];
          datosUsuario[1] = datos[1];
          break;
        }
      }
    }catch (IOException e){
        throw new IOException(e);
    }

    //Ahora lo registramos
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(cierres.getFile(), true))) {
      if(datosUsuario != null){
        String registro ="Sesión cerrada: "+datosUsuario[0]+";"+datosUsuario[1]+";"+LocalDate.now();
        System.out.println(registro);
        bw.write(registro);
      }

    }catch (IOException e){
      throw new IOException(e);
    }

  }
}
