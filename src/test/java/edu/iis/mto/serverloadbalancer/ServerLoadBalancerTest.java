package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmsCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void serverWithoutAnyVm_staysEmpty() {
		Server theServer = a(server().withCapacity(1));

		balancing(serverListWith(theServer), emptyListOfVms());

		assertThat(theServer, hasCurrentLoadOf(0.0d));
	}

	@Test
	public void serverWithOneVm_vmFillsWholeServer() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));

		balancing(serverListWith(theServer), vmListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(100.0d));
		assertThat("server that contains the vm", theServer.contains(theVm));
	}

	@Test
	public void oneServerWithOneVm_vmDontFillWholeServer() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));

		balancing(serverListWith(theServer), vmListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(10.0d));
		assertThat("server that contains the vm", theServer.contains(theVm));
	}

	@Test
    public void oneServerWithManyVms() {
        Server theServer = a(server().withCapacity(100));
        Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));

        balancing(serverListWith(theServer), vmListWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasVmsCountOf(2));
        assertThat("server that contains the vm", theServer.contains(theFirstVm));
        assertThat("server that contains the vm", theServer.contains(theSecondVm));
    }

    @Test
	public void twoServersWithOneVm() {
		Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
		Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
		Vm theVm = a(vm().ofSize(10));

		balancing(serverListWith(moreLoadedServer, lessLoadedServer), vmListWith(theVm));
		assertThat("Less loaded server that contains the vm", lessLoadedServer.contains(theVm));
		assertThat("More loaded server that not contains the vm", !moreLoadedServer.contains(theVm));
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}

	private Vm[] vmListWith(Vm... vms) {
		return vms;
	}



	private void balancing(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Vm[] emptyListOfVms() {
		return new Vm[0];
	}

	private Server[] serverListWith(Server... servers) {
		return servers;
	}

}
