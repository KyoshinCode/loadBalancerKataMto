package edu.iis.mto.serverloadbalancer;


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

	private Matcher<? super Server> hasCurrentLoadOf(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
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

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}

}
