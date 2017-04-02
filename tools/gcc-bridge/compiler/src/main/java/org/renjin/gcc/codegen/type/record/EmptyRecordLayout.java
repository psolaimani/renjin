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
package org.renjin.gcc.codegen.type.record;

import org.renjin.gcc.codegen.MethodGenerator;
import org.renjin.gcc.codegen.expr.GExpr;
import org.renjin.gcc.codegen.type.TypeOracle;
import org.renjin.gcc.codegen.type.TypeStrategy;
import org.renjin.gcc.codegen.var.LocalVarAllocator;
import org.renjin.repackaged.asm.Type;

import java.io.File;
import java.io.IOException;

/**
 * Layout for a record that has no fields.
 */
public class EmptyRecordLayout implements RecordLayout {

  private static final Type TYPE = Type.getType(Object.class);

  @Override
  public Type getType() {
    return TYPE;
  }

  @Override
  public void linkFields(TypeOracle typeOracle) {
    // NOOP
  }

  @Override
  public void writeClassFiles(File outputDir) throws IOException {
    // NOOP
  }

  @Override
  public GExpr memberOf(MethodGenerator mv, RecordValue instance, int offset, int size, TypeStrategy fieldTypeStrategy) {
    throw new UnsupportedOperationException("Empty record has no fields.");
  }

  @Override
  public RecordValue clone(MethodGenerator mv, RecordValue recordValue) {

    // Simply create a new instance as the instance holds no data

    LocalVarAllocator.LocalVar copy = mv.getLocalVarAllocator().reserve(TYPE);

    mv.anew(TYPE);
    mv.dup();
    mv.invokeconstructor(TYPE);
    copy.store(mv);

    return new RecordValue(copy);
  }
}
