/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.javascript.model.expression;

import com.google.common.collect.Iterators;
import org.junit.Test;
import org.sonar.javascript.api.EcmaScriptPunctuator;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.javascript.model.interfaces.Tree.Kind;
import org.sonar.javascript.model.interfaces.expression.ArrayLiteralTree;

import static org.fest.assertions.Assertions.assertThat;

public class ArrayLiteralTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void empty() throws Exception {
    ArrayLiteralTree tree = parse("[ ];", Kind.ARRAY_LITERAL);

    assertThat(tree.is(Kind.ARRAY_LITERAL)).isTrue();
    assertThat(tree.openBracket().text()).isEqualTo(EcmaScriptPunctuator.LBRACKET.getValue());
    assertThat(tree.elements().isEmpty());
    assertThat(tree.elements().getSeparators().isEmpty());
    assertThat(tree.closeBracket().text()).isEqualTo(EcmaScriptPunctuator.RBRACKET.getValue());
  }

  @Test
  public void with_trailing_comma_at_the_end() throws Exception {
    ArrayLiteralTree tree = parse("[ a, a , ];", Kind.ARRAY_LITERAL);

    assertThat(tree.elements().size()).isEqualTo(2);
    assertThat(Iterators.getLast(tree.elements().iterator()).isNot(Kind.UNDEFINED));
    assertThat(tree.elements().getSeparators().size()).isEqualTo(2);
  }

  @Test
  public void with_elided_elements_at_the_end() throws Exception {
    // One elided element
    ArrayLiteralTree tree1 = parse("[ ,a, , ];", Kind.ARRAY_LITERAL);

    assertThat(tree1.elements().size()).isEqualTo(3);
    assertThat(tree1.elements().get(0).is(Kind.UNDEFINED)).isTrue();
    assertThat(tree1.elements().getSeparators().size()).isEqualTo(3);

    // Two elided element
    ArrayLiteralTree tree2 = parse("[ ,a, , ,];", Kind.ARRAY_LITERAL);

    assertThat(tree2.elements().size()).isEqualTo(4);
    assertThat(tree2.elements().get(0).is(Kind.UNDEFINED)).isTrue();
    assertThat(tree2.elements().getSeparators().size()).isEqualTo(4);
  }

  @Test
  public void with_elided_elements_in_the_middle() throws Exception {
    // One elided element
    ArrayLiteralTree tree1 = parse("[ a, ,a ];", Kind.ARRAY_LITERAL);

    assertThat(tree1.elements().size()).isEqualTo(3);
    assertThat(tree1.elements().get(1).is(Kind.UNDEFINED)).isTrue();
    assertThat(tree1.elements().getSeparators().size()).isEqualTo(2);

    // One elided element
    ArrayLiteralTree tree2 = parse("[ a, , ,a ];", Kind.ARRAY_LITERAL);

    assertThat(tree2.elements().size()).isEqualTo(4);
    assertThat(tree2.elements().get(1).is(Kind.UNDEFINED));
    assertThat(tree2.elements().get(2).is(Kind.UNDEFINED));
    assertThat(tree2.elements().getSeparators().size()).isEqualTo(3);
  }
}
