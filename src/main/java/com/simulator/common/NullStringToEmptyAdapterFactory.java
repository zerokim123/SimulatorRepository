/*
 * @(#)NullStringToEmptyAdapterFactory.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */
package com.simulator.common;
import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * NullStringToEmptyAdapterFactory.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 * @param <T>
 */
public class NullStringToEmptyAdapterFactory<T>
implements TypeAdapterFactory {
    /**
     * create.
     *
     * @param gson Gson
     * @param type タイプ
     * @return TypeAdapter
     */
    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class
                && rawType != Boolean.class
                && rawType != Integer.class
                && rawType != BigDecimal.class) {
            return null;
        }

        if (rawType == String.class) {
            return (TypeAdapter<T>) new StringAdapter();
        }

        if (rawType == Boolean.class) {
            return (TypeAdapter<T>) new BooleanAdapter();
        }

        if (rawType == Integer.class) {
            return (TypeAdapter<T>) new IntegerAdapter();
        }

        if (rawType == BigDecimal.class) {
            return (TypeAdapter<T>) new BigDecimalAdapter();
        }

        return (TypeAdapter<T>) new StringAdapter();
    }

    /**
     * StringAdapter.
     */
    public static class StringAdapter extends TypeAdapter<String> {
        /**
         * read.
         *
         * @param reader JsonReader
         * @return read内容
         * @throws IOException IOException
         */
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        /**
         * write.
         *
         * @param writer JsonWriter
         * @param value 内容
         * @throws IOException IOException
         */
        public void write(JsonWriter writer, String value) throws IOException {
            /*if (value == null) {
                writer.nullValue();
                return;
            }*/
            if (value == null) {
                value = "";
            }

            writer.value(value);
        }


    }

    /**
     * BooleanAdapter.
     */
    public static class BooleanAdapter extends TypeAdapter<Boolean> {
        /**
         * read.
         *
         * @param reader JsonReader
         * @return read内容
         * @throws IOException IOException
         */
        public Boolean read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return false;
            }
            return reader.nextBoolean();
        }

        /**
         * write.
         *
         * @param writer JsonWriter
         * @param value 内容
         * @throws IOException IOException
         */
        public void write(JsonWriter writer, Boolean value) throws IOException {
            /*if (value == null) {
                writer.nullValue();
                return;
            }*/
            if (value == null) {
                value = false;
            }

            writer.value(value);
        }

    }

    /**
     * IntegerAdapter.
     */
    public static class IntegerAdapter extends TypeAdapter<Integer> {
        /**
         * read.
         *
         * @param reader JsonReader
         * @return read内容
         * @throws IOException IOException
         */
        public Integer read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return 0;
            }
            return reader.nextInt();
        }

        /**
         * write.
         *
         * @param writer JsonWriter
         * @param value 内容
         * @throws IOException IOException
         */
        public void write(JsonWriter writer, Integer value) throws IOException {
            /*if (value == null) {
                writer.nullValue();
                return;
            }*/
            if (value == null) {
                value = 0;
            }

            writer.value(value);
        }
    }

    /**
     * BigDecimalAdapter
     */
    public static class BigDecimalAdapter extends TypeAdapter<BigDecimal> {
        /**
         * read.
         *
         * @param reader JsonReader
         * @return read内容
         * @throws IOException IOException
         */
        public BigDecimal read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return new BigDecimal(0);
            }
            return new BigDecimal(0);
        }

        /**
         * write.
         *
         * @param writer JsonWriter
         * @param value 内容
         * @throws IOException IOException
         */
        public void write(JsonWriter writer, BigDecimal value)
                throws IOException {
            /*if (value == null) {
                writer.nullValue();
                return;
            }*/
            if (value == null) {
                value = new BigDecimal(0);
            }

            writer.value(value);
        }
    }
}
