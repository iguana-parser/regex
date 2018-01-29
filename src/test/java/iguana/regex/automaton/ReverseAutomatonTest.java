/*
 * Copyright (c) 2015, Ali Afroozeh and Anastasia Izmaylova, Centrum Wiskunde & Informatica (CWI)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 */

package iguana.regex.automaton;

import iguana.utils.input.Input;
import iguana.regex.Char;
import iguana.regex.Alt;
import iguana.regex.CharRange;
import iguana.regex.RegularExpression;
import iguana.regex.Seq;
import iguana.regex.matcher.DFAMatcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReverseAutomatonTest {
	
	@Test
	public void test1() {
		Seq<Char> r = Seq.from("test");
		Automaton a = AutomatonOperations.reverse(r.getAutomaton());
		DFAMatcher matcher = new DFAMatcher(a);
		assertTrue(matcher.match(Input.fromString("tset")));
	}
	
	@Test
	public void test2() {
		RegularExpression r = Alt.from(CharRange.in('a', 'z'), CharRange.in('A', 'Z'), CharRange.in('0', '9'), Char.from('_'));
		Automaton a = AutomatonOperations.reverse(r.getAutomaton());
		DFAMatcher matcher = new DFAMatcher(a);
		assertTrue(matcher.match(Input.fromChar('a')));
		assertTrue(matcher.match(Input.fromChar('m')));
		assertTrue(matcher.match(Input.fromChar('z')));
		assertTrue(matcher.match(Input.fromChar('A')));
		assertTrue(matcher.match(Input.fromChar('M')));
		assertTrue(matcher.match(Input.fromChar('Z')));
		assertTrue(matcher.match(Input.fromChar('0')));
		assertTrue(matcher.match(Input.fromChar('9')));
		assertTrue(matcher.match(Input.fromChar('3')));
		assertTrue(matcher.match(Input.fromChar('_')));
	}

}
