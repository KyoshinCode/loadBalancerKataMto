package edu.iis.mto.serverloadbalancer;

/**
 * Created by kyo on 26.06.2017.
 */
public class Server {
    public static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public int capacity;

    public boolean contains(Vm theVm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = (double)vm.size / (double) capacity * MAXIMUM_LOAD;
    }
}
