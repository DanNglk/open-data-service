/*
 * 
 */
package org.jvalue.ods.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import org.jvalue.ods.data.generic.GenericEntity;
import org.jvalue.ods.data.generic.ListObject;
import org.jvalue.ods.data.generic.MapObject;
import org.jvalue.ods.data.objecttypes.ListObjectType;
import org.jvalue.ods.data.objecttypes.MapObjectType;
import org.jvalue.ods.data.objecttypes.ObjectType;
import org.jvalue.ods.data.valuetypes.GenericValueType;
import org.jvalue.ods.data.valuetypes.ListComplexValueType;
import org.jvalue.ods.data.valuetypes.MapComplexValueType;
import org.jvalue.ods.data.valuetypes.SimpleValueType;

/**
 * The Class SchemaManager.
 */
public class SchemaManager {
	//
	// /**
	// * Creates the json schema.
	// *
	// * @param listObjectType
	// * the schema
	// * @return the string
	// */
	// public static String createJsonSchema(ObjectType objectType) {
	// return createJsonSchema(objectType, 0);
	// }
	//
	// /**
	// * Creates the json schema.
	// *
	// * @param objectType
	// * the schema
	// * @param tabs
	// * the tabs
	// * @return the string
	// */
	// private static String createJsonSchema(ObjectType objectType, int tabs) {
	// String result = "";
	//
	// if (objectType.getClass().equals(MapComplexValueType.class)) {
	// MapComplexValueType map = (MapComplexValueType) objectType;
	// result = createJsonSchemaFromMap(map, tabs + 1);
	// } else if (objectType.getClass().equals(ListComplexValueType.class)) {
	// ListComplexValueType list = (ListComplexValueType) objectType;
	// result = createJsonSchemaFromList(list, tabs + 1);
	// } else if (objectType.getClass().equals(SimpleValueType.class)) {
	// SimpleValueType bot = (SimpleValueType) objectType;
	// if (bot.getName() == "java.lang.Boolean")
	// {
	// result = createJsonSchemaFromElementary("boolean", tabs + 1);
	// }
	// else if (bot.getName() == "Null")
	// {
	// result = createJsonSchemaFromElementary("null", tabs + 1);
	// }
	// else if (bot.getName() == "java.lang.Number")
	// {
	// result = createJsonSchemaFromElementary("number", tabs + 1);
	// }
	// else if (bot.getName() == "java.lang.String")
	// {
	// result = createJsonSchemaFromElementary("string", tabs + 1);
	// }
	//
	// }
	//
	// return result;
	// }
	//
	// /**
	// * Creates the tabs.
	// *
	// * @param tabCount
	// * the tab count
	// * @return the string
	// */
	// private static String createTabs(int tabCount) {
	// String s = "";
	//
	// for (int i = 0; i < tabCount; i++)
	// s += "\t";
	//
	// return s;
	// }
	//
	// /**
	// * Creates the json schema from elementary.
	// *
	// * @param type
	// * the type
	// * @param tabs
	// * the tabs
	// * @return the string
	// */
	// private static String createJsonSchemaFromElementary(String type, int
	// tabs) {
	// /*
	// * example: { "type" : "string" }
	// */
	// String result;
	// result = "{" + "\n";
	// result += createTabs(tabs) + "\"type\": \"" + type + "\"" + "\n";
	// result += createTabs(tabs - 1) + "}";
	// return result;
	// }
	//
	// /**
	// * Creates the json schema from list.
	// *
	// * @param listObjectType
	// * the list schema
	// * @param tabs
	// * the tabs
	// * @return the string
	// */
	// private static String createJsonSchemaFromList(ListObjectType
	// listObjectType,
	// int tabs) {
	// /*
	// * example: { "type": "array", "items": { ... } }
	// */
	// String result = "{" + "\n";
	// result += createTabs(tabs) + "\"type\": \"array\"," + "\n";
	// result += createTabs(tabs) + "\"items\": ";
	//
	// List<GenericValueType> list = listObjectType.getAttributes();
	// for (int i = 0; i < list.size(); i++) {
	// GenericValueType genericValueType = list.get(i);
	//
	// if (i != list.size() - 1) {
	// result += createJsonSchema(genericValueType, tabs) + "," + "\n";
	// } else {
	// result += createJsonSchema(genericValueType, tabs) + "\n";
	// }
	// }
	//
	// result += createTabs(tabs - 1) + "}";
	//
	// return result;
	// }
	//
	// /**
	// * Creates the json schema from map.
	// *
	// * @param mapObjectType
	// * the map schema
	// * @param tabs
	// * the tabs
	// * @return the string
	// */
	// @SuppressWarnings("unchecked")
	// private static String createJsonSchemaFromMap(MapComplexValueType
	// mapObjectType, int tabs) {
	// /*
	// * { "type": "object", "properties": { ... } }
	// */
	//
	// String result = "{" + "\n";
	// result += createTabs(tabs) + "\"type\": \"object\"," + "\n";
	// result += createTabs(tabs) + "\"properties\": {" + "\n";
	//
	// Map<String, GenericValueType> map = mapObjectType.getMap();
	// Object[] arr = map.entrySet().toArray();
	// for (int i = 0; i < arr.length; i++) {
	// Entry<String, GenericValueType> entry = (Entry<String, GenericValueType>)
	// arr[i];
	// if (i != arr.length - 1) {
	// result += createTabs(tabs + 1) + "\"" + entry.getKey() + "\": "
	// + createJsonSchema(entry.getValue(), tabs + 1) + ","
	// + "\n";
	// } else {
	// result += createTabs(tabs + 1) + "\"" + entry.getKey() + "\": "
	// + createJsonSchema(entry.getValue(), tabs + 1) + "\n";
	// }
	// }
	//
	// result += createTabs(tabs) + "}" + "\n";
	// result += createTabs(tabs - 1) + "}";
	//
	// return result;
	// }
	//
	/**
	 * Validate generic entity fits schema.
	 * 
	 * @param ge
	 *            the ge
	 * @param objectType
	 *            the db schema
	 * @return true, if successful
	 */
	public static boolean validateGenericValusFitsObjectType(GenericEntity ge,
			ObjectType objectType) {

//		if (objectType instanceof MapObjectType) {
//
//			MapObjectType mot = (MapObjectType) objectType;
//
//			if (!(ge instanceof MapObject)) {
//				return false;
//			}
//
//			MapObject mo = (MapObject) ge;
//
//			for (Entry<String, GenericValueType> e : mot.getAttributes()
//					.entrySet()) {
//
//				Serializable s = mo.getMap().get(e.getKey());
//				if (s == null) {
//					continue;
//				}
//
//				if (e.getValue() instanceof SimpleValueType) {
//					if (!s.getClass().getName()
//							.equals(((SimpleValueType) e.getValue()).getName())) {
//						return false;
//					}
//				} else if (e.getValue() instanceof MapComplexValueType) {
//					return validateGenericValusFitsValueType((GenericEntity) s,
//							e.getValue());
//				} else if (e.getValue() instanceof ListComplexValueType) {
//					return validateGenericValusFitsValueType((GenericEntity) s,
//							e.getValue());
//				}
//
//			}
//
//		} else if (objectType instanceof ListObjectType) {
//
//			ListObjectType lot = (ListObjectType) objectType;
//
//			if (!(ge instanceof ListObject)) {
//				return false;
//			}
//
//			ListObject lo = (ListObject) ge;
//
//			for (GenericValueType e : lot.getAttributes()) {
//
//				if (e instanceof SimpleValueType) {
//
//					if (!contains((SimpleValueType) e, lot.getAttributes())) {
//						return false;
//					}
//
//					if (!s.getClass().getName()
//							.equals(((SimpleValueType) e).getName())) {
//						return false;
//					}
//				} else if (e instanceof MapComplexValueType) {
//					return validateGenericValusFitsValueType((GenericEntity) s,
//							e);
//				} else if (e instanceof ListComplexValueType) {
//					return validateGenericValusFitsValueType((GenericEntity) s,
//							e);
//				}
//
//			}
//		}
//
//		return true;
//	}
//
//	private static boolean contains(SimpleValueType svt,
//			List<GenericValueType> attributes) {
//
//		boolean found = false;
//		
//		for (GenericValueType gvt : attributes) {
//			if(gvt instanceof SimpleValueType) {
//				if (((SimpleValueType) gvt).getName().equals(svt.getName())) {
//					found = true;
//				}
//			}
//		}
		
		
		return true;
	}

	private static boolean validateGenericValusFitsValueType(GenericEntity s,
			GenericValueType value) {
		return true;

	}

	// TODO

}
