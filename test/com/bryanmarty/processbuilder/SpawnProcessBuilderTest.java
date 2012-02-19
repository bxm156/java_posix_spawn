package com.bryanmarty.processbuilder;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.List;
import java.util.LinkedList;
import java.util.Map.Entry;


public class SpawnProcessBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEnvironment() {
		SpawnProcessBuilder pb = new SpawnProcessBuilder("ls");
		assertNotNull(pb.environment());
		assertEquals(pb.environment(),System.getenv());
		pb.environment().put("Custom1","Test");
		assertFalse(pb.environment().equals(System.getenv()));
		assertEquals("Test",pb.environment().get("Custom1"));
		
		LinkedList<String> commands = new LinkedList<String>();
		commands.add("ls");
		pb = new SpawnProcessBuilder(commands);
		assertNotNull(pb.environment());
		assertEquals(pb.environment(),System.getenv());
		pb.environment().put("Custom1","Test");
		assertFalse(pb.environment().equals(System.getenv()));
		assertEquals("Test",pb.environment().get("Custom1"));
	}

}
