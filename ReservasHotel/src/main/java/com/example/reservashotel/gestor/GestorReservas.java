package com.example.reservashotel.gestor;

import com.example.reservashotel.modelos.Reserva;

import java.util.ArrayList;
import java.util.List;

public class GestorReservas {

    private List <Reserva> listaReservas;

    public GestorReservas(){

        listaReservas = new ArrayList<>();

    }

    public void insertaReserva(Reserva reserva){

        listaReservas.add(reserva);

    }

    public void eliminaReserva(Reserva reserva){

        listaReservas.remove(reserva);

    }

    public void modificaReserva(int idAReserva, Reserva nuevaReserva){

        listaReservas.set(idAReserva, nuevaReserva);

    }

    public int getIdReserva(Reserva reserva){

        return reserva.getId();

    }

}
