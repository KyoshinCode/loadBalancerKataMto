package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyo on 26.06.2017.
 */
public class Server {
    public static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;

    List<Vm> vms = new ArrayList<Vm>();
    public Server(int capacity) {
        this.capacity = capacity;
    }

    public int capacity;

    public boolean contains(Vm theVm) {

        return vms.contains(theVm);
    }

    public void addVm(Vm vm) {
        currentLoadPercentage += (double)vm.size / (double) capacity * MAXIMUM_LOAD;
        this.vms.add(vm);
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return currentLoadPercentage + ((double) vm.size / (double) this.capacity * MAXIMUM_LOAD) <= MAXIMUM_LOAD;
    }
}
