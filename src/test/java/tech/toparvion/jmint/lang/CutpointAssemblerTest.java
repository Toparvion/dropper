package tech.toparvion.jmint.lang;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import tech.toparvion.jmint.lang.gen.JavadocLexer;
import tech.toparvion.jmint.lang.gen.JavadocParser;
import tech.toparvion.jmint.model.Cutpoint;
import tech.toparvion.jmint.model.CutpointType;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Toparvion
 */
public class CutpointAssemblerTest {
  private static final Log log = LogFactory.getLog(CutpointAssemblerTest.class);

  @Test
  public void cutpoint1IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_1.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 1: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.BEFORE, cutpoint.getType());
  }

  @Test
  public void cutpoint2IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_2.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 2: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.CATCH, cutpoint.getType());
  }

  @Test
  public void cutpoint3IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_3.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 3: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.INSTEAD, cutpoint.getType());
  }

  @Test
  public void cutpoint4IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_4.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 4: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.AFTER, cutpoint.getType());
  }

  @Test
  public void cutpoint5IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_5.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 5: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.DEFAULT_CUTPOINT_TYPE, cutpoint.getType());
  }

  @Test
  public void cutpoint6IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_6.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 6: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.IGNORE, cutpoint.getType());
  }

  @Test
  public void cutpoint7IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_7.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("CutpointType 7: '%s'", cutpoint.getType()));
    assertEquals(CutpointType.IGNORE, cutpoint.getType());
  }

  @Test
  public void cutpoint8IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_8.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("Cutpoint 8: '%s'", cutpoint));
    assertEquals(CutpointType.AFTER, cutpoint.getType());
    assertNotNull(cutpoint.getAuxParams());
    assertEquals(1, cutpoint.getAuxParams().length);
    assertEquals("AS_FINALLY", cutpoint.getAuxParams()[0]);
  }

  @Test
  public void cutpoint9IsRecognizedCorrectly() throws Exception {
    CutpointAssembler cutpointAssembler = loadJavadoc("src/test/resources/Cutpoint_9.javadoc");
    Cutpoint cutpoint = cutpointAssembler.getCutpoint();
    log.info(String.format("Cutpoint 9: '%s'", cutpoint));
    assertEquals(CutpointType.AFTER, cutpoint.getType());
    assertNotNull(cutpoint.getAuxParams());
    assertEquals(2, cutpoint.getAuxParams().length);
    assertEquals("as", cutpoint.getAuxParams()[0]);
    assertEquals("finally", cutpoint.getAuxParams()[1]);
  }

  private CutpointAssembler loadJavadoc(String javadocPath) throws IOException {
    ANTLRFileStream fileStream = new ANTLRFileStream(javadocPath);
    JavadocLexer lexer = new JavadocLexer(fileStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    JavadocParser parser = new JavadocParser(tokenStream);
    ParseTree tree = parser.documentation();

    CutpointAssembler assembler = new CutpointAssembler();
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(assembler, tree);

    return assembler;
  }

}