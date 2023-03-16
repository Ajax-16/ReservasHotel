package com.example.reservashotel.modelos;

public class Alojamiento {

    private String tipo;

    private Integer precio;

    private Integer numNoches;
    private Integer precioTotal;

    public Alojamiento(String tipo, Integer numNoches) {
        this.tipo = tipo;
        if(setPrecioPorNoche()!=-1){
            this.precio = setPrecioPorNoche();
            this.numNoches = numNoches;
            this.precioTotal = this.precio * this.numNoches;
        }else{
            throw new RuntimeException("Error al crear el tipo de habitación");
        }
    }

    public Integer setPrecioPorNoche(){

        switch (this.tipo){

            case "Bungalow":

                return 100;

            case "Apartamento":

                return 70;

            case "Habitación":

                return 50;

            case "VIP":

                return 200;

            default:

                return -1;

        }

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getNumNoches() {
        return numNoches;
    }

    public void setNumNoches(Integer numNoches) {
        this.numNoches = numNoches;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }
}
