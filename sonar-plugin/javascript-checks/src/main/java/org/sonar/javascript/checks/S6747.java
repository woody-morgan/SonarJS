/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2025 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Sonar Source-Available License Version 1, as published by SonarSource SA.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the Sonar Source-Available License for more details.
 *
 * You should have received a copy of the Sonar Source-Available License
 * along with this program; if not, see https://sonarsource.com/license/ssal/
 */
package org.sonar.javascript.checks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.javascript.api.Check;
import org.sonar.plugins.javascript.api.JavaScriptRule;
import org.sonar.plugins.javascript.api.TypeScriptRule;

@JavaScriptRule
@TypeScriptRule
@Rule(key = "S6747")
public class S6747 extends Check {

  @RuleProperty(
    key = "whitelist",
    description = "Comma-separated list of property and attribute names to ignore",
    defaultValue = ""
  )
  public String whitelist = "";

  @Override
  public List<Object> configurations() {
    return Collections.singletonList(
      new Config(Arrays.asList(whitelist.split(",")).stream().map(String::trim).toList())
    );
  }

  private static class Config {

    List<String> ignore;

    Config(List<String> ignore) {
      this.ignore = ignore;
    }
  }
}
