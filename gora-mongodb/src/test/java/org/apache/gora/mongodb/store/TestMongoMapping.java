/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.gora.mongodb.store;

import static org.junit.Assert.assertEquals;

import org.apache.gora.mongodb.store.MongoMapping.DocumentFieldType;
import org.junit.Test;

public class TestMongoMapping {

  // /////////////////////////////////////// TEST DOCUMENT FIELDS REGISTRATION

  /** Add several top level fields of each type */
  @Test
  public void addSeveralTopLevelDocumentFields() {
    MongoMapping mapping = new MongoMapping();
    // Add fields of type binary
    mapping.addClassField("test", "classBin1", "topLevel1",
        DocumentFieldType.BINARY.toString().toLowerCase());
    mapping.addClassField("test", "classBin2", "topLevel2",
        DocumentFieldType.BINARY.toString().toUpperCase());
    assertEquals("topLevel1", mapping.getDocumentField("classBin1"));
    assertEquals("topLevel2", mapping.getDocumentField("classBin2"));
    // Add fields of type int32
    mapping.addClassField("test", "classInt321", "topLevel3",
        DocumentFieldType.INT32.toString().toLowerCase());
    mapping.addClassField("test", "classInt322", "topLevel4",
        DocumentFieldType.INT32.toString().toUpperCase());
    assertEquals("topLevel3", mapping.getDocumentField("classInt321"));
    assertEquals("topLevel4", mapping.getDocumentField("classInt322"));
    // Add fields of type int64
    mapping.addClassField("test", "classInt641", "topLevel5",
        DocumentFieldType.INT64.toString().toLowerCase());
    mapping.addClassField("test", "classInt642", "topLevel6",
        DocumentFieldType.INT64.toString().toUpperCase());
    assertEquals("topLevel5", mapping.getDocumentField("classInt641"));
    assertEquals("topLevel6", mapping.getDocumentField("classInt642"));
    // Add fields of type double
    mapping.addClassField("test", "classDouble1", "topLevel7",
        DocumentFieldType.DOUBLE.toString().toLowerCase());
    mapping.addClassField("test", "classDouble2", "topLevel8",
        DocumentFieldType.DOUBLE.toString().toUpperCase());
    assertEquals("topLevel7", mapping.getDocumentField("classDouble1"));
    assertEquals("topLevel8", mapping.getDocumentField("classDouble2"));
    // Add fields of type string
    mapping.addClassField("test", "classString1", "topLevel9",
        DocumentFieldType.STRING.toString().toLowerCase());
    mapping.addClassField("test", "classString2", "topLevel10",
        DocumentFieldType.STRING.toString().toUpperCase());
    assertEquals("topLevel9", mapping.getDocumentField("classString1"));
    assertEquals("topLevel10", mapping.getDocumentField("classString2"));
    // Add fields of type date
    mapping.addClassField("test", "classDate1", "topLevel11",
        DocumentFieldType.DATE.toString().toLowerCase());
    mapping.addClassField("test", "classDate2", "topLevel12",
        DocumentFieldType.DATE.toString().toUpperCase());
    assertEquals("topLevel11", mapping.getDocumentField("classDate1"));
    assertEquals("topLevel12", mapping.getDocumentField("classDate2"));
    // Add fields of type list
    mapping.addClassField("test", "classList1", "topLevel13",
        DocumentFieldType.LIST.toString().toLowerCase());
    mapping.addClassField("test", "classList2", "topLevel14",
        DocumentFieldType.LIST.toString().toUpperCase());
    assertEquals("topLevel13", mapping.getDocumentField("classList1"));
    assertEquals("topLevel14", mapping.getDocumentField("classList2"));
    // Add fields of type document
    mapping.addClassField("test", "classDocument1", "topLevel15",
        DocumentFieldType.DOCUMENT.toString().toLowerCase());
    mapping.addClassField("test", "classDocument2", "topLevel16",
        DocumentFieldType.DOCUMENT.toString().toUpperCase());
    assertEquals("topLevel15", mapping.getDocumentField("classDocument1"));
    assertEquals("topLevel16", mapping.getDocumentField("classDocument2"));
  }

