package com.example.reservashotel.modelos;

import java.util.Objects;

public class Reserva {

    private Integer id;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private Alojamiento alojamiento;

    public Reserva(String nombre, String apellido1, String apellido2, Alojamiento alojamiento){

        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.alojamiento = alojamiento;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id) && Objects.equals(nombre, reserva.nombre) && Objects.equals(apellido1, reserva.apellido1) && Objects.equals(apellido2, reserva.apellido2) && Objects.equals(alojamiento, reserva.alojamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido1, apellido2, alojamiento);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", alojamiento=" + alojamiento +
                '}';
    }
}
