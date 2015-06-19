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

@Plugin(type = JSONtoWikiConverter.class)
public class ActiveSkillsConverter extends XLSWikiConverter {

	private final String ACTIVE = "active_skills";

	@Override
	public String[] getURLs() {
		return new String[] { "ActiveSkill.json", "new/ActiveSkill.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();
		headers
			.put(
				ACTIVE,
				"Active Skill ID\tActive Skill Name\tDesc\tNumTriggers\tCutInId\tSkillType1\tBuffIds1\t"
					+ "Magnitude1\tTargetType1\tAffinityAffected1\tProb1\tDuration1\tAnimationId1\tNot Used\t"
					+ "EffectId1\tFreq1\tSkillType2\tBuffIds2\tMagnitude2\tTargetType2\tAffinityAffected2\tProb2\t"
					+ "Duration2\tAnimationId2\tNot Used\tEffectId2\tFreq2\tSkillType3\tBuffIds3\tMagnitude3\t"
					+ "TargetType3\tAffinityAffected3\tProb3\tDuration3\tAnimationId3\tNot Used\tEffectId3\tFreq3");
		return headers;
	}

	@Override
	public Map<String, String> convert(final JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();

		final StringBuilder sb = new StringBuilder();
		append(sb, getField(json, "Id"));
		append(sb, getField(json, "Name"));
		append(sb, getField(json, "Desc"));
		append(sb, getField(json, "NumTriggers"));
		append(sb, getField(json, "CutInId"));
		// BuffIds is an array.. dictates the size of the other arrays
		final int skillCount = json.getJSONArray("BuffIds").length();
		for (int i = 0; i < skillCount; i++) {
			append(sb, getArrayField(json, "SkillType", i));
			append(sb, getArrayField(json, "BuffIds", i));
			append(sb, getArrayField(json, "Magnitude", i));
			append(sb, getArrayField(json, "TargetType", i));
			append(sb, getArrayField(json, "AffinityAffected", i));
			append(sb, getArrayField(json, "Prob", i));
			append(sb, getArrayField(json, "Duration", i));
			append(sb, getArrayField(json, "AnimationId", i));
			append(sb, " ");
			append(sb, getArrayField(json, "EffectId", i));
			append(sb, getArrayField(json, "Freq", i));
		}

		lines.put(ACTIVE, sb.toString());
		return lines;
	}

}
