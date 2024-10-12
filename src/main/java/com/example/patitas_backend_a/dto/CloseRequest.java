package com.example.patitas_backend_a.dto;

public record CloseRequest(
  String tipoDocumento,
  String numeroDocumento
) {
}
