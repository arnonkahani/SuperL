package com.Workers.JunitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.Workers.Menus.MenuManager;
import com.Workers.Objects.CallBack;

public class CallbackTest {

	@Test
	public void test() {
		String one = "1";
		
		CallBack c1 = new CallBack() {
			
			@Override
			public boolean call(MenuManager manager) {
				return one.compareTo("1") == 0;
			}
		};
		
		assertTrue(c1.call(null));
	}

}
