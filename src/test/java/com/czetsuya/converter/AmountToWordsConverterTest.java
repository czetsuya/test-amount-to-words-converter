package com.czetsuya.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
public class AmountToWordsConverterTest {

	private AmountToWordsConverter converter;

	@BeforeEach
	public void setup() {
		converter = new AmountToWordsConverter();
	}

	@Test
	public void testInputThousand() {
		assertEquals("one thousand DOLLARS AND one CENT", converter.convert("1000.1"));
	}

	@Test
	public void testInputMillion() {
		assertEquals("one million, two hundred fifty DOLLARS AND twenty five CENTS", converter.convert("1000250.25"));
	}

	@Test
	public void testInputBillion() {
		assertEquals("seven billion, five hundred twenty six million, eight hundred ninety two thousand, five hundred forty six DOLLARS AND ninety nine CENTS",
				converter.convert("7526892546.99"));
	}
}