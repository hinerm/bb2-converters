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

import net.bb2.AbstractJSONtoWikiConverter;
import net.bb2.JSONtoWikiConverter;

import org.json.JSONObject;
import org.scijava.plugin.Plugin;

@Plugin(type = JSONtoWikiConverter.class)
public class LeaderSkillsConverter extends AbstractJSONtoWikiConverter {

	private final String LEADER = "leader_skills";

	@Override
	public String[] getURLs() {
		return new String[] {
			"LeaderSkill.json", "new/LeaderSkill.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(LEADER, "Leader Skill ID\tName\tDesc\tBuffId\tMagnitude");
		return headers;
	}

	@Override
	public Map<String, String> convert(final JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();

		final StringBuilder sb = new StringBuilder();

		append(sb, getField(json, "Id"));
		append(sb, getField(json, "Name"));
		append(sb, getField(json, "Description"));
		append(sb, getField(json, "BuffId"));
		append(sb, getField(json, "Magnitude"));

		lines.put(LEADER, sb.toString());
		return lines;
	}

}
