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

package iguana.regex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RegexBuilder<T extends RegularExpression> {

    protected Set<CharRange> lookaheads = new HashSet<>();

    protected Set<CharRange> lookbehinds = new HashSet<>();

	public RegexBuilder(T symbol) {
        this.lookaheads = symbol.getLookaheads();
        this.lookbehinds = symbol.getLookbehinds();
	}

    public RegexBuilder() {}
	
    public RegexBuilder<T> addLookbehind(Char c) {
        lookbehinds.add(CharRange.from(c.getValue()));
        return this;
    }

	public RegexBuilder<T> addLookbehind(CharRange range) {
		lookbehinds.add(range);
		return this;
	}

    public RegexBuilder<T> addLookahead(Char c) {
        lookaheads.add(CharRange.from(c.getValue()));
        return this;
    }

    public RegexBuilder<T> addLookahead(CharRange range) {
		lookaheads.add(range);
		return this;
	}

    public RegexBuilder<T> setChildren(List<RegularExpression> children) {
        return this;
    }

	public abstract T build();
	
}
