/*
 * #%L
 * Blood Brothers 2 Converters.
 * %%
 * Copyright (C) 2015 Blood Brothers 2 Wiki Members
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.bb2.converter;

import java.util.HashMap;
import java.util.Map;

import net.bb2.JSONtoWikiConverter;

import org.json.JSONObject;
import org.scijava.plugin.Plugin;

/**
 * Sample class to use as a starting point when implementing new
 * {@link JSONtoWikiConverter}s.
 */
@Plugin(type = JSONtoWikiConverter.class)
public class ConverterTemplate extends XLSWikiConverter {

	@Override
	public String[] getURLs() {
		return new String[] {
		// TODO
		// Add URLs of interest for this plugin.
		// Typically thsese will be pages from http://www.bloodbrothers2.info/3816/
		};
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();

		// TODO
		// It's helpful to define class constants for each sheet you will create.
		// Then call headers.add(<SHEET NAME>,<SHEET HEADERS>) here for each sheet

		return headers;
	}

	@Override
	public Map<String, String> convert(final JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();

		// TODO
		// Here is where you need to actually convert a JSON object to a
		// line in each sheet.
		// The protected append(), getField() and getArrayField() methods can be
		// helpful here. In many cases, the JSON field will simply be directly
		// appended. In more complicated cases, additional formatting will be
		// desired.

		return lines;
	}
}
