/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2024 SonarSource SA
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
import { rule } from './index.js';
import { NodeRuleTester } from '../../../tests/tools/testers/rule-tester.js';
import { TypeScriptRuleTester } from '../../../tests/tools/index.js';

const ruleTesterJS = new NodeRuleTester({ parserOptions: { ecmaVersion: 2018 } });
const ruleTesterTS = new TypeScriptRuleTester();

const testCases = {
  valid: [
    {
      code: `console.log(this);`,
    },
    {
      code: `
      function foo() {
        x = this.a    // OK
        var func = s => this.foo(s)   // OK
        var func1 = s => {return this.foo(s)} // OK
      }`,
    },
    {
      code: `
      var foo = function(){
        foo(this)
      }`,
    },
    {
      code: `var func = s => this.foo(s)`,
    },
    {
      code: `
      class C {
        constructor() {
          this.a = [];   // ok
        }
      
        method1(){
          this.a = [];  // ok
        }
      
        get getMethod() {
          return this.bones.length;  // ok
        }
      
        set setMethod(foo) {
          this.id = foo;  // ok
        }
      }`,
    },
  ],
  invalid: [
    {
      code: `console.log(this.prop);`,
      errors: [
        {
          message: `Remove the use of "this".`,
          line: 1,
          endLine: 1,
          column: 13,
          endColumn: 17,
        },
      ],
    },
    {
      code: `this.a = function(){}`,
      errors: 1,
    },
    {
      code: `var x = this.a()`,
      errors: 1,
    },
    {
      code: `
      if (!this.JSON) {
        this.JSON = {}  
      }`,
      errors: 2,
    },
    {
      code: `this.foo = bar;`,
      errors: [
        {
          suggestions: [
            {
              desc: 'Remove "this"',
              output: 'foo = bar;',
            },
            {
              desc: 'Replace "this" with "window" object',
              output: 'window.foo = bar;',
            },
          ],
        },
      ],
    },
    {
      code: `this.foo.bar.baz = qux;`,
      errors: [
        {
          suggestions: [
            {
              output: 'foo.bar.baz = qux;',
            },
            {
              output: 'window.foo.bar.baz = qux;',
            },
          ],
        },
      ],
    },
    {
      code: `this['f' + 'o' + 'o'] = bar;`,
      errors: [
        {
          suggestions: [],
        },
      ],
    },
  ],
};

ruleTesterJS.run('The global "this" object should not be used JavaScript', rule, testCases);
testCases.valid.push({
  code: `
  class C {
    prop = this.C
  }`,
});
testCases.valid.push({
  code: `
  const c = class C {
    prop = this.C
  }`,
});
ruleTesterTS.run('The global "this" object should not be used TypeScript', rule, testCases);
