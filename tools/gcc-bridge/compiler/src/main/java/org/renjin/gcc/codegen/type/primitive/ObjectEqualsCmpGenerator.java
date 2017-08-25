/**
 * Renjin : JVM-based interpreter for the R language for the statistical analysis
 * Copyright © 2010-2016 BeDataDriven Groep B.V. and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, a copy is available at
 * https://www.gnu.org/licenses/gpl-2.0.txt
 */
package org.renjin.gcc.codegen.type.primitive;

import org.renjin.gcc.codegen.MethodGenerator;
import org.renjin.gcc.codegen.condition.ConditionGenerator;
import org.renjin.gcc.codegen.expr.JExpr;
import org.renjin.gcc.gimple.GimpleOp;
import org.renjin.repackaged.asm.Label;
import org.renjin.repackaged.asm.Type;

public class ObjectEqualsCmpGenerator implements ConditionGenerator {

  private GimpleOp op;
  private JExpr x;
  private JExpr y;

  public ObjectEqualsCmpGenerator(GimpleOp op, JExpr x, JExpr y) {
    this.op = op;
    this.x = x;
    this.y = y;
  }

  @Override
  public void emitJump(MethodGenerator mv, Label trueLabel, Label falseLabel) {

    x.load(mv);
    y.load(mv);
    mv.invokevirtual(Object.class, "equals", Type.BOOLEAN_TYPE, Type.getType(Object.class));

    switch (op) {
      case EQ_EXPR:
        // If the two are equal, then 1 will on the stack
        // If eq zero, then jump
        mv.ifeq(falseLabel); // = 0, not equal, FALSE
        mv.goTo(trueLabel);  // = 1, equal,     TRUE
        break;

      case NE_EXPR:
        mv.ifeq(trueLabel);
        mv.goTo(falseLabel);
        break;

      default:
        throw new UnsupportedOperationException("TODO: " + op);
    }
  }
}
