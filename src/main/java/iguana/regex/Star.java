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

import java.util.*;

public class Star extends AbstractRegularExpression {

    private static final long serialVersionUID = 1L;

    private final RegularExpression s;

    private final List<RegularExpression> separators;

    public static Star from(RegularExpression s) {
        return builder(s).build();
    }

    private Star(Builder builder) {
        super(builder);
        this.s = builder.s;
        this.separators = Collections.unmodifiableList(builder.separators);
    }

    private static String getName(RegularExpression s) {
        return s + "*";
    }

    @Override
    public int length() {
        return s.length();
    }

    @Override
    public <T> T accept(RegularExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isNullable() {
        return true;
    }

    @Override
    public Set<CharacterRange> getFirstSet() {
        return s.getFirstSet();
    }

    @Override
    public Set<CharacterRange> getNotFollowSet() {
        return s.getFirstSet();
    }

    public List<RegularExpression> getSeparators() {
        return separators;
    }

    @Override
    public Builder copyBuilder() {
        return new Builder(this);
    }

    public RegularExpression getSymbol() {
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Star))
            return false;

        Star other = (Star) obj;
        return s.equals(other.s) && separators.equals(other.separators);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    public static Builder builder(RegularExpression s) {
        return new Builder(s);
    }

    public static class Builder extends RegexBuilder<Star> {

        private RegularExpression s;
        private List<RegularExpression> separators = new ArrayList<>();

        public Builder(RegularExpression s) {
            super(getName(s));
            this.s = s;
        }

        public Builder(Star star) {
            super(star);
            this.s = star.s;
            this.addSeparators(star.getSeparators());
        }

        public Builder addSeparator(RegularExpression symbol) {
            separators.add(symbol);
            return this;
        }

        public Builder addSeparators(List<RegularExpression> symbols) {
            separators.addAll(symbols);
            return this;
        }

        public Builder addSeparators(RegularExpression... symbols) {
            separators.addAll(Arrays.asList(symbols));
            return this;
        }

        @Override
        public Star build() {
            return new Star(this);
        }
    }

}