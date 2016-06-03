package org.renjin.gcc.codegen.type.voidt;

import com.google.common.base.Optional;
import org.objectweb.asm.Type;
import org.renjin.gcc.codegen.expr.Expr;
import org.renjin.gcc.codegen.expr.Expressions;
import org.renjin.gcc.codegen.expr.SimpleExpr;
import org.renjin.gcc.codegen.fatptr.FatPtrExpr;
import org.renjin.gcc.codegen.fatptr.ValueFunction;

import java.util.Collections;
import java.util.List;


public class VoidPtrValueFunction implements ValueFunction {
  @Override
  public Type getValueType() {
    return Type.getType(Object.class);
  }

  @Override
  public int getElementLength() {
    return 1;
  }

  @Override
  public int getArrayElementBytes() {
    return 4;
  }

  @Override
  public Optional<SimpleExpr> getValueConstructor() {
    return Optional.absent();
  }

  @Override
  public Expr dereference(SimpleExpr array, SimpleExpr offset) {
    return Expressions.elementAt(array, offset);
  }

  @Override
  public List<SimpleExpr> toArrayValues(Expr expr) {
    FatPtrExpr fatPtrExpr = (FatPtrExpr) expr;
    return Collections.singletonList(fatPtrExpr.wrap());
  }

  @Override
  public String toString() {
    return "VoidPtr";
  }
}