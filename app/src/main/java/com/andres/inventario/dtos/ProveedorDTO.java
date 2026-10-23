package com.andres.inventario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProveedorDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "El RUC es obligatorio.")
    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos.")
    private String ruc;
    @NotBlank(message = "La razón social es obligatoria.")
    @Size(max = 150, message = "La razón social no puede superar 150 caracteres.")
    private String razonSocial;
    @Size(max = 100, message = "El contacto no puede superar 100 caracteres.")
    private String contacto;
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres.")
    private String telefono;
    @Email(message = "El email no tiene un formato válido.")
    @Size(max = 100, message = "El email no puede superar 100 caracteres.")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