  /**
   * Add several fields of each type and of different levels but no conflicting
   * configurations.
   */
  @Test
  public void addSeveralDocumentFields() {
    MongoMapping mapping;

    // Add fields with already registered parent fields
    mapping = new MongoMapping();
    mapping.addClassField("test", "classField1", "top1", "document");
    mapping.addClassField("test", "classField2", "top1.level2-1", "string");
    mapping.addClassField("test", "classField3", "top1.level2-2", "int32");
    mapping.addClassField("test", "classField4", "top1.level2-3", "document");
    mapping.addClassField("test", "classField5", "top1.level2-4", "date");
    mapping.addClassField("test", "classField6", "top1.level2-3.leaf", "int64");
    assertEquals("top1", mapping.getDocumentField("classField1"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1"));
    assertEquals("top1.level2-1", mapping.getDocumentField("classField2"));
    assertEquals(DocumentFieldType.STRING,
        mapping.getDocumentFieldType("top1.level2-1"));
    assertEquals("top1.level2-2", mapping.getDocumentField("classField3"));
    assertEquals(DocumentFieldType.INT32,
        mapping.getDocumentFieldType("top1.level2-2"));
    assertEquals("top1.level2-3", mapping.getDocumentField("classField4"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1.level2-3"));
    assertEquals("top1.level2-4", mapping.getDocumentField("classField5"));
    assertEquals(DocumentFieldType.DATE,
        mapping.getDocumentFieldType("top1.level2-4"));
    assertEquals("top1.level2-3.leaf", mapping.getDocumentField("classField6"));
    assertEquals(DocumentFieldType.INT64,
        mapping.getDocumentFieldType("top1.level2-3.leaf"));

    // Add fields with not already registered parent fields
    mapping = new MongoMapping();
    mapping.addClassField("test", "classField1", "top1.l2.l3.l4", "double");
    mapping.addClassField("test", "classField2", "top2.l2", "document");
    mapping.addClassField("test", "classField3", "top2.l2.l3.l5.l6", "list");
    mapping.addClassField("test", "classField4", "top1.l2.date", "date");
    assertEquals("top1.l2.l3.l4", mapping.getDocumentField("classField1"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1.l2"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1.l2.l3"));
    assertEquals(DocumentFieldType.DOUBLE,
        mapping.getDocumentFieldType("top1.l2.l3.l4"));
    assertEquals("top2.l2", mapping.getDocumentField("classField2"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2.l2"));
    assertEquals("top2.l2.l3.l5.l6", mapping.getDocumentField("classField3"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2.l2"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2.l2.l3"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top2.l2.l3.l5"));
    assertEquals(DocumentFieldType.LIST,
        mapping.getDocumentFieldType("top2.l2.l3.l5.l6"));
    assertEquals("top1.l2.date", mapping.getDocumentField("classField4"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1"));
    assertEquals(DocumentFieldType.DOCUMENT,
        mapping.getDocumentFieldType("top1.l2"));
    assertEquals(DocumentFieldType.DATE,
        mapping.getDocumentFieldType("top1.l2.date"));
  }

  /** Add conflicting fields */
  @Test(expected = IllegalStateException.class)
  public void addConflictingFields1() {
    MongoMapping mapping = new MongoMapping();
    mapping.addClassField("test", "classFieldName", "top1", "int32");
    mapping.addClassField("test", "classFieldName", "top1.l2", "double"); // conflict
  }

  /** Add conflicting fields */
  @Test(expected = IllegalStateException.class)
  public void addConflictingFields2() {
    MongoMapping mapping = new MongoMapping();
    mapping.addClassField("test", "classFieldName", "top1", "int64");
    mapping.addClassField("test", "classFieldName", "top1", "string"); // conflict
  }

  /** Add conflicting fields */
  @Test(expected = IllegalStateException.class)
  public void addConflictingFields3() {
    MongoMapping mapping = new MongoMapping();
    mapping.addClassField("test", "classFieldName", "top1", "document");
    mapping.addClassField("test", "classFieldName", "top1.l2", "string");
    mapping.addClassField("test", "classFieldName", "top1.l2.l3", "double"); // conflict
  }

}
