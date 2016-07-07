package tech.toparvion.jmint.modify;

import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * Created by Toparvion on 29.04.2016 14:51
 */
public class AfterBodyModifier implements MethodModifier {

  @Override
  public void apply(CtBehavior method, String injection) throws CannotCompileException {
    method.insertAfter(injection);
  }
}