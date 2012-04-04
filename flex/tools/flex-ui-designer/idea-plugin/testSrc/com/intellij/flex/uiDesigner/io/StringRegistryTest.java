package com.intellij.flex.uiDesigner.io;

import org.hamcrest.collection.IsArray;
import org.junit.Test;

import static com.intellij.flex.uiDesigner.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StringRegistryTest {
  @SuppressWarnings({"unchecked"})
  @Test
  public void rollback() {
    StringRegistry stringRegistry = new StringRegistry();
    StringRegistry.StringWriter stringWriter = new StringRegistry.StringWriter(stringRegistry);
    stringWriter.startChange();

    stringWriter.getReference("test");
    stringWriter.getReference("test2");
    stringWriter.getReference("test3");

    stringWriter.commit();
    
    final IsArray<String> array = array(equalTo("test"), equalTo("test2"), equalTo("test3"));
    assertThat(stringRegistry.toArray(), array);
    assertThat(stringWriter.size(), equalTo(1));
    
    stringWriter.startChange();
    
    stringWriter.getReference("newTest");
    stringWriter.getReference("test2");
    stringWriter.getReference("newTest2");
    
    stringWriter.rollback();
    
    assertThat(stringRegistry.toArray(), array);
    assertThat(stringWriter.size(), equalTo(1));
  }
  
  @Test
  public void rollback2() {
    StringRegistry stringRegistry = new StringRegistry();
    StringRegistry.StringWriter stringWriter = new StringRegistry.StringWriter(stringRegistry);
    stringWriter.startChange();

    stringWriter.getReference("test");
    stringWriter.getReference("test2");
    stringWriter.getReference("test3");

    stringWriter.rollback();
    
    assertThat(stringRegistry.toArray(), emptyArray());
    assertThat(stringWriter.size(), equalTo(1));
  }
}