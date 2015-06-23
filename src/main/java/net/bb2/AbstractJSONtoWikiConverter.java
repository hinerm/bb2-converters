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

package net.bb2;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;

public abstract class AbstractJSONtoWikiConverter implements
	JSONtoWikiConverter
{

	public static final String DELIM = "\t";

	@Parameter
	private LogService logService;

	private Set<String> processed = new HashSet<String>();

	@Override
	public void preinit(final String outputDir) {  }

	@Override
	public void run(final String baseURL, final String outputDir) throws Exception {
		init();

		int row = 1;

		for (final String url : getURLs()) {
			String page = null;
			try {
				page = Resources.toString(new URL(baseURL + url), Charsets.UTF_8);
			}
			catch (final FileNotFoundException e) {
				// if URL not found, log and continue
				logService.error("WARNING: URL not found: " + url);
				logService.error(e);
				continue;
			}

			final JSONObject obj = new JSONObject(page);
			for (final String id : JSONObject.getNames(obj)) {
				final JSONObject jsonElement = obj.getJSONObject(id);
				if (!processed.contains(getKey(jsonElement))) {
					final Map<String, String> lines = convert(jsonElement);
					if (lines.keySet().size() > 0) write(lines, row++);
					processed.add(getKey(jsonElement));
				}
			}
		}

		cleanup();
	}

	protected abstract void init() throws Exception;

	protected abstract void cleanup() throws Exception;

	protected abstract void write(Map<String, String> lines, int row)
		throws Exception;

	/**
	 * NB: Assumes the unique identifying string is "Id".
	 * Override this method if that is not true.
	 *
	 * @return The unique identifier for the given JSON.
	 */
	protected String getKey(final JSONObject json) {
		return json.get("Id").toString();
	}

	protected String getJSON(final String key, final String url) {
		//FIXME read the JSON on the target page and hten lookup the item with the specified key..
		return null;
	}

	protected void append(final StringBuilder sb, final String entry) {
		sb.append(entry);
		sb.append(DELIM);
	}

	protected String getField(final JSONObject json, final String... ids) {
		String s;
		if (ids.length == 0 || !json.has(ids[0]) ||
			json.get(ids[0]).toString().equals("null"))
		{
			s = " ";
		}
		else {
			s = json.get(ids[0]).toString();

			for (int i = 1; i < ids.length; i++) {
				if (!json.get(ids[i]).toString().equals("null")) {
					s += ", " + json.get(ids[i]);
				}
			}
		}
		return s;
	}

	protected String getArrayField(final JSONObject json, final String id,
		final int index)
	{
		String s;
		if (!json.has(id) || json.get(id).toString().equals("null")) {
			s = " ";
		}
		else {
			s = json.getJSONArray(id).get(index).toString();
		}
		return s;
	}

}
