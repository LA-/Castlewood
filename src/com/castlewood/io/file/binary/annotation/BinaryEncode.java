package com.castlewood.io.file.binary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.castlewood.io.file.binary.Binary;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BinaryEncode
{

	public int opcode();

	public Binary type();

}
