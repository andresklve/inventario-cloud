package com.andres.inventario.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ruc", length = 11, nullable = false, unique = true)
    private String ruc;
    @Column(name = "razon_social", length = 150, nullable = false)
    private String razonSocial;
    @Column(name = "contacto", length = 100)
    private String contacto;
    @Column(name = "telefono", length = 20)
    private String telefono;
    @Column(name = "email", length = 100)
    private String email;

    public Proveedor() {
    }

    public Proveedor(String ruc, String razonSocial, String contacto, String telefono, String email) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
    }

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
