/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import { CssAnalysisInput, CssAnalysisOutput } from './analysis.js';
import { linter } from '../linter/wrapper.js';
import { createStylelintConfig } from '../linter/config.js';

/**
 * Analyzes a CSS analysis input
 *
 * Analyzing a CSS analysis input is rather straighforward. All that is needed
 * is to create a Stylelint configuration based on the rules from the active
 * quality profile and uses this configuration to linter the input file.
 *
 * @param input the CSS analysis input to analyze
 * @returns a promise of the CSS analysis output
 */
export async function analyzeCSS(input: CssAnalysisInput): Promise<CssAnalysisOutput> {
  const { filePath, fileContent: code, rules } = input;
  const config = createStylelintConfig(rules);
  const sanitizedCode = code.replace(/[\u2000-\u200F]/g, ' ');

  const options = {
    code: sanitizedCode,
    codeFilename: filePath,
    config,
  };
  return linter.lint(filePath, options);
}
