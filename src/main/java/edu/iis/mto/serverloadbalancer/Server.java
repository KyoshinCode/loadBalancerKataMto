package edu.iis.mto.serverloadbalancer;

/**
 * Created by kyo on 26.06.2017.
 */
public class Server {
    public double currentLoadPercentage;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public int capacity;

    public boolean contains(Vm theVm) {
        return true;
    }
}
