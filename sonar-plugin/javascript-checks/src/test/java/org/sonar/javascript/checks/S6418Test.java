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

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

class S6418Test {

  @Test
  void configurations() {
    S6418 check = new S6418();
    // default configuration
    String defaultConfigAsString = new Gson().toJson(check.configurations());
    assertThat(defaultConfigAsString).isEqualTo(
      "[{\"secretWords\":\"api[_.-]?key,auth,credential,secret,token\",\"randomnessSensibility\":5.0}]"
    );
  }
}
