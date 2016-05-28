package ru.ftc.upc.testing.dropper.lang;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import ru.ftc.upc.testing.dropper.lang.gen.DroppingJavaLexer;
import ru.ftc.upc.testing.dropper.lang.gen.DroppingJavaParser;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Toparvion
 */
public class DropperTreeListenerTest {

  @Test
  public void nestedClassesAreRecognizedFully() throws Exception {
    String dropletPath = "src/test/java/ru/ftc/upc/testing/dropper/lang/droplets/MultiNestedClasses.java";
    TargetsMap targetsMap = loadDroplet(dropletPath);
    String expected = "MultiNestedClasses -> {\n" +
            "\tTargetMethod{name='method1', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 1 ;\n" +
            "\t}\n" +
            "\tTargetMethod{name='method4', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 4 ;\n" +
            "\t}\n" +
            "\tTargetMethod{name='method7', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 7 ;\n" +
            "\t}\n" +
            "}\n" +
            "MultiNestedClasses.InnerClass1 -> {\n" +
            "\tTargetMethod{name='method2', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 2 ;\n" +
            "\t}\n" +
            "\tTargetMethod{name='method3', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 3 ;\n" +
            "\t}\n" +
            "}\n" +
            "MultiNestedClasses.InnerClass2 -> {\n" +
            "\tTargetMethod{name='method5', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 5 ;\n" +
            "\t}\n" +
            "}\n" +
            "MultiNestedClasses.InnerClass2.InnerClass3 -> {\n" +
            "\tTargetMethod{name='method6', cutpoint=null, resultType=int, formalParams=(), text=\n" +
            "\t\treturn 6 ;\n" +
            "\t}\n" +
            "}\n";
    String actual = targetsMap.toString();
    assertEquals(expected, actual);
    System.out.println(actual);
  }

  @Test
  public void methodHeadersAreRecognizedFully() throws Exception {
    String dropletPath = "src/test/java/ru/ftc/upc/testing/dropper/lang/droplets/VariousMethodHeaders.java";
    TargetsMap targetsMap = loadDroplet(dropletPath);
    String expected = "VariousMethodHeaders -> {\n" +
            "\tTargetMethod{name='method1', cutpoint=null, resultType=void, formalParams=(), text=}\n" +
            "\tTargetMethod{name='method2', cutpoint=null, resultType=void, formalParams=(int a), text=}\n" +
            "\tTargetMethod{name='method3', cutpoint=null, resultType=void, formalParams=(Map map), text=}\n" +
            "\tTargetMethod{name='method4', cutpoint=null, resultType=void, formalParams=(List xs), text=}\n" +
            "\tTargetMethod{name='method5', cutpoint=null, resultType=void, formalParams=(UUID u1, Date d2), text=}\n" +
            "\tTargetMethod{name='method6', cutpoint=null, resultType=void, formalParams=(double d1, double d2), text=}\n" +
            "\tTargetMethod{name='method7', cutpoint=null, resultType=double, formalParams=(), text=\n" +
            "\t\treturn Math . random ( ) ;\n" +
            "\t}\n" +
            "\tTargetMethod{name='method8', cutpoint=null, resultType=Set, formalParams=(EventObject eo), text=\n" +
            "\t\treturn Collections . emptySet ( ) ;\n" +
            "\t}\n" +
            "}\n";
    String actual = targetsMap.toString();
    assertEquals(expected, actual);
    System.out.println(actual);
  }

  @Test
  public void methodBodiesAreRecognizedFully() throws Exception {
    String dropletPath = "src/test/java/ru/ftc/upc/testing/dropper/lang/droplets/MethodBodies.java";
    TargetsMap targetsMap = loadDroplet(dropletPath);
    String expected = "MethodBodies -> {\n" +
            "\tTargetMethod{name='method1', cutpoint=null, resultType=boolean, formalParams=(), text=\n" +
            "\t\tif ( Math . random ( ) > 0.5d ) { return true ; } else { return false ; }\n" +
            "\t}\n" +
            "}\n";
    String actual = targetsMap.toString();
    assertEquals(expected, actual);
    System.out.println(actual);
  }

  private TargetsMap loadDroplet(String dropletPath) throws IOException {
    ANTLRFileStream fileStream = new ANTLRFileStream(dropletPath);
    DroppingJavaLexer lexer = new DroppingJavaLexer(fileStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    DroppingJavaParser parser = new DroppingJavaParser(tokenStream);
    ParseTree tree = parser.compilationUnit();

    DropperTreeListener listener = new DropperTreeListener();
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(listener, tree);

    return listener.getTargetsMap();
  }
}