package com.github.leertaken.leertaak3.opdracht9;

import org.junit.*;

import com.github.leertaken.leertaak3.opdracht9.solvers.BottomUpSolver;
import com.github.leertaken.leertaak3.opdracht9.solvers.RecursiveSolver;
import com.github.leertaken.leertaak3.opdracht9.solvers.TopDownSolver;
//import static org.junit.Assert.*;
import static org.junit.Assert.*;
//import org.junit.Assert;


public class DynamischProgrammerenTest {
	int[] numbers = null;
	int sum;
	Solver solver;
	
	@Test
	public void testRecursive(){
		solver = new RecursiveSolver();
		doTest();
		
	}
	
	@Test
	public void testBottomUp(){
		solver = new BottomUpSolver();
		doTest();
	}
	
	@Test
	public void testTopDown(){
		solver = new TopDownSolver();
		doTest();
	}
	
	private void doTest(){
		// 3+5+9=17
		assertTrue(solver.solve(new int[]{3, 5, 7, 9, 11}, 17));
		// Lukt niet
		assertFalse(solver.solve(new int[]{2, 4}, 5));
		// E�n te weinig
		assertFalse(solver.solve(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 31));
		// Precies goed
		assertTrue(solver.solve(new int[]{1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 31));
		//Mich test Midden 2+5 = 7 (recursie alleen)
		//assertTrue(solver.solve(new int[]{1,2,5,10},7));
	}
}
